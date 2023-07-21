package com.deborasroka.banky.model;

public class AccountFactory {
	
	
	public Account createAccount(AccountType acctType) {
		
		Account finalAccount = null;
		
		switch (acctType) {
		case CHECKING: finalAccount= new CheckingAccount();
			break;
		case CREDITCARD: finalAccount= new CreditCardAccount();
			break;
		case PERSONAL_LOAN: finalAccount= new PersonalLoanAccount() ;
			break;
		case SAVINGS: finalAccount= new SavingsAccount();
			break;
		default: finalAccount = null;
			break;
		}
		
		return (CheckingAccount) finalAccount;
		
	}

}
