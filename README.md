# RESTful-API
In this homework, it was used JAVA Programming Language and Eclipse IDE. First, it was created MAVEN Project on Eclipse IDE to create RESTful API with the functions as Create + Read + Update + Delete (CRUD). Required dependencies added to pom.xml and web.xml. 


Then, UserModel.java class was created and details of users such as userID, name, surname, and email are defined in this class.
package net.codejava.ws;


public class UserModel {

	    private int userID;
	    private String name;
	    private String surname;
	    private String email;
	 
	    public UserModel(int userID) {
	        this.userID = userID;
	    }
	 
	    public UserModel() {
	    }
	 
	    public UserModel(int userID, String name, String surname, String email) {
	        this.userID = userID;
	        this.name = name;
	        this.surname = surname;
	        this.email=email;
	    }

		public int getUserID() {
			return userID;
		}

		public void setUserID(int userID) {
			this.userID = userID;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSurname() {
			return surname;
		}

		public void setSurname(String surname) {
			this.surname = surname;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
			} 
		}

For DatabaseOp.java class sqlite-jdbc-3.27.2.1.jar was added to referenced libraries of the project. This class contains methods that perform operations such as database connection, inserting, updating, and deleting. In addition to these, methods such as listing all users and listing the user whose id is entered are also included in this class. connect() method establishes connection with database, the insert() method adds new user records to database, the getAllUsers() method lists all users in the database, the getUserbyId() method lists the user whose id is entered, the delete() method deletes the user from the database, and the update() method updates the user.


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


The UserService.java class is the class in which GET-POST-PUT-DELETE requests are performed. The @Path("/users") notation will define the application path that acts as the base URI for all resources. @Produces (MediaType.APPLICATION_JSON) notation ensures that the output is in JSON format. We have given a different path to the @GET notation methods. This means that whichever path we enter in the URL section when querying on the Internet, that method will run. It is enough to write /users to display the whole list on the screen. Then the URI will look like this: http://localhost:8080/API/rest/users. This listAllUsers() method gets all data from database by calling getAllUsers() method. If a path such as /users /GET1 is entered, the user with an id of 1 is displayed. This READ() method gets the data of entered userID. It calls getUserbyId() method. The method with @POST notation is used to add data to the API. After a POST request is sent to the API for "Create", data of user in JSON format is transferred in the database. For this, it was created CREATE() method. This method checks whether there was a user with given userID or not. Then, it creates a new user. In the method with @PUT notation, when a request like /users /PUT1 is made, user data with an id of 1 is updated with the data of the new user. In addition, these data are updated in the database. UPDATE() method updates the user of entered userID with given data. In the method with @DELETE annotation, when a request like /users /DELETE1 is made, user data with the id of 1 is deleted. DELETE() method deletes the user with entered userID if the userID exists.


package net.codejava.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

 
@Path("/users")
public class UserService {
    private DatabaseOp db=new DatabaseOp();
     
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserModel> listAllUsers() {
        return db.getAllUsers();
    }
    
    
    @GET
    @Path("GET{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response READ(@PathParam("userID") int id) {
        UserModel user = db.getUserbyId(id);
        if (user != null) {
            return Response.ok(user, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
            }
        }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response CREATE(UserModel user) throws URISyntaxException {
    	if (db.getUserbyId(user.getUserID()) != null) {
			return Response.status(Response.Status.NOT_IMPLEMENTED).build();
		}
        db.insert(user.getUserID(),user.getName(),user.getSurname(),user.getEmail());
       int newId=user.getUserID();
        URI uri = new URI("/users" + newId);
        return Response.created(uri).build();
    }
     
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("PUT{userID}")
    public Response UPDATE(@PathParam("userID") int userID, UserModel user) {
        db.Update(userID,user.getName(), user.getSurname(), user.getEmail());
        if (db.getUserbyId(userID)!=null) {
        	return Response.status(Response.Status.ACCEPTED).build();
        } else {
        	return Response.status(Response.Status.NOT_MODIFIED).build();
            }
        }
    
    @DELETE
    @Path("DELETE{userID}")
    public Response DELETE(@PathParam("userID") int userID) {
    	
        if (db.getUserbyId(userID)!=null) {
        	db.Delete(userID);
            return Response.status(Response.Status.ACCEPTED).build();
        } else {
        	return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
    }
    
Insomnia TEST
At first, there is not any data in database. With GET request, it looks like that:
<a href="https://ibb.co/WB1DJhD"><img src="https://i.ibb.co/2cB8498/GET1.png" alt="GET1" border="0"></a>

<a href="https://ibb.co/fD5PcQb"><img src="https://i.ibb.co/MB43X9b/GETDB.png" alt="GETDB" border="0"></a>

Then, it was sent a POST request and 201 Created code has been obtained. This user is also recorded in database. 

<a href="https://ibb.co/xhx8fKb"><img src="https://i.ibb.co/F6Rq0k1/POST1.png" alt="POST1" border="0"></a>

<a href="https://ibb.co/GPcN6xw"><img src="https://i.ibb.co/s3yzfmS/DB1.png" alt="DB1" border="0"></a>

If a record with the same id as the first entered is entered, this time 501 Not Implemented error code is received. Then, it was created a user with different id.

<a href="https://ibb.co/0y2t4Lk"><img src="https://i.ibb.co/QvQdSVZ/POSTHATA.png" alt="POSTHATA" border="0"></a>

<a href="https://ibb.co/BsdCZHT"><img src="https://i.ibb.co/f0BxpVr/POST2.png" alt="POST2" border="0"></a>

<a href="https://ibb.co/XXxg807"><img src="https://i.ibb.co/PxGnQXc/DB2.png" alt="DB2" border="0"></a>

Then, for PUT request, it was written http://localhost:8080/API/rest/users/PUT1. It was sent a PUT request and 201 Accepted code has been obtained. For checking, GET request was sent by writing http://localhost:8080/API/rest/users/GET1.

<a href="https://ibb.co/vdD4TLd"><img src="https://i.ibb.co/t23pvY2/PUT1.png" alt="PUT1" border="0"></a>

<a href="https://ibb.co/vsWvZTv"><img src="https://i.ibb.co/DGFg5vg/GETID1.png" alt="GETID1" border="0"></a>

<a href="https://ibb.co/N9JmKxK"><img src="https://i.ibb.co/zXB7ZsZ/DB3.png" alt="DB3" border="0"></a>

When it was tried to update a record with userID that does not exist in database, 304 Not Modified Error code has been obtained.

<a href="https://ibb.co/WkC6JLN"><img src="https://i.ibb.co/GWm0Sqf/PUTHATA.png" alt="PUTHATA" border="0"></a>

The userID of the user to be deleted has been written as http://localhost: 8080 /API/rest/users/DELETE2. That is, the user with userID 2 was deleted and the code 202 Accepted was obtained.

<a href="https://ibb.co/Prtmnjg"><img src="https://i.ibb.co/CwW1Z8Q/DELETE.png" alt="DELETE" border="0"></a>

<a href="https://ibb.co/yqBSp6F"><img src="https://i.ibb.co/GR3T9WQ/DB4.png" alt="DB4" border="0"></a>

When it was tried to delete a record with userID that does not exist, 400 Bad Request Error code has been obtained.

<a href="https://ibb.co/Tb5vBrs"><img src="https://i.ibb.co/QCGbFX4/DELETEHATA.png" alt="DELETEHATA" border="0"></a>

Finally, all users listed with the get request are as follows.

<a href="https://ibb.co/dPH1rkT"><img src="https://i.ibb.co/gScH3t2/GETSON.png" alt="GETSON" border="0"></a>

<a href="https://ibb.co/F751k2y"><img src="https://i.ibb.co/0JCLpT6/DBFINAL.png" alt="DBFINAL" border="0"></a>



