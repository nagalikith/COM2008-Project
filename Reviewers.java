
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Reviewers extends User {
	private static String subid;
	Scanner scanner = new Scanner(System.in);
	// list of objects or articles -> private ArrayList<Articles> articlesSelected;

	public Reviewers(String t, String fn, String sn, String e, String p, String u)
			throws SQLException, NoSuchAlgorithmException {
		super(t, fn, sn, e, p, u);
		// TODO Auto-generated constructor stub
	}
	
	public static String getsubID() {
		return subid ;
		
	}

	public ArrayList<ArrayList<String>> viewArticle() throws SQLException, NoSuchAlgorithmException {
		String queryCheck = "SELECT * FROM review WHERE revid = ? ;";
		ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck, generateHash(this.getEmail()));
		return rows;


	}
	
	public void setSubId(ArrayList<ArrayList<String>> rows , String choice) {
		ArrayList<String> data = new ArrayList<String>();
		data = (rows.get(Integer.parseInt(choice) - 1));
		subid = data.get(1);
	}

	public boolean articleCheck() throws SQLException, NoSuchAlgorithmException {
		String queryCheck = "SELECT * FROM review WHERE revid = ? ;";
		if (DAC.checkEmail(queryCheck , generateHash(getEmail()))) {
			return true;
		}
		return false;
	}

	public void selectArticleReview(ArrayList<ArrayList<String>> rows, String choice1, String choice2, String choice3)
			throws SQLException, NoSuchAlgorithmException {
		ArrayList<String> data = new ArrayList<String>();
		String query = "INSERT INTO review(revid,subid) VALUES (?,?)";
		String reviewID = generateHash(this.getEmail());
		data = (rows.get(Integer.parseInt(choice1) - 1));
		DAC.insertReviewID(query, reviewID, data.get(0));

		data = (rows.get(Integer.parseInt(choice2) - 1));
		DAC.insertReviewID(query, reviewID, data.get(0));

		data = (rows.get(Integer.parseInt(choice3) - 1));
		DAC.insertReviewID(query, reviewID, data.get(0));

	}

	public ArrayList<ArrayList<String>> showArticleForSelection() throws SQLException {
		String queryCheck = "SELECT * FROM submission WHERE email <> ? AND mainauthor = TRUE AND status = 'Submission' ;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, this.getEmail());
		return rows;

	}
	public boolean uniAffliation(String uni , String id) throws SQLException {
		String query = "select submission.id ,user.uni from submission,user where submission.email = user.email AND uni = ? ; ";
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			ArrayList<String> row = new ArrayList<String>();

			while (rs.next()) {
				row = new ArrayList<String>();
				row.add(rs.getString("uni"));
			}
			if (row.contains(uni)) {
				return true;
			} else {
				return false;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return false;
		
	}

	public void submitInitialVerdict(String sum, String typo,String judgement , ArrayList<String> questions)
			throws SQLException, NoSuchAlgorithmException {
		String revid = generateHash(getEmail());
		String queryrev = "UPDATE review " + "set summary = ? , typos = ? , judgement = ? , status = ? "
				+ "where revid = ? AND subid = ?";
		String queryerror = "INSERT INTO errors(revid, subid, question,count,role) VALUES (?,?,?,?,?);";
		DAC.addinitialsub(queryrev,revid, sum, typo, judgement, "Initial", subid);
		DAC.adderror(queryerror, revid, subid, questions, "Reviewer");

	}

	public void submitFinalVerdict(String sum, String typo,String judgement) throws NoSuchAlgorithmException, SQLException {
		String revid = generateHash(getEmail());
		String queryrev = "INSERT INTO review(revid,subid,summary,typos,judgement,status) VALUES (?,?,?,?,?,?);";
		DAC.addFinalSub(queryrev, revid, subid, sum, typo, judgement, "FINAL");
	}

	public boolean checkInitialreview(String id) throws SQLException, NoSuchAlgorithmException {
		String queryCheck = "SELECT * FROM review WHERE subid = ? AND revid = ? AND status = 'Initial' ;";
		ArrayList<ArrayList<String>> rows = DAC.checkreview(queryCheck,id,generateHash(this.getEmail()));
		System.out.println(rows);
		if (rows.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	public boolean checkSubmission(String id) throws SQLException, NoSuchAlgorithmException {
		String queryCheck = "SELECT * FROM review WHERE subid = ? AND revid = ? AND status IS NULL ;";
		ArrayList<ArrayList<String>> rows = DAC.checkreview(queryCheck,id,generateHash(this.getEmail()));
		System.out.println(id);
		if (rows.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public ArrayList<ArrayList<String>> checkResponse(String id) throws SQLException, NoSuchAlgorithmException {
		// responses to the reviews
		String query = "select * from errors WHERE subid = ? AND revid = ? AND answers is not null ";
		return DAC.getErrors(query, id, generateHash(this.getEmail()));

	}

	private static String generateHash(String passowrd) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		byte[] hash = digest.digest(passowrd.getBytes());
		return toHex(hash);
	}

	private static String toHex(byte[] hashInBytes) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hashInBytes.length; i++) {
			sb.append(Integer.toString((hashInBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();

	}

	// String queryerror = "INSERT INTO errors VALUES (?,?,?,?,?);";
}
