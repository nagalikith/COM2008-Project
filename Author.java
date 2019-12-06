package classesTest;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	public void setsubID(String id) {
		subid = id;

	}

	public String getsubID() {
		return subid;

	}

	public String mainAuthor(String id) throws SQLException {
		DAC.connectionOpen();
		String queryCheck = "SELECT * FROM submission WHERE email = ? AND id = ? ;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, this.getEmail(), id);
		return (rows.get(0)).get(4);
	}

	public void submitArticle(String title, String abst, String pdf, String journal)
			throws SQLException, NoSuchAlgorithmException, FileNotFoundException {
		String uniqueID = UUID.randomUUID().toString();
		String query = "INSERT INTO submission VALUES (?,?,?,?,?,TRUE,'Submission',?);";
		ArrayList<String> data = new ArrayList<String>();
		data.add(uniqueID);
		data.add(title);
		data.add(abst);
		data.add(this.getEmail());
		data.add(pdf);
		data.add(journal);
		DAC.publish(query, data);
		query = "INSERT INTO roles VALUES (?,?);";
		login.addroll("Reviewer", getEmail());
	}

	public void registerCoAuthor(String title, String firstname, String lastname, String email, String password,
			String uni) throws NoSuchAlgorithmException, SQLException {
		// and by submitting,
		// they self-register as an author and register all the co-authors of the
		// article;
		String queryCheck = "SELECT * FROM user WHERE email = ? ;";
		if (DAC.checkEmail(queryCheck, email)) {
			login.loginNew(title, firstname, lastname, email, password, uni);
			login.addroll("Author", email);
			addCoAuthor(email);
		} else {
			System.out.println("Invalid Email");

		}
	}

	public void addCoAuthor(String email) throws SQLException {
		// TODO Auto-generated method stub
		String queryCheck = "SELECT * FROM submission WHERE mainauthor = TRUE AND id = ? ;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, subid);
		ArrayList<String> data = new ArrayList<String>();
		try {
			data = (rows.get(0));
			data.set(3, email);
			String query = "INSERT INTO submission(id, title, abstract, email, mainauthor, status) VALUES (?,?,?,?,FALSE,?);";
			DAC.connectionOpen();
			PreparedStatement pstmt = null;
			try {

				pstmt = DAC.getCon().prepareStatement(query);
				pstmt.setString(1, data.get(0));
				pstmt.setString(2, data.get(1));
				pstmt.setString(3, data.get(2));
				pstmt.setString(4, data.get(3));
				pstmt.setString(5, data.get(5));
				pstmt.executeUpdate();
			} catch (SQLException ex) {
				System.out.println(ex);
			} finally {
				if (pstmt != null) {
					pstmt.close();
					DAC.connectionClose();
				}
			}

		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}

	}

	public void submitRevisedArticle(String pdf) throws SQLException, FileNotFoundException {
		String query = "INSERT INTO revised VALUES (?,?);";
		DAC.connectionOpen();
		PreparedStatement pstmt = null;
		try {

			File file = new File(pdf);
			FileInputStream input = new FileInputStream(file);

			pstmt = DAC.getCon().prepareStatement(query);
			pstmt.setString(1, subid);
			pstmt.setBinaryStream(2, input);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (pstmt != null) {
				pstmt.close();
				DAC.connectionClose();
			}
		}
	}

	public static String articleTitle(String sub) throws SQLException {
		String queryCheck = "SELECT * FROM submission WHERE id = ? AND status = 'Submission' ;";
		ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck, sub);
		return rows.get(0).get(1);

	}

	public ArrayList<ArrayList<String>> checkInitialVerdict() throws SQLException {
		String queryCheck = "SELECT * FROM review WHERE subid = ? AND status = 'Initial' ;";
		ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck, subid);
		return rows;

	}

	public ArrayList<ArrayList<String>> checkFinalVerdict() throws SQLException {
		String queryCheck = "SELECT * FROM review WHERE subid = ? AND (status = 'FINAL' OR status = 'complete');";
		ArrayList<ArrayList<String>> rows = DAC.getreview(queryCheck, subid);
		return rows;

	}

	public ArrayList<ArrayList<String>> checkInitialArticle() throws SQLException, IOException {
		String queryCheck = "SELECT * FROM submission WHERE id = ? AND status = 'Submission' AND mainauthor = TRUE ;";
		String querypdf = "SELECT pdf FROM submission WHERE id = ? AND status = 'Submission' AND mainauthor = TRUE ;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, subid);
		if (rows.size() == 0) {
			return null;
		} else {
			DAC.getpdf(querypdf, subid);
			return rows;
		}

	}

	public ArrayList<ArrayList<String>> checkFinalArticle() throws SQLException, IOException {
		String queryCheck = "SELECT * FROM submission WHERE id = ? AND status = 'Submission' AND mainauthor = TRUE ;";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, subid);
		if (rows.size() == 0) {
			return rows;
		} else {
			return rows;
		}

	}
	

	public ArrayList<ArrayList<String>> criticismQuestions(String subId) throws SQLException {
		DAC.connectionOpen();
		Scanner sc = new Scanner(System.in);

		// ArrayList<String> reviewId = new ArrayList<String>();
		String getReviewers = "SELECT revid FROM errors WHERE subid = ? GROUP BY revid";
		String max = "SELECT MAX(count) FROM errors WHERE revid = ? AND subid = ?";
		String questions = "SELECT question FROM errors WHERE revid = ? AND subid = ? AND count = ?";
		String answers = "UPDATE errors SET answers = ? WHERE revid = ? AND subid = ? AND count = ?";

		ArrayList<ArrayList<String>> questionListList = new ArrayList<ArrayList<String>>();
		ArrayList<String> questionList = new ArrayList<String>();
		// ArrayList<ArrayList<String>> answerList = new ArrayList<ArrayList<String>>();

		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(getReviewers);
			PreparedStatement pstmt2 = DAC.getCon().prepareStatement(max);
			PreparedStatement pstmt3 = DAC.getCon().prepareStatement(questions);
			PreparedStatement pstmt4 = DAC.getCon().prepareStatement(answers);

			pstmt.setString(1, subId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String reviewerId = rs.getString(1); // for each revid and the current subid answer 1....n questoins

				pstmt2.setString(1, reviewerId);
				pstmt2.setString(2, subId);
				ResultSet rs2 = pstmt2.executeQuery();
				rs2.next();
				int n = rs2.getInt(1);

				for (int i = 0; i < n; i++) {

					pstmt3.setString(1, reviewerId);
					pstmt3.setString(2, subId);
					pstmt3.setInt(3, (i + 1));
					ResultSet rs3 = pstmt3.executeQuery();
					rs3.next();
					String question = rs3.getString(1);

					// adding to list
					questionList.add(question);

					rs3.close();
				}
				questionListList.add(questionList);
				questionList = new ArrayList<String>();
				rs2.close();
			}

			rs.close();
			pstmt.close();
			pstmt2.close();
			pstmt3.close();
			pstmt4.close();

		} catch (SQLException e) {
			System.out.println("respond crit " + e);
		} finally {
			DAC.connectionClose();
		}
		sc.close();
		return questionListList;

	}

	public void respondToReviews(ArrayList<ArrayList<String>> answerListList) throws SQLException {
		// make an oblect of class response
		// one main author is responsible for responding to the criticisms expressed in
		// the reviews and
		// for submitting the revised article, along with a response to each reviewer;
		// each response must include a list of answers to each of that reviewer’s
		// questions, where the
		// review contained a list of questions (or criticisms).
		DAC.connectionOpen();

		String getReviewers = "SELECT revid FROM errors WHERE subid = ? GROUP BY revid";
		String answers = "UPDATE errors SET answers = ? WHERE revid = ? AND subid = ? AND count = ?";
		ArrayList<String> answerList = new ArrayList<String>();

		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(getReviewers);
			PreparedStatement pstmt2 = DAC.getCon().prepareStatement(answers);

			pstmt.setString(1, subid);
			ResultSet rs = pstmt.executeQuery();

			int j = 0;
			while (rs.next()) {
				String reviewerId = rs.getString(1);
				answerList = answerListList.get(j);

				for (int i = 0; i < answerList.size(); i++) {
					pstmt2.setString(1, answerList.get(i));
					pstmt2.setString(2, reviewerId);
					pstmt2.setString(3, subid);
					pstmt2.setInt(4, (i + 1));
					pstmt2.executeUpdate();
				}
				j++;
			}
			rs.close();
			pstmt.close();
			pstmt2.close();
		} catch (SQLException e) {
			System.out.println("crit answer " + e);
		} finally {
			DAC.connectionClose();
		}
	}


	public ArrayList<ArrayList<String>> checkReview(String revid) throws SQLException {
		String query = "select * from errors WHERE subid = ? AND revid = ? ";
		return DAC.getErrors(query, subid, revid);

	}
	
	public static boolean checkFinalarticle(String id) throws SQLException {
		String query = "select id from revised WHERE subid = ?";
		return DAC.checkEmail(query, id);

	}

	public ArrayList<ArrayList<String>> checkResponse(String id) throws SQLException {
		// responses to the reviews
		String query = "select * from errors WHERE subid = ? AND answers is not null ";
		return DAC.getErrors(query, id);

	}
}
