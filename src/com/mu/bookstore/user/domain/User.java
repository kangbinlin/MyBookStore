package com.mu.bookstore.user.domain;

public class User {
	public String uid;
	public String username;
	public String password;
	public String email;
	public String code;
	public boolean state;
	public User() {
		super();
		
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password="
				+ password + ", email=" + email + ", code=" + code + ", state="
				+ state + "]";
	}
	public User(String uid, String username, String password, String email,
			String code, boolean state) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.code = code;
		this.state = state;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	
	

}
