package classesTest;

public class User {

	private String title;
	private String firstName;
	private String surName;
	private String email;
	private String password;
	private String uniAffiliation;
	
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
	
	public void email(String email) {
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
	public void changePassword() {
		getFromDB();
	}
	
	
	public void getFromDB() {
		String query = "SELECT * FROM user WHERE email = ?";
		String email = this.email;
	}
	
}
