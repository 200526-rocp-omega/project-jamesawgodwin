package com.revature.models;

import java.util.Objects;

public class AccountInfo {

		
		private int id;
		private double balance;
		private String status;
		private String type;
		private UserInfo user;
		
		public AccountInfo() {
			super();
		}

		public AccountInfo(int id, double balance, String status, String type, UserInfo user) {
			super();
			this.id = id;
			this.balance = balance;
			this.status = status;
			this.type = type;
			this.user = user;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public double getBalance() {
			return balance;
		}

		public void setBalance(double balance) {
			this.balance = balance;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public UserInfo getUser() {
			return user;
		}

		public void setUser(UserInfo user) {
			this.user = user;
		}

		@Override
		public int hashCode() {
			return Objects.hash(balance, id, status, type, user);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof AccountInfo)) {
				return false;
			}
			AccountInfo other = (AccountInfo) obj;
			return Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance) && id == other.id
					&& Objects.equals(status, other.status) && Objects.equals(type, other.type)
					&& Objects.equals(user, other.user);
		}

		@Override
		public String toString() {
			return "AccountInfo [id=" + id + ", balance=" + balance + ", status=" + status + ", type=" + type
					+ ", user=" + user + "]";
		}
		
		
		
	}


