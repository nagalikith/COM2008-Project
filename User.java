package classesTest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {

	private String title;
	private String firstName;
	private String surName;
	private String email;
	private String password;
	private String uniAffiliation;
	
	public User(String t,String fn,String sn,String e,String p,String u) {
		title = t;
		firstName = fn;
		surName = sn;
		email = e;
		password = p;
		uniAffiliation = u;
	}
	
	//all the setters
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setSurname(String surName) {
		this.surName = surName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setUniAffiliation(String uniAffiliation) {
		this.uniAffiliation = uniAffiliation;
	}
	
	//all the getters
	public String getTitle() {
		return title;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSurName() {
		return surName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getUniAffiliation() {
		return uniAffiliation;
	}
	
	//rest of the functions
	public void changePassword(String password) throws SQLException {
		String query = "UPDATE user SET password = ? WHERE email = ?;";
		DAC.changePassword(query, password, this.email);
	}
	
	
	public User getFromDB() throws SQLException {
		String query = "SELECT * FROM user WHERE email = ?";
		String email = this.email;
		ArrayList<String> user = DAC.getUser(query,email);
		User obj = new User(user.get(0), user.get(1), user.get(2), user.get(3), user.get(4), user.get(5));
		
		return obj;
	}
	
}
