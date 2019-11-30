
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Reviewers extends User {
	Scanner scanner = new Scanner(System.in);
	// list of objects or articles -> private ArrayList<Articles> articlesSelected;

	public Reviewers(String t, String fn, String sn, String e, String p, String u) {
		super(t, fn, sn, e, p, u);
		// TODO Auto-generated constructor stub
	}

	public void viewArticle() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void selectArticleReview() throws SQLException {
		String queryCheck = "SELECT * FROM submission WHERE email <> ? AND mainauthor = TRUE;";
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
				String query = "INSERT INTO firstreview(revid) VALUES (?)";
				String reviewID = generateHash(this.getEmail());
				data = (rows.get(choice1 - 1));
				data.set(3, reviewID );
				DAC.insertReviewID(query, reviewID);
				
				data = (rows.get(choice2 - 1));
				data.set(3, reviewID );
				DAC.insertReviewID(query, getEmail());
				
				data = (rows.get(choice3 - 1));
				data.set(3, reviewID );
				DAC.insertReviewID(query, getEmail());

			} catch (Exception e) {
				System.out.println("Invalid Input Try Again");
				selectArticleReview();

			}
		}else {
			
			System.out.println("Not enough articles : "  + rows);
		}

	}

	public void submitInitialVerdict() {
		System.out.print("Enter Summary");
		String sum = scanner.nextLine();
		System.out.print("Enter Typos");
		String typo = scanner.nextLine();
		String role = "";
		System.out.print("How many erros?");
		int count = scanner.nextInt(); 
		for (int i = 1; i<= count;i++) {
			System.out.print("How many erros?");
			role = scanner.nextLine();
		}

	}

	public void submitFinalVerdict() {

	}

	public void checkResponse() {

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
}
