package com.revature.templates;

import java.util.Objects;

public class TransactionsTemplate {
	
	private int accountId;
	private double transactionalAmount;
	
	public TransactionsTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TransactionsTemplate(int accountId, double transactionalAmount) {
		super();
		this.accountId = accountId;
		this.transactionalAmount = transactionalAmount;
	}
	
	public int getAccountId() {
		return accountId;
	}
	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public double getTransactionalAmount() {
		return transactionalAmount;
	}
	
	public void setTransactionalAmount(double transactionalAmount) {
		this.transactionalAmount = transactionalAmount;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(accountId, transactionalAmount);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TransactionsTemplate)) {
			return false;
		}
		TransactionsTemplate other = (TransactionsTemplate) obj;
		return accountId == other.accountId
				&& Double.doubleToLongBits(transactionalAmount) == Double.doubleToLongBits(other.transactionalAmount);
	}
	
	@Override
	public String toString() {
		return "TransactionsTemplate [accountId=" + accountId + ", transactionalAmount=" + transactionalAmount + "]";
	}
	
	

}
