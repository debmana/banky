package com.deborasroka.banky.model;

public enum InterestRate {
	
	creditCardInterestRate(1.3),
	personalLoanInterestRate(2.4);

	private double d;

	InterestRate(double d) {
		this.d = d;
	}
	
	public double getInterestVal() {
		
		return d;
	}
	
}
