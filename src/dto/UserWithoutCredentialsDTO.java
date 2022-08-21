package dto;

public class UserWithoutCredentialsDTO {
	private String name;
	private String lastName;
	private String userName;
	private String sex;
	private String birthDate;
	private String role;
	private String type;
	private int numberOfPoints;
	
	public UserWithoutCredentialsDTO() {
		super();
	}
	
	public UserWithoutCredentialsDTO(String name, String lastName, String userName, String sex, String birthDate, String role, String type, int numberOfPoints) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.userName = userName;
		this.sex = sex;
		this.birthDate = birthDate;
		this.role = role;
		this.type = type;
		this.numberOfPoints = numberOfPoints;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumberOfPoints() {
		return numberOfPoints;
	}

	public void setNumberOfPoints(int numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}

}
