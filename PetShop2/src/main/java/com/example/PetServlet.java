package com.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Product;


/**
 * Servlet implementation class PetsServlet
 */
public class PetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
        try {
        		PrintWriter out = response.getWriter();
        		out.println("<html><body>");
        		String petname = request.getParameter("name");
        		String petcolor = request.getParameter("color"); 
        		String petprice = request.getParameter("price");
        		Double petcost = Double.parseDouble(petprice);
        	
        	   SessionFactory factory = HibernateUtil.getSessionFactory();
               
               Session session = factory.openSession();
               
               session.beginTransaction();
               Product newPet = new Product();
               newPet.setName(petname);
               newPet.setColor(petcolor);
               newPet.setPrice(petcost);
               session.save(newPet);
               session.getTransaction().commit();
               session.close();
                             
                out.println("<b>Product Listing</b><br>");
                        out.println("ID: " + String.valueOf(newPet.getID()) + ", Name: " + newPet.getName() +
                                        ", Price: " + String.valueOf(newPet.getPrice()) + ", Color: " + newPet.getColor().toString() + "<br>");
     
                out.println("<form action ='pets' method='post'>");
                out.println("<input type='submit' value = 'list pets'>");
                out.println("<a href='addpet.jsp'>Add another Pet</a><br>");
            out.println("</body></html>");
            
            
        }catch (InputMismatchException ex) {
    		PrintWriter out = response.getWriter();
    		out.println("<html><body>");
            out.println("an input was incorrectly typed. Please try again <br>");
            out.println("<a href='addpet.jsp'>Return</a><br>");
            out.println("</body></html>");
       }catch (NumberFormatException exx) {
   		   PrintWriter out = response.getWriter();
   		   out.println("<html><body>");
           out.println("an input was incorrectly typed. Please try again <br>");
           out.println("<a href='addpet.jsp'>Return</a><br>");
           out.println("</body></html>");
       } catch(Exception e) {
        	throw e;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
            SessionFactory factory = HibernateUtil.getSessionFactory();
            
            Session session = factory.openSession();
            List<Product> list = session.createQuery("from Product", Product.class).list();
            session.close();
            
             PrintWriter out = response.getWriter();
             out.println("<html><body>");
             out.println("<b>Product Listing</b><br>");
             if(list.size()==0) {
            	 out.println("Current Inventory is Empty <br>");
             }
             for(Product p: list) {
                     out.println("ID: " + String.valueOf(p.getID()) + ", Name: " + p.getName() +
                                     ", Price: " + String.valueOf(p.getPrice()) + ", Color: " + p.getColor().toString() + "<br>");
             }
             out.println("<a href='addpet.jsp'>Add another Pet</a><br>");
             out.println("</body></html>");
         
         
     } catch (Exception ex) {
             throw ex;
     } 		
	}
}