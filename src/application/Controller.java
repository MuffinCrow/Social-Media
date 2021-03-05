package application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import library.Profile;
import library.ProfileDAO;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	
	private ProfileDAO dao;
	private String enterName = "";
	private String enterPass = "";
	public Profile currentProfile = null;
	private InputStream inputStream = null;
	
	public void init() {
		final String url = getServletContext().getInitParameter("JDBC-URL");
		final String username = getServletContext().getInitParameter("JDBC-USERNAME");
		final String password = getServletContext().getInitParameter("JDBC-PASSWORD");
		
		dao = new ProfileDAO(url, username, password);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		enterName = request.getParameter("enterName");
		enterPass = request.getParameter("enterPass");
		inputStream = null;
		Part filePart = request.getPart("base64Image");
		if (filePart != null) {
			inputStream = filePart.getInputStream();
		}
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getServletPath();
		
		try {
			switch (action) {
			case "/home": homeMenu(request, response); break;
			case "/signup": signupForm(request, response); break;
			case "/profile": profileScreen(request, response); break;
			case "/edit": editProfile(request, response); break;
			default: loginScreen(request, response); break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		/*try {
			switch (action) {
				case "/search": searchProducts(request, response); break;
				case "/add": // intentionally fall through
				case "/edit": showEditForm(request, response); break;
				case "/insert": insertProducts(request, response); break;
				case "/update": updateProducts(request, response); break;
				default: viewProducts(request, response); break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}*/
	}
	
	private void viewProfiles(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Profile> profiles = dao.getProfiles();
		request.setAttribute("profiles", profiles);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);
	}
	
	private void loginScreen(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		enterName = "";
		enterPass = "";
		currentProfile = null;
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}
	
	private void homeMenu(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String action = request.getParameter("action");
		
		switch (action) {
			case "login":
				if (!enterName.equals("") && !enterPass.equals("")) {
					String tempName = enterName;
					enterName = "";
					String tempPass = enterPass;
					enterPass = "";
					
					if (dao.getProfile(tempName) != null && dao.getProfile(tempName).getPassword().equals(tempPass)) {
						currentProfile = dao.getProfile(tempName);
						currentProfile.setLastLogin();
						viewProfiles(request, response);
					} else {
						RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
						dispatcher.forward(request, response);
						PrintWriter out = response.getWriter();  
						response.setContentType("text/html");  
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Incorrect Username or Password');");  
						out.println("</script>");
					}
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
					dispatcher.forward(request, response);
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Incorrect Username or Password');");  
					out.println("</script>");
				}
				break;
			case "signup":
				if (request.getParameter("username").equals("") || request.getParameter("username") == null ||
						request.getParameter("password").equals("") || request.getParameter("password") == null ||
						request.getParameter("firstName").equals("") || request.getParameter("firstName") == null ||
						request.getParameter("lastName").equals("") || request.getParameter("lastName") == null) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
					dispatcher.forward(request, response);
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Please fill out the required fields.');");  
					out.println("</script>");
				} else if (dao.getProfile(request.getParameter("username")) != null) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
					dispatcher.forward(request, response);
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Username already taken. Please enter a different username.');");  
					out.println("</script>");
				} else {
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					String firstName = request.getParameter("firstName");
					String lastName = request.getParameter("lastName");
					String highSchool = request.getParameter("highSchool");
					String college = request.getParameter("college");
					String town = request.getParameter("town");
					String birthday = request.getParameter("birthday");
					String email = request.getParameter("email");
					String phone = request.getParameter("phone");
					Blob base64Image = null;
					if (inputStream != null) {
						base64Image = (Blob) inputStream;
					}
					dao.insertProfile(username, password, firstName, lastName, highSchool, college, town, birthday, email, phone, base64Image);
					
					currentProfile = dao.getProfile(username);
					response.sendRedirect(request.getContextPath() + "/home");
				}
				break;
			case "update":
				if (request.getParameter("username").equals(currentProfile.getUsername())) {
					String password = request.getParameter("password");
					String firstName = request.getParameter("firstName");
					String lastName = request.getParameter("lastName");
					String highSchool = request.getParameter("highSchool");
					String college = request.getParameter("college");
					String town = request.getParameter("town");
					String birthday = request.getParameter("birthday");
					String email = request.getParameter("email");
					String phone = request.getParameter("phone");
					Blob blob = null;
					if (inputStream != null) {
						blob = (Blob) inputStream;
					}
					InputStream inputStream = blob.getBinaryStream();
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					byte[] buffer = new byte[4096];
					int bytesRead = -1;
					
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);					
					}
					
					byte[] imageBytes = outputStream.toByteArray();
					String base64Image = Base64.getEncoder().encodeToString(imageBytes);
					
					currentProfile.setPassword(password);
					currentProfile.setFirstName(firstName);
					currentProfile.setLastName(lastName);
					currentProfile.setSchool(highSchool);
					currentProfile.setCollege(college);
					currentProfile.setTown(town);
					currentProfile.setBirthday(birthday);
					currentProfile.setEmail(email);
					currentProfile.setPhone(phone);
					currentProfile.setBase64Image(base64Image);
					currentProfile.setLastEdit();
					
					dao.updateProfile(currentProfile);
					viewProfiles(request, response);
				} else if (!request.getParameter("username").equals(currentProfile.getUsername()) && dao.getProfile(request.getParameter("username")) == null) {
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					String firstName = request.getParameter("firstName");
					String lastName = request.getParameter("lastName");
					String highSchool = request.getParameter("highSchool");
					String college = request.getParameter("college");
					String town = request.getParameter("town");
					String birthday = request.getParameter("birthday");
					String email = request.getParameter("email");
					String phone = request.getParameter("phone");
					Blob blob = null;
					if (inputStream != null) {
						blob = (Blob) inputStream;
					}
					InputStream inputStream = blob.getBinaryStream();
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					byte[] buffer = new byte[4096];
					int bytesRead = -1;
					
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);					
					}
					
					byte[] imageBytes = outputStream.toByteArray();
					String base64Image = Base64.getEncoder().encodeToString(imageBytes);
					
					currentProfile.setUsername(username);
					currentProfile.setPassword(password);
					currentProfile.setFirstName(firstName);
					currentProfile.setLastName(lastName);
					currentProfile.setSchool(highSchool);
					currentProfile.setCollege(college);
					currentProfile.setTown(town);
					currentProfile.setBirthday(birthday);
					currentProfile.setEmail(email);
					currentProfile.setPhone(phone);
					currentProfile.setBase64Image(base64Image);
					currentProfile.setLastEdit();
					
					dao.updateProfile(currentProfile);
					viewProfiles(request, response);
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
					dispatcher.forward(request, response);
					PrintWriter out = response.getWriter();  
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Username already taken. Please enter a different username.');");  
					out.println("</script>");
				}
			default:
				viewProfiles(request, response);
				break;
		}
	}
	
	private void signupForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
		dispatcher.forward(request, response);
	}
	
	private void editProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		try {
		    Profile profile = currentProfile;
		    request.setAttribute("profile", profile);
		} catch (NumberFormatException e) {
			// this is expected for empty forms (i.e., without a valid id)
		} finally {
		    RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
		    dispatcher.forward(request, response);
		}
	}
	
	private void profileScreen(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		try {
			final String username = request.getParameter("username");
		    
		    Profile profile = dao.getProfile(username);
		    request.setAttribute("profile", profile);
		} catch (NumberFormatException e) {
			// this is expected for empty forms (i.e., without a valid id)
		} finally {
		    RequestDispatcher dispatcher = request.getRequestDispatcher("productform.jsp");
		    dispatcher.forward(request, response);
		}
	}
}
