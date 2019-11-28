import java.security.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class login {
	static FindDrivers connect = new FindDrivers();

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
			loginExist(email, password);
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
			if (connect.CheckEmail(query)) {
				System.out.println("Invalid Email");
			} else {
				loginNew(title, firstname, lastname, email, password, uni);
				addrolls(role, email);
			}
		} else {
			System.out.println("Wrong Choice");
		}

		scanner.close();
	}

	private static void addrolls(String role, String email) throws SQLException {
		// TODO Auto-generated method stub
		String query = "INSERT INTO roles VALUES (?,?);";
		String queryCheck = "SELECT * FROM roles WHERE user = " + "\'" + email + "\'" + ";";
		if (connect.CheckRole(role, queryCheck) == false) {
			System.out.println("Role already selected");
		} else {
			connect.addrolls(email, role, query);
		}

	}

	public static void loginExist(String username, String passowrd) throws SQLException, NoSuchAlgorithmException {
		String query = "SELECT * FROM user WHERE email = " + "\'" + username + "\'" + ";";
		// ResultSet rs = connect.connection(query);
		if (connect.CheckEmail(query)) {
			System.out.println("User Doesnt exist");
		} else {
			String pass = (passowrd);
			connect.CheckUser(query, pass);

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
		connect.signUp(query, data);

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

	public static byte[] toByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

}
