import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Author extends User {
	Scanner scanner = new Scanner(System.in);
	private String subid;

	public Author(String t, String fn, String sn, String e, String p, String u) throws SQLException {
		super(t, fn, sn, e, p, u);
		// TODO Auto-generated constructor stub
		String queryCheck = "SELECT * FROM submission WHERE email = ? AND status = 'Submission';";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, this.getEmail());
		ArrayList<String> data = new ArrayList<String>();
		if (rows.size() > 0) {

			int count = 1;
			for (ArrayList<String> list : rows) {
				System.out.println(count + ": Title: " + list.get(1) + " Author " + list.get(3));
				count ++ ;
			}
			
			
			System.out.println("Select Article of choice");
			int choice = scanner.nextInt();
			
			data = (rows.get(choice - 1));
			subid = data.get(0);
		} else {
			System.out.println("Empty");
		}
	}

	public void submitArticle() throws SQLException, NoSuchAlgorithmException {
		// one main author is in charge of submitting an article for consideration
		// Must be replaced by GUI Text Fields.
		scanner.nextLine();
		System.out.print("Enter Title");
		String title = scanner.nextLine();
		System.out.print("Enter abstract");
		String abst = scanner.nextLine();
		String uniqueID = UUID.randomUUID().toString();
		String query = "INSERT INTO submission VALUES (?,?,?,?,TRUE,'Submission');";
		ArrayList<String> data = new ArrayList<String>();
		data.add(uniqueID);
		data.add(title);
		data.add(abst);
		data.add(this.getEmail());
		DAC.publish(query, data);
		query = "INSERT INTO roles VALUES (?,?);";
		DAC.addrolls(getEmail(), "editor", query);
	}
	
	public void registerCoAuthor() throws NoSuchAlgorithmException, SQLException {
		// and by submitting,
		// they self-register as an author and register all the co-authors of the
		// article;
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
		String queryCheck = "SELECT * FROM user WHERE email = " + "\'" + email + "\'" + ";";
		if (DAC.checkEmail(queryCheck)) {
			login.loginNew(title, firstname, lastname, email, password, uni);
			login.addroll(role, email);
			addCoAuthor(email);
		} else {
			System.out.println("Invalid Email");

		}
	}

	public void addCoAuthor(String email) throws SQLException {
		// TODO Auto-generated method stub
		String queryCheck = "SELECT * FROM submission WHERE email = ?;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, this.getEmail());
		ArrayList<String> data = new ArrayList<String>();
		for (ArrayList<String> list : rows) {
			System.out.println(list.get(1));
		}
		System.out.println("Select article number");
		int choice = scanner.nextInt();
		try {
			data = (rows.get(choice - 1));
			data.set(3, email);
			String query = "INSERT INTO submission VALUES (?,?,?,?,FALSE,\'"+data.get(5)+"\');";
			DAC.publish(query, data);

		} catch (Exception e) {
			System.out.println("Invalid Input Try Again");
			addCoAuthor(getEmail());
			// TODO: handle exception
		}

	}
	
	public void submitRevisedArticle() throws SQLException {
		scanner.nextLine();
		System.out.print("Enter Title");
		String title = scanner.nextLine();
		scanner.nextLine();
		System.out.print("Enter abstract");
		String abst = scanner.nextLine();
		String query = "INSERT INTO submission VALUES (?,?,?,?,TRUE,'Revised');";
		ArrayList<String> data = new ArrayList<String>();
		data.add(subid);
		data.add(title);
		data.add(abst);
		data.add(this.getEmail());
		DAC.publish(query, data);
	}

	public void trackStatusArticle() {
		// all authors may log in to track the status of their submitted article, until
		// the editor dealing
		// with this submission decides to accept or reject the article;
	}

	// all logged-in authors can see the reviews (of their own submission), the
	// initial verdicts, their
	// responses, the final verdicts and the initial and revised versions of their
	// article;

	public void checkReview() {

	}

	public void checkInitialVerdict() {
		String queryCheck = "SELECT * FROM review WHERE subid = \'"+ subid + "\' ;";

	}

	public void checkFinalVerdict() {

	}

	public boolean checkInitialArticle() throws SQLException {
		String queryCheck = "SELECT * FROM submission WHERE id = ? AND status = 'Submission' ;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, subid);
		if (rows.size() == 0) {
			return false;
		} else {
			System.out.println(rows);
			return true;
		}

	}

	public boolean checkFinalArticle() throws SQLException {
		String queryCheck = "SELECT * FROM submission WHERE id = ? AND status = 'Revised' ;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, subid);
		if (rows.size() == 0) {
			return false;
		} else {
			System.out.println(rows);
			return true;
		}

	}

	public void checkResponse() {
		// responses to the reviews
	}

	public void respondToReviews() {
		// make an oblect of class response
		// one main author is responsible for responding to the criticisms expressed in
		// the reviews and
		// for submitting the revised article, along with a response to each reviewer;
		// each response must include a list of answers to each of that reviewer’s
		// questions, where the
		// review contained a list of questions (or criticisms).
		
		
		
	}
}
