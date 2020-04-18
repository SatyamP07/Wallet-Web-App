package com.wallet.app.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "C_Data_V1")
public class CustomerDetails {
	@Id
	@Column(name = "Account_id", length=7)
	private int accountId;
	@Column(name = "Account_password", length=15)
	private String accountPassword;
	@Column(name = "Name", length=20)
	private String name;
	@Column(name = "Email_id", length=25)
	private String eMail;
	@Column(name = "Mobile_number", length=10)
	private String mobileNumber;
	@Column(name = "Transaction_pin", length=4)
	private int transactionPin;
	@Column(name = "Balance", length=9)
	private float balance;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = TransactionDetails.class)
	@JoinColumn(name = "account_id", referencedColumnName = "account_id")
	private List<TransactionDetails> transactionDetails = new ArrayList<TransactionDetails>();
	
	
	public CustomerDetails() {
		super();
	}
	public CustomerDetails(String name, String eMail, String mobileNumber) {
		this.accountId = 0;
		this.name = name;
		this.eMail = eMail;
		this.mobileNumber = mobileNumber;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	
	public String getAccountPassword() {
		return accountPassword;
	}
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	public int getTransactionPin() {
		return transactionPin;
	}
	public void setTransactionPin(int transactionPin) {
		this.transactionPin = transactionPin;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public List<TransactionDetails> getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	@Override
	public String toString() {
		return "CustomerDetails [accountId=" + accountId + ", name=" + name + ", eMail=" + eMail + ", mobileNumber="
				+ mobileNumber + ", balance=" + balance + ", transactionDetails=" + transactionDetails + "]";
	}
}
