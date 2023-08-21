package controller;

import java.io.IOException;

import dao.MyDao;
import dto.Customer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Login")
public class LoginPage extends HttpServlet{
 
     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String email=req.getParameter("emph");
    	String password=req.getParameter("password");
    	//System.out.println(password);
		//System.out.println(email);
    	
    	//verify if exist or not
    	MyDao dao=new MyDao();
    	if(email.equals("admin@jsp.com")  && password.equals("admin")) 
    	{
    		resp.getWriter().print("<h1  style='color:red'>Login succesfull</h1>");
			req.getRequestDispatcher("Admin.html").include(req, resp); 
    	}else {
    	Customer customer=dao.fetchByEmail(email);
    	
    	if(customer==null) {
    		resp.getWriter().print("<h1  style='color:red'>Invalid email</h1>");
    		req.getRequestDispatcher("Login.html").include(req, resp);    
    		}
    	else
    	{
    		if(password.equals(customer.getPassword())) {
    			resp.getWriter().print("<h1  style='color:red'>Login succesfull</h1>");
    			req.getRequestDispatcher("Customerhome.html").include(req, resp); 
    			
    		}else
    		{
    			resp.getWriter().print("<h1  style='color:red'>invalid password</h1>");
    			req.getRequestDispatcher("Login.html").include(req, resp); 
    		}
    	}
    	
    	
    }	
 }
}
