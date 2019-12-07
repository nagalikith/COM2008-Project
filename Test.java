package classesTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	public static void publishNextEdition() throws SQLException {
		//the chief editor may publish the next edition of the journal when it is ready, thereby making
		//all the contained articles available to readers
		DAC.connectionOpen();
		
		try {
			String journal = "journal1";
			
			String getEditions = "SELECT * FROM currentedition WHERE journal = ?"; //list of article ID and their journal name
			PreparedStatement pstmt = DAC.getCon().prepareStatement(getEditions);
			pstmt.setString(1, journal);
			ResultSet rs = pstmt.executeQuery();
			
			String getEditionNum = "SELECT MAX(editionnum) FROM edition WHERE journal = ?";
			PreparedStatement pstmt2 = DAC.getCon().prepareStatement(getEditionNum);
			
			String insert = "INSERT INTO edition (articleid, editionnum, journal) VALUES(?,?,?)";
			PreparedStatement pstmt3 = DAC.getCon().prepareStatement(insert);
			
			while(rs.next()) {
				String articleId = rs.getString(1); //iterates over all the article IDs and inserts them into edition
				String journal = rs.getString(2);
				pstmt2.setString(1, journal);
				ResultSet rs2 = pstmt2.executeQuery();
				rs2.next();
				int editionNum = (rs2.getInt(1) +1);
				
				pstmt3.setString(1, articleId);
				pstmt3.setInt(2, editionNum);
				pstmt3.setString(3, journal);
				pstmt3.executeUpdate();
			}
		}
		catch(SQLException e) {
			System.out.println("publish next" + e);
		}
		finally {
			DAC.connectionClose();
		}		
	}
	
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		publishNextEdition();
	}

}
