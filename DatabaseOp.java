package net.codejava.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOp {
	
	Connection conn=null;

	public Connection connect()  
	    {
		 
		Connection c = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Baran\\Desktop\\cmp.db");
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Database opened successfully");
	      
	     return c; 
	}
	
	
	public void insert(int userID,String username,String usersurname,String useremail) {
		
        String sql = "INSERT INTO cmp (userID,name,surname,email) " +
              "VALUES (?,?,?,?);";
        try (Connection conn = this.connect();
        		PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setString(2, username);
            pstmt.setString(3, usersurname);
            pstmt.setString(4, useremail);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        }
	
	public List<UserModel>getAllUsers() {
		List<UserModel> user=new ArrayList<UserModel>();
		
		String sql = "SELECT*FROM cmp";
		ResultSet rs=null;
        try (Connection conn = this.connect();
        		Statement stmt  = conn.createStatement();){
        	rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                UserModel u=new UserModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                int id=u.getUserID();
                u.setUserID(id);
                user.add(u);
            }
            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
            }
        }
	
	public UserModel getUserbyId(int userID) {
		UserModel user=null;
		String sql = "SELECT*FROM cmp where userID="+userID;
		ResultSet rs=null;
		
		 try (Connection conn = this.connect();
				 Statement stmt  = conn.createStatement();){
			 rs    = stmt.executeQuery(sql);
	        	
	            while (rs.next()) {
	                user=new UserModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
	                }
	           	} catch (SQLException e) {
	            System.out.println(e.getMessage());
	            }
		 return user;
	}
	
	public void Update(int id,String username,String usersurname,String useremail) {
		 String sql = "UPDATE cmp SET userID= ?, name = ? , surname = ?, email=?" + "WHERE userID = ?";
		 try (Connection conn = this.connect();
				 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			 pstmt.setInt(1, id);
			 pstmt.setString(2, username);
		     pstmt.setString(3, usersurname);
		     pstmt.setString(4, useremail);
		     pstmt.setInt(5, id);
		     pstmt.executeUpdate();
		     
		 } catch (SQLException e) {
			 System.out.println(e.getMessage());
			 }
		 }
	
	public void Delete(int id) {
		String sql = "DELETE FROM cmp WHERE userID = ?";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        }
	}
