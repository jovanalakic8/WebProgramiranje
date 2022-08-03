package beans;

import java.time.LocalDateTime;

public class User {

	private String userName;
	private String password;
	private String name;
	private String lastName;
	private String sex;
	private String birthDate;
	private String role;
	private String managedSportObjectId;
	
	private User() {
		
	}
	
	public User(String userName, String password, String name, String lastName, String sex, String birthDate, String role) {
		super();
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.sex = sex;
		this.birthDate = birthDate;
		this.role = role;
		this.managedSportObjectId = null;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getManagedSportObjectId() {
		return managedSportObjectId;
	}

	public void setManagedSportObject(String managedSportObjectId) {
		this.managedSportObjectId = managedSportObjectId;
	}
	
}
