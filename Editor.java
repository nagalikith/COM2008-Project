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
	
	
	public static void selectJournal(String email,String journalInput) throws NoSuchAlgorithmException, SQLException {
		Scanner sc = new Scanner(System.in);
		
		ArrayList<String> journalList = getJournalList(email);
		
        System.out.print("choose one journal to take actions ");
        System.out.println(journalList.toString());
        
		//System.out.print("Enter Journal Name");
		//String journal = sc.nextLine();
		
		if (journalList.contains(journalInput) == false)
		{
			System.out.println("not valid journal");

		}
		else {
			journal = journalInput;
		}
		sc.close();
		
	}
	
	public static void register(String title, String firstname, String lastname, String email, String uni) throws NoSuchAlgorithmException, SQLException {
		
		Scanner scanner = new Scanner(System.in);
		
		//System.out.println("Enter Title");
		//String title = scanner.nextLine();
		//System.out.println("Enter firstname");
		//String firstname = scanner.nextLine();
		//System.out.println("Enter lastname");
		//String lastname = scanner.nextLine();
		//System.out.println("Enter email");
		//String email = scanner.nextLine();
		//System.out.println("Enter passowrd");
		//String password = scanner.nextLine();
		//System.out.println("Enter Uni");
		//String uni = scanner.nextLine();
		String password = "pass";
		boolean chiefEditor = false;
		
		//email = "\'" + email + "\'" ; // 'email'
		//journal = "\'" + journal + "\'"; 
		String queryEmail = "SELECT * FROM user WHERE email = "+"\'" + email + "\'" +";";
		String queryEditor = "SELECT * FROM editor WHERE email = " + "\'" + email + "\'" + " AND " +"journal = " + "\'" + journal + "\'" + ";";

		scanner.close();
		
		if (DAC.checkEmail(queryEmail) == false) {
			System.out.println("user already exists");
			System.out.println("adding to editor");
			if (DAC.checkEmail(queryEditor) == true) {
				addEditor(email,journal,chiefEditor);
			}
			else {System.out.println("already an editor for the journal " + journal);}
		} else {
			login.loginNew(title, firstname, lastname, email, password, uni);
			//login.addrolls(role, email);
			addEditor(email,journal,chiefEditor);
		}
		
	}
	
	public static ArrayList<String> getJournalList(String email) throws SQLException{
		
		DAC.connectionOpen();
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
	
	public static void passRole(String email, String journalInput) throws SQLException {
		//pass role of chief editor to different editor
		Scanner sc = new Scanner(System.in);
		
		ArrayList<String> journalList = getJournalList(email);
		
        System.out.print("choose one journal to take actions ");
        System.out.println(journalList.toString());
        
		//System.out.print("Enter Journal Name");
		//String journal = sc.nextLine();
		
		DAC.connectionOpen();
		
		ArrayList<String> editors = new ArrayList<String>();
		String getEditors = "SELECT email FROM editor WHERE journal = ?";
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(getEditors);
			pstmt.setString(1, journalInput);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				editors.add(rs.getString("email"));
			}
			editors.remove(email);
			
			if(editors.isEmpty()) {
				System.out.println("you are the sole editor for the journal");
			}
			else {
			System.out.println("select editor to pass role to "+editors.toString());
			String emailnew = sc.nextLine();
			
			String queryOne = "UPDATE editor SET chiefeditor = true WHERE email = ? AND journal = ?";
			PreparedStatement pstmt2 = DAC.getCon().prepareStatement(queryOne);
			pstmt2.setString(1, emailnew);
			pstmt2.setString(2, journalInput);
			pstmt2.executeUpdate();
			
			String queryTwo = "UPDATE editor SET chiefeditor = false WHERE email = ? AND journal = ?";
			PreparedStatement pstmt3 = DAC.getCon().prepareStatement(queryTwo);
			pstmt3.setString(1, email);
			pstmt3.setString(2, journalInput);
			pstmt3.executeUpdate();
			}
			
		}
		catch(SQLException e) {
			System.out.println("passrole"+e);
		}
		finally {
			DAC.connectionClose();
		}
		sc.close();
		
	}
	
	public static void retire(String email, String journalInput) throws SQLException {
		//an editor may retire (possibly temporarily, to avoid a conflict of interest) from the board for
		//a journal, so long as at least one chief editor remains on the board;
		Scanner sc = new Scanner(System.in);
		
		ArrayList<String> journalList = getJournalList(email);
		
        System.out.print("choose one journal to take actions ");
        System.out.println(journalList.toString());
        
		//System.out.print("Enter Journal Name");
		//String journal= sc.nextLine();
		
		sc.close();
		
		//check number of editor for journal
		String queryCount = "SELECT COUNT(*) FROM editor WHERE journal = ?";
		DAC.connectionOpen();
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(queryCount);
			pstmt.setString(1, journalInput);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			int n = rs.getInt(1);

			if (n>1) {
				//check if email is chief
				String queryCheck = "SELECT chiefeditor FROM editor WHERE email = ? AND journal = ?";
				PreparedStatement pstmt4 = DAC.getCon().prepareStatement(queryCheck);
				pstmt4.setString(1, email);
				pstmt4.setString(2, journalInput);
				ResultSet rs4 = pstmt4.executeQuery();
				rs4.next();
				boolean chief = rs4.getBoolean(1);
				
				//delete the email from the table editor
				String queryDel = "DELETE FROM editor WHERE email = ? AND journal = ?";
				PreparedStatement pstmt1 = DAC.getCon().prepareStatement(queryDel);
				pstmt1.setString(1, email);
				pstmt1.setString(2, journalInput);
				pstmt1.executeUpdate();
				
				if(chief) {
				//make the next person chief editor
				String makeNext = "SELECT * FROM editor WHERE journal = ?";
				PreparedStatement pstmt2 = DAC.getCon().prepareStatement(makeNext);
				pstmt2.setString(1, journalInput);
				ResultSet rs2 = pstmt2.executeQuery();
				rs2.next();
				String nameNewChief = rs2.getString("email");
				
				String update = "UPDATE editor SET chiefeditor = true WHERE email = ? AND journal = ?";
				PreparedStatement pstmt3 = DAC.getCon().prepareStatement(update);
				pstmt3.setString(1, nameNewChief);
				pstmt3.setString(2, journalInput);
				pstmt3.executeUpdate();
				
				System.out.println("deleted made new cheif");
				}
				else {
					System.out.println("deleted was not cheif");
				}
			}
			else {
				//if number = 1, cant retire
				System.out.println("cant retire youre sole editor");
			}
		}
		catch(SQLException e) {
			System.out.println("retire " + e);
		}
		finally {
			DAC.connectionClose();
		}
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
			
			String insert = "INSERT INTO currentedition VALUES(?)";
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
	
	public static void checkFinal() throws SQLException {
		DAC.connectionOpen();
		
		try {
			String query = "SELECT subid,COUNT(subid) FROM review WHERE status = 'final' GROUP BY subid";
			PreparedStatement ptsmt = DAC.getCon().prepareStatement(query);
			ResultSet rs = ptsmt.executeQuery();
			
			String insert = "INSERT INTO reviewedarticle VALUES(?)";
			PreparedStatement ptsmt2 = DAC.getCon().prepareStatement(insert);
			
			String update = "UPDATE review SET status = 'complete' WHERE subid = ?";
			PreparedStatement ptsmt3 = DAC.getCon().prepareStatement(update);

			while(rs.next()) {
				if(rs.getInt(2) == 3) {
					
					String subId = rs.getString(1);
					
					ptsmt2.setString(1, subId);
					ptsmt3.setString(1, subId);
					ptsmt2.executeUpdate();
					ptsmt3.executeUpdate();
				}
			}
			
			
		}
		catch(SQLException e) {
			System.out.println("checkFinal "+e);
		}
		finally {
			DAC.connectionClose();
		}
	}
	
	public static ArrayList<String> getVerdicts(String id) throws SQLException {
		DAC.connectionOpen();
		ArrayList<String> verdicts = new ArrayList<String>(); 
		
		try {
			String query = "SELECT judgement FROM review WHERE subid = ?";
			PreparedStatement ptsmt = DAC.getCon().prepareStatement(query);
			ptsmt.setString(1, id);
			ResultSet rs = ptsmt.executeQuery();
			
			while(rs.next()) {
				verdicts.add(rs.getString("judgement"));
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
}
