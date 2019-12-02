import java.security.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class login {

	public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter 1 to Login Enter 2 to Signup");
		String choice = scanner.nextLine();
		if (Integer.parseInt(choice) == 1) {
			System.out.print("Enter email");
			String email = scanner.nextLine();
			System.out.print("Enter passowrd");
			String password = scanner.nextLine();
			System.out.print("Enter role");
			String role = scanner.nextLine();
			
			String queryCheck = "SELECT * FROM roles WHERE user = " + "\'" + email + "\'" + ";";
			if (loginExist(email, password) && (DAC.checkRole(role,queryCheck) == false)){
				String query = "SELECT * FROM user WHERE email = ?";
				ArrayList<String> user = DAC.getUser(query, email);
				//Reviewers reviewer = new Reviewers(user.get(0), user.get(1), user.get(2), user.get(3), user.get(4), user.get(5));
				Author author = new Author(user.get(0), user.get(1), user.get(2), user.get(3), user.get(4), user.get(5));
				System.out.print("Enter 1 to view Articles Enter 2 to AddCoAuthor");
				int choice1 = scanner.nextInt();
				scanner.nextLine();
				if (choice1 == 1) {
					//reviewer.viewArticle();
					//reviewer.submitInitialVerdict("Initial");
					//reviewer.submitFinalVerdict();
					author.submitArticle();
					author.registerCoAuthor();
					//author.submitRevisedArticle();
					System.out.println(author.checkInitialArticle());
					System.out.println(author.checkFinalArticle());
				}else {
					//author.registerCoAuthor();
				}
				
			}
			
		} else if (Integer.parseInt(choice) == 2) {
			System.out.print("Enter Title");
			String title = scanner.nextLine();
			System.out.print("Enter firstname");
			String firstname = scanner.nextLine();
			System.out.print("Enter lastname");
			String lastname = scanner.nextLine();
			System.out.print("Enter email");
			String email = scanner.nextLine();
			System.out.print("Enter passowrd");
			String password = scanner.nextLine();
			System.out.print("Enter Uni");
			String uni = scanner.nextLine();
			System.out.print("Enter Role");
			String role = scanner.nextLine();
			String query = "SELECT * FROM user WHERE email = " + "\'" + email + "\'" + ";";
			if (DAC.checkEmail(query)) {
				loginNew(title, firstname, lastname, email, password, uni);
				addroll(role, email);
			} else {
				System.out.println("Invalid Email");
				
			}
		} else {
			System.out.println("Wrong Choice");
		}

		scanner.close();
	}

	static boolean addroll(String role, String email) throws SQLException {
		// TODO Auto-generated method stub
		String query = "INSERT INTO roles VALUES (?,?);";
		String queryCheck = "SELECT * FROM roles WHERE user = " + "\'" + email + "\'" + ";";
		if (DAC.checkRole(role, queryCheck) == false) {
			System.out.println("Role already selected");
			return false;
		} else {
			DAC.addrolls(email, role, query);
			return true;
		}

	}

	public static boolean loginExist(String username, String passowrd) throws SQLException, NoSuchAlgorithmException {
		String query = "SELECT * FROM user WHERE email = " + "\'" + username + "\'" + ";";
		// ResultSet rs = connect.connection(query);
		if (DAC.checkEmail(query)) {
			System.out.println("User Doesnt exist");
			return false;
		} else {
			String pass = generateHash(passowrd);
			return DAC.checkUser(query, pass);

		}

	}

	public static void loginNew(String title, String firstname, String lastname, String email, String passowrd,
			String uni) throws NoSuchAlgorithmException, SQLException {
		ArrayList<String> data = new ArrayList<String>();
		String query = "INSERT INTO user VALUES (?,?,?,?,?,?)";
		String pass = generateHash(passowrd);
		data.add(title);
		data.add(firstname);
		data.add(lastname);
		data.add(email);
		data.add(pass);
		data.add(uni);
		DAC.signUp(query, data);

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
