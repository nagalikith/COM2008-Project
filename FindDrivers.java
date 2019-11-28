import java.sql.*;
import java.util.ArrayList;

public class FindDrivers {
	private static Connection con;
	private ResultSet rs;

	public static void main(String[] args) throws Exception {
	}

	public ResultSet getDetails(String username) throws SQLException {
		String query = "SELECT email FROM user WHERE email = " + "\'" + username + "\'" + ";";
		System.out.println(query);
		return rs;

	}

	public static void connectionOpen() throws SQLException {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "likith", "teddy123");
		} catch (SQLException ex) {
			System.out.println("called through open");
			connectionClose();
		}
	}

	public static void connectionClose() throws SQLException {

		try {
			System.out.println(con);
			if (con != null) {
				con.close();
				System.out.println("closed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public boolean CheckEmail(String query) throws SQLException {
		connectionOpen();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next() == false) {
				System.out.println("Empty");
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

	public boolean CheckUser(String query, String password) throws SQLException {
		connectionOpen();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String pass = rs.getString(5);
			System.out.println("db   " + pass);
			System.out.println("input" + password);
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

	public void signUp(String query, ArrayList<String> data) throws SQLException {
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

	public void addrolls(String email, String role, String query) throws SQLException {
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

	public boolean CheckRole(String role, String query) throws SQLException {
		connectionOpen();
		Statement stmt = null;
		String rolesFromDB = "";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String role1 = rs.getString("role");
				//rolesFromDB = rolesFromDB +" " +role1;
				if (role1.compareTo(role) == 0) {
					System.out.println("Role already exists");
					return false;
				}
			}
			return true;

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
}
