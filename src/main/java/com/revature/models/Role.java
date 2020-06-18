package com.revature.models;

import java.util.Objects;

	public class Role {
	
		private int roleId; // primary key
		private String role; // not null, unique
		  
		public Role() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Role(int roleId, String role) {
			super();
			this.roleId = roleId;
			this.role = role;
		}
		public int getRoleId() {
			return roleId;
		}
		public void setRoleId(int roleId) {
			this.roleId = roleId;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		@Override
		public int hashCode() {
			return Objects.hash(role, roleId);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Role)) {
				return false;
			}
			Role other = (Role) obj;
			return Objects.equals(role, other.role) && roleId == other.roleId;
		}
		@Override
		public String toString() {
			return "Role [roleId=" + roleId + ", role=" + role + "]";
		}

	  
}
