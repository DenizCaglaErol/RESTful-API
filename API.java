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
public class API {
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
