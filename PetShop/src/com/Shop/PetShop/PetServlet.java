package com.Shop.PetShop;

import java.io.IOException;
import java.util.Properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Shop.PetShop.model.*;

/**
 * Servlet implementation class PetServlet
 */
public class PetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		try {
				PrintWriter out =response.getWriter();
				out.println("<html><body>");
				
				InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
				Properties props = new Properties();
				props.load(in);
				
				
				DBConnection conn = new DBConnection(props.getProperty("url"),props.getProperty("userid"),props.getProperty("password"));
				
				Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rst =stmt.executeQuery("select * from products");
                out.println("<TABLE><TR><TH>List</TH><TH>of</TH><TH>Inventory</TH></TR>");
                out.println("</table>");
                out.println("<TABLE><TR><TH>NAME</TH></TR>");
                out.println("</table>");
                
                while (rst.next()) {
                	out.println(rst.getInt("id")+": "+rst.getString("name")+"<Br>");
                }
                
                
                stmt.close();
                
                out.println("</body></html>");
                conn.closeConnection();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
        } catch (SQLException e) {
                e.printStackTrace();
        }

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

				try {
					PrintWriter out =response.getWriter();
					out.println("<html><body>");
					
					String fname =request.getParameter("fname");
					
					InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
					Properties props = new Properties();
					props.load(in);
					
					
					DBConnection conn = new DBConnection(props.getProperty("url"),props.getProperty("userid"),props.getProperty("password"));
					
					Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	                ResultSet rst =stmt.executeQuery("select * from products where id ="+fname);
	                
	                out.println("<TABLE><TR><TH>NAME</TH><TH>  COLOR</TH><TH>  PRICE</TH></TR>");
	                out.println("</table>");
	                
	                while (rst.next()) {
	                	out.println(rst.getInt("id")+": "+rst.getString("name")+", "+rst.getString("color")+", $"+rst.getBigDecimal("price")+".00<Br>");
	                }
	                
	                
	                stmt.close();
	                
	                out.println("</body></html>");
	                conn.closeConnection();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
	        } catch (SQLException e) {
	                e.printStackTrace();
	        }

		//doGet(request, response);
	}

}
