package com.wallet.app.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "T_Data_V1")
public class TransactionDetails {
	@Id
	@Column(name = "transaction_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transactionId;
	@Column(name = "account_id")
	private int accountId;
	@Column(name = "type")
	private String type;
	@Column(name = "transaction_date")
	private Date date;
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "TransactionDetails [transactionId=" + transactionId + ", accountId=" + accountId + ", type=" + type
				+ ", date=" + date + "]";
	}
	
	
}
