package com.revature.templates;

import java.util.Objects;

public class AccountTransferTemplate {
	
	private int fromAccountId;
	private int toAccountId;
	double transactionAmount;

	public AccountTransferTemplate() {
		// TODO Auto-generated constructor stub
	}

	public AccountTransferTemplate(int fromAccountId, int toAccountId, double transactionAmount) {
		super();
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.transactionAmount = transactionAmount;
	}

	public int getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(int fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public int getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(int toAccountId) {
		this.toAccountId = toAccountId;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fromAccountId, toAccountId, transactionAmount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountTransferTemplate)) {
			return false;
		}
		AccountTransferTemplate other = (AccountTransferTemplate) obj;
		return fromAccountId == other.fromAccountId && toAccountId == other.toAccountId
				&& Double.doubleToLongBits(transactionAmount) == Double.doubleToLongBits(other.transactionAmount);
	}

	@Override
	public String toString() {
		return "AccountTransferTemplate [fromAccountId=" + fromAccountId + ", toAccountId=" + toAccountId
				+ ", transactionAmount=" + transactionAmount + "]";
	}
	

}
