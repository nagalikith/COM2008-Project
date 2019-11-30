
import java.sql.*;
import java.util.ArrayList;

public class DAC {
	private static Connection con;
	private static ResultSet rs;

	public void main(String[] args) throws Exception {
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

	public static boolean checkEmail(String query) throws SQLException {
		connectionOpen();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
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

	public static boolean checkUser(String query, String password) throws SQLException {
		connectionOpen();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String pass = rs.getString(5);
			if (pass.compareTo(password) == 0) {
				return true;
			} else {
				System.out.println("Wrong Password");
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
			int rs = pstmt.executeUpdate();
			System.out.println("Success" + rs);
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

	public static void publish(String query, ArrayList<String> publishData) throws SQLException {
		connectionOpen();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, publishData.get(0));
			pstmt.setString(2, publishData.get(1));
			pstmt.setString(3, publishData.get(2));
			pstmt.setString(4, publishData.get(3));
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
	
	public static ArrayList<ArrayList<String>> getreview(String query, String email) throws SQLException {
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
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
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
	
	public static void insertReviewID(String query, String email) throws SQLException {
		connectionOpen();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
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
	
}
