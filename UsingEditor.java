package classesTest;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class UsingEditor {
	
	
	public static ArrayList<ArrayList<String>> criticismQuestions(String subId) throws SQLException {
		DAC.connectionOpen();
		Scanner sc = new Scanner(System.in);
		
		//ArrayList<String> reviewId = new ArrayList<String>();
		String getReviewers = "SELECT revid FROM errors WHERE subid = ? GROUP BY revid";
		String max = "SELECT MAX(count) FROM errors WHERE revid = ? AND subid = ?";
		String questions = "SELECT question FROM errors WHERE revid = ? AND subid = ? AND count = ?";
		String answers = "UPDATE errors SET answer = ? WHERE revid = ? AND subid = ? AND count = ?";
		
		ArrayList<ArrayList<String>> questionListList = new ArrayList<ArrayList<String>>();
		ArrayList<String> questionList = new ArrayList<String>();
		//ArrayList<ArrayList<String>> answerList = new ArrayList<ArrayList<String>>();
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(getReviewers);
			PreparedStatement pstmt2 = DAC.getCon().prepareStatement(max);
			PreparedStatement pstmt3 = DAC.getCon().prepareStatement(questions);
			PreparedStatement pstmt4 = DAC.getCon().prepareStatement(answers);
			
			pstmt.setString(1, subId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String reviewerId = rs.getString(1); //for each revid and the current subid answer 1....n questoins
				
				pstmt2.setString(1, reviewerId);
				pstmt2.setString(2, subId);
				ResultSet rs2 = pstmt2.executeQuery();
				rs2.next();
				int n = rs2.getInt(1);
				
				for(int i=0; i<n; i++) {
					
					pstmt3.setString(1, reviewerId);
					pstmt3.setString(2, subId);
					pstmt3.setInt(3, (i+1));
					ResultSet rs3 = pstmt3.executeQuery();
					rs3.next();
					String question = rs3.getString(1);
					
					//adding to list
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
			
		}
		catch(SQLException e) {
			System.out.println("respond crit "+e);
		}
		finally {
			DAC.connectionClose();
		}
		sc.close();
		return questionListList;
		
	}
	
	public static void addCriticismAnswers(String subId, ArrayList<ArrayList<String>> answerListList) throws SQLException {
		DAC.connectionOpen();
		
		String getReviewers = "SELECT revid FROM errors WHERE subid = ? GROUP BY revid";
		String answers = "UPDATE errors SET answer = ? WHERE revid = ? AND subid = ? AND count = ?";
		ArrayList<String> answerList = new ArrayList<String>();
		
		try {
			PreparedStatement pstmt = DAC.getCon().prepareStatement(getReviewers);
			PreparedStatement pstmt2 = DAC.getCon().prepareStatement(answers);
			
			pstmt.setString(1, subId);
			ResultSet rs = pstmt.executeQuery();
			
			int j = 0;
			while(rs.next()) {
				String reviewerId = rs.getString(1);
				answerList = answerListList.get(j);

				for(int i=0; i<answerList.size(); i++) {
					pstmt2.setString(1, answerList.get(i));
					pstmt2.setString(2, reviewerId);
					pstmt2.setString(3, subId);
					pstmt2.setInt(4, (i+1));
					pstmt2.executeUpdate();
				}
				j++;
			}
			rs.close();
			pstmt.close();
			pstmt2.close();
		}
		catch(SQLException e) {
			System.out.println("crit answer "+e);
		}
		finally {
			DAC.connectionClose();
		}
	}
	
	//public static void check
	
	public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, IOException {
		//Editor.loggedIn("rj@gmail.com");
		//Editor.retire("rj@gmail.com");
		//Editor.checkFinal();
		
		//Editor.submitVerdict();
		
		ArrayList<ArrayList<String>> x = criticismQuestions("100");
		System.out.println(x.toString() + " size "+ x.get(0).size());
		
		ArrayList<ArrayList<String>> y = x;
		
		addCriticismAnswers("100",y);
		
	}
}