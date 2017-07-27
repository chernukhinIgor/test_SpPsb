package hello.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity // This tells Hibernate to make a table out of this class
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private int userId;

	@NotNull
	@Column(name="email", unique = true)
	private String email;

	@Column(name="password")
	private String password;

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name="salt")
	private String salt;

	@Column(name="name")
	private String name;

	@Column(name="surname")
	private String surname;

	@Column(name="telephone")
	private String telephone;

	@Column(name="gender")
	private String gender;

	@Column(name="datebirth")
	private String birth;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "User {" +
				"userId=" + userId +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				'}';
	}


}

