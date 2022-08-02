package dto;

public class UserWithoutCredentialsDTO {
	private String name;
	private String lastName;
	private String sex;
	private String birthDate;
	private String role;
	
	public UserWithoutCredentialsDTO() {
		super();
	}
	
	public UserWithoutCredentialsDTO(String name, String lastName, String sex, String birthDate, String role) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.sex = sex;
		this.birthDate = birthDate;
		this.role = role;
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

}
