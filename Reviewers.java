
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Reviewers extends User {
	private String subid;
	Scanner scanner = new Scanner(System.in);
	// list of objects or articles -> private ArrayList<Articles> articlesSelected;

	public Reviewers(String t, String fn, String sn, String e, String p, String u)
			throws SQLException, NoSuchAlgorithmException {
		super(t, fn, sn, e, p, u);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<ArrayList<String>> viewArticle() throws SQLException, NoSuchAlgorithmException {
		String queryCheck = "SELECT * FROM review WHERE revid = \'" + generateHash(getEmail()) + "\' ;";
		ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck);
		return rows;


	}
	
	public void setSubId(ArrayList<ArrayList<String>> rows , String choice) {
		ArrayList<String> data = new ArrayList<String>();
		data = (rows.get(Integer.parseInt(choice) - 1));
		subid = data.get(1);
	}

	public boolean articleCheck() throws SQLException, NoSuchAlgorithmException {
		String queryCheck = "SELECT * FROM review WHERE revid = \'" + generateHash(getEmail()) + "\' ;";
		if (DAC.checkEmail(queryCheck)) {
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
		String queryCheck = "SELECT * FROM submission WHERE email <> ? AND mainauthor = TRUE AND status = 'Submission';";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, this.getEmail());
		return rows;

	}

	public void submitInitialVerdict(String sum, String typo, ArrayList<String> questions)
			throws SQLException, NoSuchAlgorithmException {
		String revid = generateHash(getEmail());
		String judgement = scanner.nextLine();
		String queryrev = "UPDATE review " + "set summary = ? , typos = ? , judgement = ? , status = ? "
				+ "where revid = \'" + revid + "\' AND subid = ?";
		String queryerror = "INSERT INTO errors(revid, subid, questions,count,role) VALUES (?,?,?,?,?);";
		DAC.addinitialsub(queryrev, sum, typo, judgement, "Initial", subid);
		DAC.adderror(queryerror, revid, subid, questions, "Editor");

	}

	public void submitFinalVerdict(String sum, String typo) throws NoSuchAlgorithmException, SQLException {
		String revid = generateHash(getEmail());
		String judgement = scanner.nextLine();
		String queryrev = "INSERT INTO review(revid,subid,summary,typos,judgement,status) VALUES (?,?,?,?,?,?);";
		DAC.addFinalSub(queryrev, revid, subid, sum, typo, judgement, "FINAL");

	}

	public boolean checkInitialreview() throws SQLException {
		String queryCheck = "SELECT * FROM review WHERE subid = \'" + subid + "\' AND status = 'response' ;";
		ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck);
		System.out.println(rows);
		if (rows.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean checkSubmission() throws SQLException {
		String queryCheck = "SELECT * FROM review WHERE subid = \'" + subid + "\' AND status IS NULL ;";
		ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck);
		System.out.println(rows);
		if (rows.size() == 0) {
			return false;
		} else {
			return true;
		}
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
