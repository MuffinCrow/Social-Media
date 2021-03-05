package library;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ProfileDAO {

	private final String url;
	private final String username;
	private final String password;
	
	public ProfileDAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public Profile getProfile(String username) throws SQLException, IOException {
		final String sql = "SELECT * FROM profiles WHERE username = ?";
		
		Profile profile = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, username);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			String password = rs.getString("password");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String highSchool = rs.getString("highSchool");
			String college = rs.getString("college");
			String town = rs.getString("town");
			String birthday = rs.getString("birthday");
			String email = rs.getString("email");
			String phone = rs.getString("phone");
			//String base64Image = rs.getString("base64Image");
			Blob blob = rs.getBlob("base64Image");
			
			InputStream inputStream = blob.getBinaryStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);					
			}
			
			byte[] imageBytes = outputStream.toByteArray();
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);
			
			profile = new Profile(username, password, firstName, lastName, highSchool, college, town, birthday, email, phone, base64Image);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return profile;
	}
	
	public List<Profile> getProfiles() throws SQLException, IOException {
		final String sql = "SELECT * FROM profiles ORDER BY username";
		
		List<Profile> profiles = new ArrayList<>();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			String username = rs.getString("username");
			String password = rs.getString("password");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String highSchool = rs.getString("highSchool");
			String college = rs.getString("college");
			String town = rs.getString("town");
			String birthday = rs.getString("birthday");
			String email = rs.getString("email");
			String phone = rs.getString("phone");
			//String base64Image = rs.getString("base64Image");
			Blob blob = rs.getBlob("base64Image");
			
			InputStream inputStream = blob.getBinaryStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);					
			}
			
			byte[] imageBytes = outputStream.toByteArray();
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);
			
			profiles.add(new Profile(username, password, firstName, lastName, highSchool, college, town, birthday, email, phone, base64Image));
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
		return profiles;
	}
	
	public boolean insertProfile(String username, String password, String firstName, String lastName, String highSchool, String college,
			String town, String birthday, String email, String phone, Blob base64Image) throws SQLException {
		final String sql = "INSERT INTO profiles (username, password, firstName, lastName, highSchool, college, town, birthday, email, phone, " +
			", lastEdit, lastLogin, base64Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		pstmt.setString(3, firstName);
		pstmt.setString(4, lastName);
		pstmt.setString(5, highSchool);
		pstmt.setString(6, college);
		pstmt.setString(7, town);
		pstmt.setString(8, birthday);
		pstmt.setString(9, email);
		pstmt.setString(10, phone);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		pstmt.setString(11, dtf.format(now));
		pstmt.setString(12, dtf.format(now));
		pstmt.setBlob(13, base64Image);
		
		int affected = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return affected == 1;
	}
	
	public boolean updateProfile(Profile profile) throws SQLException {
		final String sql = "UPDATE profiles SET firstName = ?, lastName = ?, highSchool = ?, college = ?,  town = ?, birthday = ?, " + 
				"email = ?, phone = ?, lastEdit = ?, base64Image = ? WHERE username = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, profile.getFirstName());
		pstmt.setString(2, profile.getLastName());
		pstmt.setString(3, profile.getSchool());
		pstmt.setString(4, profile.getCollege());
		pstmt.setString(5, profile.getTown());
		pstmt.setString(6, profile.getBirthday());
		pstmt.setString(7, profile.getEmail());
		pstmt.setString(8, profile.getPhone());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		pstmt.setString(9, dtf.format(now));
		byte[] dataBytes = Base64.getDecoder().decode(profile.getBase64Image());
		Blob blob = new javax.sql.rowset.serial.SerialBlob(dataBytes);
		pstmt.setBlob(10, blob);
		pstmt.setString(11, profile.getUsername());
		
		int affected = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return affected == 1;
	}
	
	public boolean deleteProfile(Profile profile) throws SQLException {
		final String sql = "DELETE FROM profiles WHERE username = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, profile.getUsername());
		
		int affected = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return affected == 1;
	}
	
	private Connection getConnection() throws SQLException {
		final String driver = "com.mysql.cj.jdbc.Driver";
    
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(url, username, password);
	}
	
}
