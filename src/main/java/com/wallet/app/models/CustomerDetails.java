package com.wallet.app.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Customer_data")
@SequenceGenerator(name = "accountSequence", initialValue = 10000000, allocationSize = 1)
public class CustomerDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountSequence")
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
	@Column(name = "Transaction_pin")
	private String transactionPin;
	@Column(name = "Balance", length=9)
	private float balance;
	@Column(name = "National_id_type")
	private String nationalIdType;
	@Column(name = "National_id_number")
	private String nationalIdNumber;
	
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
	public String getTransactionPin() {
		return transactionPin;
	}
	public void setTransactionPin(String transactionPin) {
		this.transactionPin = transactionPin;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public String getNationalIdType() {
		return nationalIdType;
	}
	public void setNationalIdType(String nationalIdType) {
		this.nationalIdType = nationalIdType;
	}
	public String getNationalIdNumber() {
		return nationalIdNumber;
	}
	public void setNationalIdNumber(String nationalIdNumber) {
		this.nationalIdNumber = nationalIdNumber;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		result = prime * result + ((accountPassword == null) ? 0 : accountPassword.hashCode());
		result = prime * result + Float.floatToIntBits(balance);
		result = prime * result + ((eMail == null) ? 0 : eMail.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nationalIdNumber == null) ? 0 : nationalIdNumber.hashCode());
		result = prime * result + ((nationalIdType == null) ? 0 : nationalIdType.hashCode());
		result = prime * result + ((transactionDetails == null) ? 0 : transactionDetails.hashCode());
		result = prime * result + ((transactionPin == null) ? 0 : transactionPin.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDetails other = (CustomerDetails) obj;
		if (accountId != other.accountId)
			return false;
		if (accountPassword == null) {
			if (other.accountPassword != null)
				return false;
		} else if (!accountPassword.equals(other.accountPassword))
			return false;
		if (Float.floatToIntBits(balance) != Float.floatToIntBits(other.balance))
			return false;
		if (eMail == null) {
			if (other.eMail != null)
				return false;
		} else if (!eMail.equals(other.eMail))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nationalIdNumber == null) {
			if (other.nationalIdNumber != null)
				return false;
		} else if (!nationalIdNumber.equals(other.nationalIdNumber))
			return false;
		if (nationalIdType == null) {
			if (other.nationalIdType != null)
				return false;
		} else if (!nationalIdType.equals(other.nationalIdType))
			return false;
		if (transactionDetails == null) {
			if (other.transactionDetails != null)
				return false;
		} else if (!transactionDetails.equals(other.transactionDetails))
			return false;
		if (transactionPin == null) {
			if (other.transactionPin != null)
				return false;
		} else if (!transactionPin.equals(other.transactionPin))
			return false;
		return true;
	}
}
