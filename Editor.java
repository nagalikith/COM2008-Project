package classesTest;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Editor extends User {

	//private boolean chiefEditor;
	//private static Connection con;
	private static String journal;
	
	public Editor(String t, String fn, String sn, String e, String p, String u) {
		super(t, fn, sn, e, p, u);
		// TODO Auto-generated constructor stub
	}
	
	public void setJournal(String j) {
		journal = j;
	}
	public String getJournal() {
		return journal;
	}
	
	//functions
	
	public static void addEditor(String email, String journal, boolean chiefEditor) throws SQLException {
		DAC.connectionOpen();
		String query = "INSERT INTO editor VALUES (?,?,?)";
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, journal);
			pstmt.setBoolean(3, chiefEditor);
			pstmt.executeUpdate();
			pstmt.close();
			
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		finally {
			DAC.connectionClose();
		}
	}
	
	public void addChief() throws SQLException {
		DAC.connectionOpen();
		String check = "SELECT COUNT(chiefeditor) FROM editor WHERE email = ? AND journal = ?";
		String checkJournal = "SELECT COUNT(*) FROM editor WHERE chiefeditor = true AND journal = ?";
		String insert = "INSERT INTO editor VALUES (?,?,true)";
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(check);
			PreparedStatement pstmt2 = DAC.getCon().prepareStatement(checkJournal);
			PreparedStatement pstmt3 = DAC.getCon().prepareStatement(insert);

			pstmt.setString(1, getEmail());
			pstmt.setString(2, journal);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			
			if(count == 0) {
				//not in editor means that user is chief since he wasnt added by a chief editor
				//check if journal already has chief else add to db as cheif
				pstmt2.setString(1,journal);
				ResultSet rs2 = pstmt2.executeQuery();
				rs2.next();
				int chiefCount = rs2.getInt(1);
				
				if(chiefCount == 0) {
					pstmt3.setString(1, getEmail());
					pstmt3.setString(2, journal);
					pstmt3.executeUpdate();
				}
				else {
					System.out.println("journal already has chief editor");
				}
			}
			else {
				System.out.println("user already editor for journal");
			}
			
		}
		catch(SQLException e) {
			System.out.println("add chief "+e);
		}
		finally {
			DAC.connectionClose();
		}
	}
	
	public boolean isChief() throws SQLException {
		DAC.connectionOpen();
		String checkEditor = "SELECT chiefeditor FROM editor WHERE email = ? AND journal = ?";
		
		boolean bool = false;
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(checkEditor);
			
			pstmt.setString(1, getEmail());
			pstmt.setString(2, journal);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			bool = rs.getBoolean(1);
		}
		catch(SQLException e) {
			System.out.println("ischief "+e);
		}
		finally {
			DAC.connectionClose();
		}
		
		return bool;
	}
	
	/*public void selectJournal(String email,String journalInput) throws NoSuchAlgorithmException, SQLException {
		Scanner sc = new Scanner(System.in);
		
		boolean bool = isChief(email,journalInput);
		ArrayList<String> journalList = getJournalList();
		
        System.out.print("choose one journal to take actions ");
        System.out.println(journalList.toString());
        
		//System.out.print("Enter Journal Name");
		//String journal = sc.nextLine();
		
		if(bool) {
			//give three options
		}
		else {
			//give only valid options
			if (journalList.contains(journalInput) == false) {
				System.out.println("not valid journal");

			} else {
				journal = journalInput;
			}
		}
		sc.close();
		
	}*/
	
	public String register(String title, String firstname, String lastname, String email,String password, String uni) throws NoSuchAlgorithmException, SQLException {
		String out = "";
		boolean chiefEditor = false;
		
		String queryEmail = "SELECT * FROM user WHERE email = ?";
		String queryEditor = "SELECT * FROM editor WHERE email = ? AND journal = ?";

		if (DAC.checkEmail(queryEmail,email) == false) {
			out = "user already exists adding to editor";
			if (DAC.checkEmail(queryEditor,email,journal) == true) {
				addEditor(email,journal,chiefEditor);
			}
			else {out = "already an editor for the journal " + journal;}
		} else {
			out = "added";
			login.loginNew(title, firstname, lastname, email, password, uni);
			//login.addrolls(role, email);
			addEditor(email,journal,chiefEditor);
		}
		
		return out;
		
	}
	
	public ArrayList<String> getJournalList() throws SQLException{
		
		DAC.connectionOpen();
		
		String email = getEmail();
		String query = "SELECT * FROM editor WHERE email = ?";
		ArrayList<String> journalList = new ArrayList<String>();
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(query);
			pstmt.setString(1, email);
			ResultSet rsGetList = pstmt.executeQuery();
			
			while (rsGetList.next()) {
				journalList.add(rsGetList.getString("journal"));
				}
			}
		catch(SQLException e) {
			System.out.println("getJournalList"+e);
		}
		finally {
			DAC.connectionClose();
		}
		return journalList;

	}
	public ArrayList<String> getEditorList() throws SQLException {
		
		DAC.connectionOpen();
		String out = "";
		ArrayList<String> editors = new ArrayList<String>();
		String getEditors = "SELECT email FROM editor WHERE journal = ?";
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(getEditors);
			pstmt.setString(1, journal);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				editors.add(rs.getString("email"));
			}
			editors.remove(getEmail());

		}
		catch(SQLException e) {
			System.out.println("getEditorList"+e);
		}
		finally {
			DAC.connectionClose();
		}
		return editors;
	}
	
	public boolean isEditor(String email) throws SQLException {
		DAC.connectionOpen();
		
		boolean bool = false;
		String query = "SELECT count(*) FROM editor WHERE email =? AND journal = ?";
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, journal);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int n = rs.getInt(1);
			
			if(n==0) {
				bool = false;
			}
			else {bool = true;}
		}
		catch(SQLException e) {
			System.out.println("is editor "+e);
		}
		finally {
			DAC.connectionClose();
		}
		return bool;
	}
	
	public void passRole(String emailNew) throws SQLException {
		//pass role of chief editor to different editor
		String out = "";
		ArrayList<String> editors = getEditorList();

		DAC.connectionOpen();
		
		try {
			editors.remove(getEmail());
			
			if(editors.isEmpty()) {
				out = "you are the sole editor for the journal";
			}
			else {
			
			System.out.println(emailNew);
			String queryOne = "UPDATE editor SET chiefeditor = true WHERE email = ? AND journal = ?";
			PreparedStatement pstmt2 = DAC.getCon().prepareStatement(queryOne);
			pstmt2.setString(1, emailNew);
			pstmt2.setString(2, journal);
			pstmt2.executeUpdate();
			
			String queryTwo = "UPDATE editor SET chiefeditor = false WHERE email = ? AND journal = ?";
			PreparedStatement pstmt3 = DAC.getCon().prepareStatement(queryTwo);
			pstmt3.setString(1, getEmail());
			pstmt3.setString(2, journal);
			pstmt3.executeUpdate();
			}
			
		}
		catch(SQLException e) {
			System.out.println("passrole "+e);
		}
		finally {
			DAC.connectionClose();
		}
		
	}
	
	public boolean retire() throws SQLException {
		//an editor may retire (possibly temporarily, to avoid a conflict of interest) from the board for
		//a journal, so long as at least one chief editor remains on the board;
		
		//check number of editor for journal
		String queryCount = "SELECT COUNT(*) FROM editor WHERE journal = ?";
		boolean bool = false;
		DAC.connectionOpen();
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(queryCount);
			pstmt.setString(1, journal);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			int n = rs.getInt(1);

			if (n>1) {
				//check if email is chief
				String queryCheck = "SELECT chiefeditor FROM editor WHERE email = ? AND journal = ?";
				PreparedStatement pstmt4 = DAC.getCon().prepareStatement(queryCheck);
				pstmt4.setString(1, getEmail());
				pstmt4.setString(2, journal);
				ResultSet rs4 = pstmt4.executeQuery();
				rs4.next();
				boolean chief = rs4.getBoolean(1);
				
				//delete the email from the table editor
				String queryDel = "DELETE FROM editor WHERE email = ? AND journal = ?";
				PreparedStatement pstmt1 = DAC.getCon().prepareStatement(queryDel);
				pstmt1.setString(1, getEmail());
				pstmt1.setString(2, journal);
				pstmt1.executeUpdate();
				
				if(chief) {
				//make the next person chief editor
				String makeNext = "SELECT * FROM editor WHERE journal = ?";
				PreparedStatement pstmt2 = DAC.getCon().prepareStatement(makeNext);
				pstmt2.setString(1, journal);
				ResultSet rs2 = pstmt2.executeQuery();
				rs2.next();
				String nameNewChief = rs2.getString("email");
				
				String update = "UPDATE editor SET chiefeditor = true WHERE email = ? AND journal = ?";
				PreparedStatement pstmt3 = DAC.getCon().prepareStatement(update);
				pstmt3.setString(1, nameNewChief);
				pstmt3.setString(2, journal);
				pstmt3.executeUpdate();
				
				System.out.println("deleted made new cheif");
				}
				else {
					System.out.println("deleted was not cheif");
				}
				bool = true;
			}
			else {
				//if number = 1, cant retire
				bool = false;
			}
		}
		catch(SQLException e) {
			System.out.println("retire " + e);
		}
		finally {
			DAC.connectionClose();
		}
		
		return bool;
	}
	
	public void viewArticles() throws SQLException{
		//an editor may see a list of articles under consideration, about which they are entitled to take
		//a final decision, but which excludes any conflicts of interest;
		
		//prepare query for conflict of interest
		try {
			DAC.connectionOpen();
			String query = "SELECT * FROM reviewedarticle";
			PreparedStatement pstmt = DAC.getCon().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//using the article id, display the three verdicts for
				//that article, ask the editor to give his verdict (do that in submitverdict)
				//iterate
			}
		}
		catch(SQLException e) {
			System.out.println("viewarticles "+e);
		}
		finally {
			
		}
	}
	public ArrayList<String> getLists() throws SQLException{
		DAC.connectionOpen();
		String query = "SELECT submission.id "
				+ "FROM reviewedarticle "
				+ "INNER JOIN submission "
				+ "ON submission.id = reviewedarticle.articleid "
				+ "WHERE submission.journal = ? "
				+ "GROUP BY id;";
		
        ArrayList<String> idList = new ArrayList<String>();
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(query);
			pstmt.setString(1, journal);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				idList.add(rs.getString(1));
			}

		}
		catch(SQLException e) {
			System.out.println("getRevArti "+e);
		}
		finally {
			DAC.connectionClose();
		}
		
		return idList;
	}
	
	public ArrayList<String> getUniList(String id) throws SQLException{
		DAC.connectionOpen();
		//gets list of uniAffiliations for the subid including the conflict
		String query = "SELECT user.uni, submission.email, submission.id "
				+ "FROM submission "
				+ "INNER JOIN user "
				+ "ON submission.email = user.email "
				+ "WHERE id = ? "
				+ "GROUP BY email";
		
        ArrayList<String> uniList = new ArrayList<String>();
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				uniList.add(rs.getString(1));
			}

		}
		catch(SQLException e) {
			System.out.println("getRevArti "+e);
		}
		finally {
			DAC.connectionClose();
		}

		return uniList;
	}
	
	public ArrayList<ArrayList<String>> getReviewedArticle() throws SQLException {
		DAC.connectionOpen();
		/*String query = "SELECT reviewedarticle.articleid, submission.title "
				+ "FROM reviewedarticle "
				+ "INNER JOIN submission ON reviewedarticle.articleid = submission.id "
				+ "WHERE journal = ?"
				+ "GROUP BY id"; */
		
		String query = "SELECT reviewedarticle.articleid, submission.title "
				+ "FROM reviewedarticle "
				+ "INNER JOIN submission "
				+ "ON reviewedarticle.articleid = submission.id "
				+ "INNER JOIN user "
				+ "ON user.email = submission.email "
				+ "WHERE submission.journal = ? AND NOT user.uni = ?";
		
		ArrayList<ArrayList<String>> combinedList = new ArrayList<ArrayList<String>>();
		ArrayList<String> idList = new ArrayList<String>();
		ArrayList<String> titleList = new ArrayList<String>();
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(query);
			pstmt.setString(1, journal);
			pstmt.setString(2, getUniAffiliation());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				idList.add(rs.getString(1));
				titleList.add(rs.getString(2));
			}
			
			rs.close();
			pstmt.close();
		}
		catch(SQLException e) {
			System.out.println("getRevArti "+e);
		}
		finally {
			DAC.connectionClose();
		}
		combinedList.add(idList);
		combinedList.add(titleList);
		
		return combinedList;
	}
	
	public void delFromReviewed(ArrayList<String> usedId) throws SQLException {
		
		DAC.connectionOpen();
		String delete = "DELETE FROM reviewedarticle WHERE articleId = ?";
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(delete);
			
			for(int i=0; i<usedId.size(); i++) {
				pstmt.setString(1, usedId.get(i));
				pstmt.executeUpdate();
			}
		}
		catch(SQLException e) {
			System.out.println("del frm article "+e);
		}
		finally {
			DAC.connectionClose();
		}
	}
	
	public void addIntoCurrentEdition(ArrayList<String> acceptedId) throws SQLException {
		
		DAC.connectionOpen();
		String insert = "INSERT INTO currentedition VALUES(?,?)";
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(insert);
			
			pstmt.setString(2, journal);
			for(int i=0; i<acceptedId.size(); i++) {
				pstmt.setString(1, acceptedId.get(i));
				pstmt.executeUpdate();
			}
		}
		catch(SQLException e) {
			System.out.println("add into cur ed "+e);
		}
		finally {
			DAC.connectionClose();
		}
	}
	
	/*
	public static void submitVerdict() throws SQLException{
		// related to viewArticles. since they can only makes decision on the ones they can view
		
		//any editor on the board may make the final decision to publish or reject an article, according
		//to the “Champion and Detractor” rules;
		
		//a published article is added to the next edition in the current volume of the journal;
		DAC.connectionOpen();
		
		try {
			String query = "SELECT * FROM reviewedarticle";
			PreparedStatement pstmt = DAC.getCon().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			String insert = "INSERT INTO currentedition VALUES(?,?)";
			PreparedStatement pstmt2 = DAC.getCon().prepareStatement(insert);
			
			String delete = "DELETE FROM reviewedarticle WHERE articleId = ?";
			PreparedStatement pstmt3 = DAC.getCon().prepareStatement(delete);
			
			int n = 0;
			Scanner sc = new Scanner(System.in);
			
			while(rs.next()) {
				String articleId = rs.getString(1);
				//give him the summary too, so he can make a decision
				
				System.out.println("ArticleId = "+articleId+ " final verdicts = "+getVerdicts(articleId).toString());
				System.out.println("select number for verdict");
				System.out.print("1.strong accept 2.weak accept 3.weak reject 4.strong reject");
				int choice = sc.nextInt();
				
				if(choice == 1 || choice ==2) {
					System.out.println("adding to current edition");
					pstmt2.setString(1, articleId);
					pstmt2.executeUpdate();
				}
				else {
					System.out.println("rejected");
				}
				pstmt3.setString(1, articleId);
				pstmt3.executeUpdate();
			}
			sc.close();
			pstmt.close();
			rs.close();
			pstmt2.close();
			pstmt3.close();
			
		}
		catch(SQLException e) {
			System.out.println("submitverdict "+e);
		}
		finally {
			DAC.connectionClose();
		}
		

	}
	*/
	public void publishNextEdition() throws SQLException {
		//the chief editor may publish the next edition of the journal when it is ready, thereby making
		//all the contained articles available to readers
		DAC.connectionOpen();
		Scanner sc = new Scanner(System.in);
		
		try {
			String getEditions = "SELECT * FROM currentedition"; //list of article ID
			PreparedStatement pstmt = DAC.getCon().prepareStatement(getEditions);
			ResultSet rs = pstmt.executeQuery();
			
			String getEditionNum = "SELECT MAX(editionnum) FROM edition";
			PreparedStatement pstmt2 = DAC.getCon().prepareStatement(getEditionNum);
			ResultSet rs2 = pstmt2.executeQuery();
			rs2.next();
			int editionNum = rs2.getInt(1);
			
			String insert = "INSERT INTO edition (articleid, editionnum, journal) VALUES(?,?)";
			PreparedStatement pstmt3 = DAC.getCon().prepareStatement(insert);
			
			while(rs.next()) {
				String articleId = rs.getString(1); //iterates over all the article IDs and inserts them into edition
				
				System.out.println("choose journal for "+articleId);
				String journal = sc.nextLine();
				pstmt3.setString(1, articleId);
				pstmt3.setInt(2, editionNum);
				pstmt3.setString(3, journal);
				pstmt3.executeUpdate();
			}
		}
		catch(SQLException e) {
			System.out.println("delayarticle" + e);
		}
		finally {
			DAC.connectionClose();
		}
		sc.close();		
	}
	
	public String getVerdicts(String id) throws SQLException {
		DAC.connectionOpen();
		String verdicts = "";
		
		try {
			String query = "SELECT judgement FROM review WHERE subid = ?";
			PreparedStatement ptsmt = DAC.getCon().prepareStatement(query);
			ptsmt.setString(1, id);
			ResultSet rs = ptsmt.executeQuery();
			
			while(rs.next()) {
				verdicts = verdicts + " " + rs.getString(1);
			}
		}
		catch(SQLException e) {
			System.out.println("getVerdict "+e);
		}
		finally {
			DAC.connectionClose();
		}
		
		return verdicts;
	}
	
	public String getTitle(String id) throws SQLException {
		DAC.connectionOpen();
		String title ="";
		
		try {
			String query = "SELECT title FROM submission WHERE id = ?";
			PreparedStatement ptsmt = DAC.getCon().prepareStatement(query);
			ptsmt.setString(1, id);
			ResultSet rs = ptsmt.executeQuery();
			
			rs.next(); 
			title = rs.getString(1);
		}
		catch(SQLException e) {
			System.out.println("getVerdict "+e);
		}
		finally {
			DAC.connectionClose();
		}
		
		return title;
	}
}
