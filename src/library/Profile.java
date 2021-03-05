package library;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Profile {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String highSchool;
	private String college;
	private String town;
	private String birthday;
	private String email;
	private String phone;
	private LocalDateTime lastEdit;
	private LocalDateTime lastLogin;
	private String base64Image;
	
	public Profile(String username, String password, String firstName, String lastName, String highSchool, String college, String town, String birthday,
			String email, String phone, String base64Image) {
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.highSchool = highSchool;
		this.college = college;
		this.town = town;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.base64Image = base64Image;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getSchool() {
		return highSchool;
	}

	public String getCollege() {
		return college;
	}
	
	public String getTown() {
		return town;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getLastEdit() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return dtf.format(lastEdit);
	}

	public String getLastLogin() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return dtf.format(lastLogin);
	}
	
	public void setUsername(String a) {
		this.username = a;
	}
	
	public void setPassword(String a) {
		this.password = a;
	}

	public void setFirstName(String a) {
		this.firstName = a;
	}

	public void setLastName(String a) {
		this.lastName = a;
	}

	public void setSchool(String a) {
		this.highSchool = a;
	}
	
	public void setCollege(String a) {
		this.college = a;
	}

	public void setTown(String a) {
		this.town = a;
	}

	public void setBirthday(String a) {
		this.birthday = a;
	}

	public void setEmail(String a) {
		this.email = a;
	}

	public void setPhone(String a) {
		this.phone = a;
	}

	public void setLastEdit() {
		LocalDateTime now = LocalDateTime.now();
		this.lastEdit = now;
	}

	public void setLastLogin() {
		LocalDateTime now = LocalDateTime.now();
		this.lastLogin = now;
	}
	
	public String getBase64Image() {
		return base64Image;
	}
	
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
}
