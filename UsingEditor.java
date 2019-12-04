package classesTest;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class UsingEditor {
	
	
	public static void respondCriticism(String subId) throws SQLException {
		DAC.connectionOpen();
		Scanner sc = new Scanner(System.in);
		
		//ArrayList<String> reviewId = new ArrayList<String>();
		String getReviewers = "SELECT revid FROM errors WHERE subid = ? GROUP BY revid";
		String max = "SELECT MAX(count) FROM errors WHERE revid = ? AND subid = ?";
		String questions = "SELECT question FROM errors WHERE revid = ? AND subid = ? AND count = ?";
		String answers = "UPDATE errors SET answer = ? WHERE revid = ? AND subid = ? AND count = ?";
		
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
				
				System.out.println("ans reviewer "+reviewerId);
				for(int i=0; i<n; i++) {
					pstmt3.setString(1, reviewerId);
					pstmt3.setString(2, subId);
					pstmt3.setInt(3, (i+1));
					ResultSet rs3 = pstmt3.executeQuery();
					rs3.next();
					String question = rs3.getString(1);
					
					System.out.println("question "+(i+1)+" "+question);
					String answer = sc.nextLine();
					
					pstmt4.setString(1, answer);
					pstmt4.setString(2, reviewerId);
					pstmt4.setString(3, subId);
					pstmt4.setInt(4, (i+1));
					pstmt4.executeUpdate();
					
					rs3.close();
				}
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
		
	}
	
	public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, IOException {
		//Editor.loggedIn("rj@gmail.com");
		//Editor.retire("rj@gmail.com");
		//Editor.checkFinal();
		
		//Editor.submitVerdict();
		
		respondCriticism("1");
		
	}
}