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
