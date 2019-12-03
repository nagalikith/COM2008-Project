
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

	public void viewArticle() throws SQLException, NoSuchAlgorithmException {
		String queryCheck = "SELECT * FROM review WHERE revid = \'" + generateHash(getEmail()) + "\' ;";
		if (DAC.checkEmail(queryCheck)) {
			selectArticleReview();
		} else {
			ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck);
			ArrayList<String> data = new ArrayList<String>();

			int count = 1;
			for (ArrayList<String> list : rows) {
				System.out.println(count + ": revid: " + list.get(0) + " subid " + list.get(1));
			}
			System.out.println("Select Article of choice");
			int choice = scanner.nextInt();

			data = (rows.get(choice - 1));
			subid = data.get(1);

		}
	}

	public void selectArticleReview() throws SQLException, NoSuchAlgorithmException {
		String queryCheck = "SELECT * FROM submission WHERE email <> ? AND mainauthor = TRUE AND status = 'Submission';";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, this.getEmail());
		ArrayList<String> data = new ArrayList<String>();
		if (rows.size() >= 3) {

			int count = 1;
			for (ArrayList<String> list : rows) {
				System.out.println(count + ": Title: " + list.get(1) + " Author " + list.get(3));
			}

			System.out.println("Select article 1st Article of choice");
			int choice1 = scanner.nextInt();
			System.out.println("Select article 2nd Article of choice");
			int choice2 = scanner.nextInt();
			System.out.println("Select article 3rd Article of choice");
			int choice3 = scanner.nextInt();
			try {
				String query = "INSERT INTO review(revid,subid) VALUES (?,?)";
				String reviewID = generateHash(this.getEmail());
				data = (rows.get(choice1 - 1));
				data.set(3, reviewID);
				DAC.insertReviewID(query, reviewID, data.get(0));

				data = (rows.get(choice2 - 1));
				data.set(3, reviewID);
				DAC.insertReviewID(query, reviewID, data.get(0));

				data = (rows.get(choice3 - 1));
				data.set(3, reviewID);
				DAC.insertReviewID(query, reviewID, data.get(0));

			} catch (Exception e) {
				System.out.println("Invalid Input Try Again");
				selectArticleReview();

			}
		} else {

			System.out.println("Not enough articles : " + rows);
		}

	}

	public void submitInitialVerdict(String status) throws SQLException, NoSuchAlgorithmException {
		String revid = generateHash(getEmail());
		System.out.print("Enter Summary");
		String sum = scanner.nextLine();
		scanner.nextLine();
		System.out.print("Enter Typos");
		String typo = scanner.nextLine();
		String role = "editor";
		System.out.print("How many errors?");
		int count = scanner.nextInt();
		scanner.nextLine();
		ArrayList<String> errors = new ArrayList<String>();
		for (int i = 1; i <= count; i++) {
			System.out.print("Enter error?");
			role = scanner.nextLine();
			errors.add(role);
		}
		System.out.print("Enter judgement");
		String judgement = scanner.nextLine();
		String queryrev = "UPDATE review " + "set summary = ? , typos = ? , judgement = ? , status = ? "
				+ "where revid = \'" + revid + "\' AND subid = ?";
		String queryerror = "INSERT INTO errors VALUES (?,?,?,?,?);";
		DAC.addinitialsub(queryrev, sum, typo, judgement, status, subid);
		DAC.adderror(queryerror, errors, revid, subid, "Editor");

	}

	public void submitFinalVerdict() throws NoSuchAlgorithmException, SQLException {
		if (checkResponse()) {

			String revid = generateHash(getEmail());
			System.out.print("Enter Summary");
			String sum = scanner.nextLine();
			scanner.nextLine();
			System.out.print("Enter Typos");
			String typo = scanner.nextLine();
			String role = "editor";
			scanner.nextLine();
			System.out.print("How many errors?");
			int count = scanner.nextInt();
			ArrayList<String> errors = new ArrayList<String>();
			for (int i = 1; i <= count; i++) {
				System.out.print("Enter error?");
				role = scanner.nextLine();
				errors.add(role);
			}
			System.out.print("Enter judgement");
			String judgement = scanner.nextLine();
			String queryrev = "INSERT INTO review(revid,subid,summary,typos,judgement,status) VALUES (?,?,?,?,?,?);";
			String queryerror = "INSERT INTO errors VALUES (?,?,?,?,?);";
			DAC.addFinalSub(queryrev, revid, subid, sum, typo, judgement, "FINAL");
			DAC.adderror(queryerror, errors, revid, subid, "Editor");
		} else {
			System.out.println("No response present");
		}

	}

	public boolean checkResponse() throws SQLException {
		String queryCheck = "SELECT * FROM review WHERE subid = \'" + subid + "\' AND status = 'response' ;";
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
