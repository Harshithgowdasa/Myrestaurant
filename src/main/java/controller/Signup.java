package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.MyDao;
import dto.Customer;

//This is to map the action url to this class (should be same as form action)
@WebServlet("/signup")
//this is to make normal class to servlet class
@MultipartConfig
//to accept multi value and images
public class Signup extends HttpServlet {
	@Override
	// when there is form and we want data to be secured so doPost
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fullname = req.getParameter("name");
		String country = req.getParameter("country");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		long mobile = Long.parseLong(req.getParameter("mobile"));
		String gender = req.getParameter("gender");
		LocalDate dob = LocalDate.parse(req.getParameter("date"));
		int age = Period.between(dob, LocalDate.now()).getYears();// period is inbuilt class in java

		// logic to receive image and convert into byte[]
		Part pic = req.getPart("image");
		byte[] picture = null;
		picture = new byte[pic.getInputStream().available()];
		pic.getInputStream().read(picture);

//		System.out.println(fullname);
//		System.out.println(email);
//		System.out.println(password);
//		System.out.println(mobile);
//		System.out.println(gender);
//		System.out.println(bob);
//		System.out.println(age);
		MyDao d = new MyDao();
		
		//logic to verify email and mobile are unique or not 
		if(d.fetchByEmail(email)==null && d.fetchByMobile(mobile)==null)
		{
		//loading value inside the object
		Customer customer = new Customer();
		
		customer.setAge(age);
		customer.setCountry(country);
		customer.setDob(dob);
		customer.setEmail(email);
		customer.setFullname(fullname);
		customer.setGender(gender);
		customer.setMobile(mobile);
		customer.setPassword(password);
		customer.setPicture(picture);

		///MyDao d = new MyDao();
		//Persisting
		d.save(customer);

		resp.getWriter().print("<h1 style='color:green'>Account created Successfully</h1>");
		req.getRequestDispatcher("Login.html").include(req, resp); 

	    }
		else {
			resp.getWriter().print("<h1 style='color:red'>Account already exist</h1>");
			req.getRequestDispatcher("signup.html").include(req, resp); 
		}
		
	}
	

}
