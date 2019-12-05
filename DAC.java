

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class DAC {
	private static Connection con;
	private static ResultSet rs;

	public void main(String[] args) throws Exception {
	}
	
	public static Connection getCon() throws SQLException {
			return con;
	}

	public static void changePassword(String query, String password, String email) throws SQLException {
		connectionOpen();

		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, password);
			pstmt.setString(2, email);
			pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			connectionClose();
		}
	}

	public static ArrayList<String> getUser(String query, String email) throws SQLException {
		connectionOpen();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			ArrayList<String> row = new ArrayList<String>();

			rs.next();
			row.add(rs.getString("title"));
			row.add(rs.getString("firstname"));
			row.add(rs.getString("lastname"));
			row.add(rs.getString("email"));
			row.add(rs.getString("password"));
			row.add(rs.getString("uni"));
			pstmt.close();

			return row;
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			connectionClose();
		}
		return null;
	}

	public static void connectionOpen() throws SQLException {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "teddy123");
		} catch (SQLException ex) {
			connectionClose();
		}
	}

	public static void connectionClose() throws SQLException {

		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static boolean checkEmail(String query , String email) throws SQLException {
		connectionOpen();
		Statement stmt = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next() == false) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (stmt != null) {
				stmt.close();
				connectionClose();
			}
		}
		return false;
	}

	public static boolean checkUser(String query,String email ,String password) throws SQLException {
		connectionOpen();
		Statement stmt = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			rs.next();
			String pass = rs.getString(5);
			if (pass.compareTo(password) == 0) {
				return true;
			} else {
				// System.out.println("Wrong Password");
				return false;
			}

		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (stmt != null) {
				stmt.close();
				connectionClose();
			}
		}
		return false;
	}

	public static void signUp(String query, ArrayList<String> data) throws SQLException {
		connectionOpen();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, data.get(0));
			pstmt.setString(2, data.get(1));
			pstmt.setString(3, data.get(2));
			pstmt.setString(4, data.get(3));
			pstmt.setString(5, data.get(4));
			pstmt.setString(6, data.get(5));
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (stmt != null) {
				stmt.close();
				connectionClose();
			}
		}
	}

	public static void addrolls(String email, String role, String query) throws SQLException {
		connectionOpen();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, role);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (stmt != null) {
				stmt.close();
				connectionClose();
			}
		}

	}

	public static boolean checkRole(String role, String query) throws SQLException {
		connectionOpen();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String role1 = rs.getString("role");
				// rolesFromDB = rolesFromDB +" " +role1;
				if (role1.compareTo(role) == 0) {
					return false;
				}
			}
			return true;

		} catch (

		SQLException ex) {
			System.out.println(ex);
		} finally {
			if (stmt != null) {
				stmt.close();
				connectionClose();
			}
		}
		return false;
	}

	public static void publish(String query, ArrayList<String> publishData) throws SQLException, FileNotFoundException {
		connectionOpen();
		PreparedStatement pstmt = null;
		try {

			File file = new File(publishData.get(4));
			FileInputStream input = new FileInputStream(file);

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, publishData.get(0));
			pstmt.setString(2, publishData.get(1));
			pstmt.setString(3, publishData.get(2));
			pstmt.setString(4, publishData.get(3));
			pstmt.setBinaryStream(5, input);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (pstmt != null) {
				pstmt.close();
				connectionClose();
			}
		}
	}
	
	

	public static ArrayList<ArrayList<String>> getArticle(String query, String email) throws SQLException {
		// TODO Auto-generated method stub
		connectionOpen();
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			ArrayList<String> row = new ArrayList<String>();

			while (rs.next()) {
				row = new ArrayList<String>();
				row.add(rs.getString("id"));
				row.add(rs.getString("title"));
				row.add(rs.getString("abstract"));
				row.add(rs.getString("email"));
				row.add(rs.getString("mainauthor"));
				row.add(rs.getString("status"));
				rows.add(row);
			}
			pstmt.close();
			return rows;
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			connectionClose();
		}
		return null;

	}
	
	public static ArrayList<ArrayList<String>> checkreview(String query,String subid,String revid) throws SQLException {
		// TODO Auto-generated method stub
		connectionOpen();
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, subid);
			pstmt.setString(2, revid);
			rs = pstmt.executeQuery();

			ArrayList<String> row = new ArrayList<String>();

			while (rs.next()) {
				row = new ArrayList<String>();
				row.add(rs.getString(6));
				rows.add(row);
			}
			pstmt.close();
			return rows;
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			connectionClose();
		}
		return null;

	}

	public static ArrayList<ArrayList<String>> getreview(String query , String value) throws SQLException {
		// TODO Auto-generated method stub
		connectionOpen();
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			ArrayList<String> row = new ArrayList<String>();

			while (rs.next()) {
				row = new ArrayList<String>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
				rows.add(row);
			}
			pstmt.close();
			return rows;
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			connectionClose();
		}
		return null;

	}

	public static void insertReviewID(String query, String email, String subid) throws SQLException {
		connectionOpen();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, subid);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (pstmt != null) {
				pstmt.close();
				connectionClose();
			}
		}
	}

	public static void adderror(String query,String revid, String subid, ArrayList<String> errors, String role)
			throws SQLException {
		// TODO Auto-generated method stub
		connectionOpen();
		PreparedStatement pstmt = null;
		try {
			int count = 1;
			for (String error : errors) {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, revid);
				pstmt.setString(2, subid);
				pstmt.setString(3, error);
				pstmt.setInt(4, count);
				pstmt.setString(5, role);
				pstmt.executeUpdate();
				count++;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (pstmt != null) {
				pstmt.close();
				connectionClose();
			}
		}

	}

	public static void addinitialsub(String query, String revid ,String sum, String typo, String judgement, String status,
			String subid) throws SQLException {
		// TODO Auto-generated method stub
		connectionOpen();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, sum);
			pstmt.setString(2, typo);
			pstmt.setString(3, judgement);
			pstmt.setString(4, status);
			pstmt.setString(5, revid);
			pstmt.setString(6, subid);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (pstmt != null) {
				pstmt.close();
				connectionClose();
			}
		}

	}

	public static void addFinalSub(String query, String revid, String subid, String sum, String typo, String judgement,
			String status) throws SQLException {
		// TODO Auto-generated method stub
		connectionOpen();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, revid);
			pstmt.setString(2, subid);
			pstmt.setString(3, sum);
			pstmt.setString(4, typo);
			pstmt.setString(5, judgement);
			pstmt.setString(6, status);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (pstmt != null) {
				pstmt.close();
				connectionClose();
			}
		}

	}

	public static void getpdf(String query, String subid) throws SQLException, IOException {
		// TODO Auto-generated method stub
		connectionOpen();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, subid);
			rs = pstmt.executeQuery();

			File file = new File("DOWNLOADED.pdf");
			FileOutputStream output = new FileOutputStream(file);
			
			System.out.println("Writing to file " + file.getAbsolutePath());
			
			while (rs.next()) {
				InputStream input = rs.getBinaryStream("pdf");
				byte[] buffer = new byte[1024];
				while (input.read(buffer) > 0) {
					output.write(buffer);
				}
			}
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			connectionClose();
			
		}
	}
	
	public static ArrayList<ArrayList<String>> getErrors(String query , String id, String role) throws SQLException {		
		connectionOpen();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, role);
			ResultSet rs = pstmt.executeQuery();

			ArrayList<String> row = new ArrayList<String>();

			ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
			while (rs.next()) {
				row = new ArrayList<String>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				rows.add(row);
			}
			pstmt.close();
			return rows;
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			DAC.connectionClose();
		}
		return null;
	}

}
