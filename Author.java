
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Author extends User {
	Scanner scanner = new Scanner(System.in);
	private static String subid;

	public Author(String t, String fn, String sn, String e, String p, String u) throws SQLException {
		super(t, fn, sn, e, p, u);
		// TODO Auto-generated constructor stub
	}
	
	public void gettingsubID(String id) {
		subid = id;
		
	}

	public void submitArticle(String title, String abst, String pdf) throws SQLException, NoSuchAlgorithmException, FileNotFoundException {
		String uniqueID = UUID.randomUUID().toString();
		String query = "INSERT INTO submission VALUES (?,?,?,?,?,TRUE,'Submission');";
		ArrayList<String> data = new ArrayList<String>();
		data.add(uniqueID);
		data.add(title);
		data.add(abst);
		data.add(this.getEmail());
		data.add(pdf);
		DAC.publish(query, data);
		query = "INSERT INTO roles VALUES (?,?);";
		login.addroll("Reviewer",getEmail());
	}

	public void registerCoAuthor(String title, String firstname, String lastname, String email, String password,
			String uni) throws NoSuchAlgorithmException, SQLException {
		// and by submitting,
		// they self-register as an author and register all the co-authors of the
		// article;
		String queryCheck = "SELECT * FROM user WHERE email = " + "\'" + email + "\'" + ";";
		if (DAC.checkEmail(queryCheck)) {
			login.loginNew(title, firstname, lastname, email, password, uni);
			login.addroll("Author", email);
			addCoAuthor(email);
		} else {
			System.out.println("Invalid Email");

		}
	}

	public void addCoAuthor(String email) throws SQLException {
		// TODO Auto-generated method stub
		String queryCheck = "SELECT * FROM submission WHERE email = ? AND id = \'"+ subid +"\' ;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, this.getEmail());
		ArrayList<String> data = new ArrayList<String>();
		try {
			data = (rows.get(0));
			data.set(3, email);
			String query = "INSERT INTO submission VALUES (?,?,?,?,FALSE,\'" + data.get(5) + "\');";
			DAC.publish(query, data);

		} catch (Exception e) {
			System.out.println("Invalid Input Try Again");
			addCoAuthor(getEmail());
			// TODO: handle exception
		}

	}

	public void submitRevisedArticle(String title,String abst, String pdf) throws SQLException, FileNotFoundException {
		String query = "INSERT INTO submission VALUES (?,?,?,?,?,TRUE,'Revised');";
		ArrayList<String> data = new ArrayList<String>();
		data.add(subid);
		data.add(title);
		data.add(abst);
		data.add(this.getEmail());
		data.add(pdf);
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
	
	public static String articleTitle() throws SQLException {
		String queryCheck = "SELECT * FROM review WHERE subid = \'" + subid + "\' AND status = 'Submission' ;";
		ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck);
		return rows.get(0).get(1);

	}

	public ArrayList<ArrayList<String>> checkInitialVerdict() throws SQLException {
		String queryCheck = "SELECT * FROM review WHERE subid = \'" + subid + "\' AND status = 'Initial' ;";
		ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck);
		return rows;

	}

	public ArrayList<ArrayList<String>> checkFinalVerdict() throws SQLException {
		String queryCheck = "SELECT * FROM review WHERE subid = \'" + subid + "\' AND status = 'Initial' ;";
		ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck);
		return rows;

	}

	public ArrayList<ArrayList<String>> checkInitialArticle() throws SQLException, IOException {
		String queryCheck = "SELECT * FROM submission WHERE id = ? AND status = 'Submission' ;";
		String querypdf = "SELECT pdf FROM submission WHERE id = ? AND status = 'Submission' ;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, subid);
		if (rows.size() == 0) {
			return null;
		} else {
			DAC.getpdf(querypdf,subid);
			return rows;
		}

	}

	public ArrayList<ArrayList<String>> checkFinalArticle() throws SQLException, IOException {
		String queryCheck = "SELECT * FROM submission WHERE id = ? AND status = 'Revised' ;";
		String querypdf = "SELECT pdf FROM submission WHERE id = ? AND status = 'Revised' ;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, subid);
		if (rows.size() == 0) {
			return null;
		} else {
			DAC.getpdf(querypdf,subid);
			return rows;
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
