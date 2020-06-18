package com.revature.templates;

import java.util.Objects;

public class JointAccountTemplate {
	
	private int user_id;
	private int account_id;

	public JointAccountTemplate() {
		// TODO Auto-generated constructor stub
	}

	public JointAccountTemplate(int user_id, int account_id) {
		super();
		this.user_id = user_id;
		this.account_id = account_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account_id, user_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof JointAccountTemplate)) {
			return false;
		}
		JointAccountTemplate other = (JointAccountTemplate) obj;
		return account_id == other.account_id && user_id == other.user_id;
	}

	@Override
	public String toString() {
		return "JointAccountTemplate [user_id=" + user_id + ", account_id=" + account_id + "]";
	}
	
	

}
