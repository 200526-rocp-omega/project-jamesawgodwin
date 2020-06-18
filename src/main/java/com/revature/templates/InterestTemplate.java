package com.revature.templates;

import java.util.Objects;

public class InterestTemplate {
	
	private int numOfMonths;

	public InterestTemplate(int numOfMonths) {
		super();
		this.numOfMonths = numOfMonths;
	}

	public InterestTemplate() {
		// TODO Auto-generated constructor stub
	}

	public int getNumOfMonths() {
		return numOfMonths;
	}

	public void setNumOfMonths(int numOfMonths) {
		this.numOfMonths = numOfMonths;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numOfMonths);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof InterestTemplate)) {
			return false;
		}
		InterestTemplate other = (InterestTemplate) obj;
		return numOfMonths == other.numOfMonths;
	}

	@Override
	public String toString() {
		return "InterestTemplate [numOfMonths=" + numOfMonths + "]";
	}


}
