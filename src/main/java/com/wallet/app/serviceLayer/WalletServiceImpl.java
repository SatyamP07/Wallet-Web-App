package com.wallet.app.serviceLayer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.app.daoLayer.WalletDaoInterface;
import com.wallet.app.models.CustomerDetails;
import com.wallet.app.models.TransactionDetails;
@Service
public class WalletServiceImpl implements WalletServiceInterface {
	@Autowired
	private WalletDaoInterface walletDao;
	
	@Override
	public CustomerDetails createAccount(CustomerDetails customer) {
		List<CustomerDetails> allAccounts = getAllAccounts();
		if(allAccounts.isEmpty())
			customer.setAccountId(10000000);
		else{
			int maxID = allAccounts.stream().max(Comparator.comparingLong(CustomerDetails::getAccountId)).get().getAccountId();
			customer.setAccountId(maxID + 1);
		}
		String encryptedPassword = new String(passwordEncryptor(customer.getAccountPassword().getBytes()));
		customer.setAccountPassword(encryptedPassword);
		String encryptedTpin = new String(passwordEncryptor(customer.getTransactionPin().getBytes()));
		customer.setTransactionPin(encryptedTpin);
		return walletDao.save(customer);
	}

	@Override
	public CustomerDetails updateAccount(CustomerDetails customer) {
		String encryptedPassword = new String(passwordEncryptor(customer.getAccountPassword().getBytes()));
		customer.setAccountPassword(encryptedPassword);
		String encryptedTpin = new String(passwordEncryptor(customer.getTransactionPin().getBytes()));
		customer.setTransactionPin(encryptedTpin);
		return walletDao.save(customer);
	}

	@Override
	public boolean deleteAccountById(int accountID) {
		walletDao.deleteById(accountID);
		return true;
	}

	@Override
	public CustomerDetails getAccountById(int accountId) {
		CustomerDetails customer = walletDao.findById(accountId).get();
		String decryptedPassword = new String(passwordDecryptor(customer.getAccountPassword().getBytes()));
		customer.setAccountPassword(decryptedPassword);
		String decryptedTpin = new String(passwordDecryptor(customer.getTransactionPin().getBytes()));
		customer.setTransactionPin(decryptedTpin);
		return customer;
	}

	@Override
	public List<CustomerDetails> getAllAccounts() {
		List<CustomerDetails> allCustomers =  walletDao.findAll();
		List<CustomerDetails> allAccounts = allCustomers.stream().map((customer) -> {
			String decryptedPassword = new String(passwordDecryptor(customer.getAccountPassword().getBytes()));
			customer.setAccountPassword(decryptedPassword);
			String decryptedTpin = new String(passwordDecryptor(customer.getTransactionPin().getBytes()));
			customer.setTransactionPin(decryptedTpin);
			return customer;
		}).collect(Collectors.toList());
		return allAccounts;
	}

	@Override
	public boolean deposit(int accountId, float money) {
		CustomerDetails customer = getAccountById(accountId);
		customer.setBalance(customer.getBalance() + money);
		
		TransactionDetails tData = new TransactionDetails();
		tData.setAccountId(accountId);
		tData.setType("Deposit");
		tData.setAmount(money);
		
		java.util.Date dateJava=new java.util.Date();
		java.sql.Date date=new java.sql.Date(dateJava.getTime());
		tData.setDate(date);
		
		customer.getTransactionDetails().add(tData);
		updateAccount(customer);
		return true;
	}

	@Override
	public boolean withdraw( int accountId, float money) {
		CustomerDetails customer = getAccountById(accountId);
		customer.setBalance(customer.getBalance() - money);
		
		TransactionDetails tData = new TransactionDetails();
		tData.setAccountId(accountId);
		tData.setType("Withdraw");
		tData.setAmount(-money);
		
		java.util.Date dateJava=new java.util.Date();
		java.sql.Date date=new java.sql.Date(dateJava.getTime());
		tData.setDate(date);
		
		customer.getTransactionDetails().add(tData);
		updateAccount(customer);
		return true;
	}

	@Override
	public boolean fundTransfer(int senderId, int receiverId, float money) {
		CustomerDetails customer = getAccountById(senderId);
		customer.setBalance(customer.getBalance() - money);
		
		TransactionDetails tData = new TransactionDetails();
		tData.setAccountId(senderId);
		tData.setType("Fund Transfered to " + receiverId);
		tData.setAmount(-money);
		
		java.util.Date dateJava=new java.util.Date();
		java.sql.Date date=new java.sql.Date(dateJava.getTime());
		tData.setDate(date);
		
		customer.getTransactionDetails().add(tData);
		updateAccount(customer);
		
		customer = getAccountById(receiverId);
		customer.setBalance(customer.getBalance() + money);
		tData.setType("Fund Transfered from " + senderId);
		tData.setAmount(money);
		customer.getTransactionDetails().add(tData);
		updateAccount(customer);
		return true;
	}

	@Override
	public List<TransactionDetails> printTransactions(int accountId) {
		CustomerDetails customer = getAccountById(accountId);
		return customer.getTransactionDetails();
	}

	@Override
	public CustomerDetails getSignedInAccount() {
		int accountId = getAllAccounts().stream().max(Comparator.comparingLong(CustomerDetails::getAccountId)).get().getAccountId();
		CustomerDetails customer = getAccountById(accountId);
		String encryptedPassword = new String(passwordEncryptor(customer.getAccountPassword().getBytes()));
		customer.setAccountPassword(encryptedPassword);
		String encryptedTpin = new String(passwordEncryptor(customer.getTransactionPin().getBytes()));
		customer.setTransactionPin(encryptedTpin);
		return customer;
	}

	@Override
	public byte[] passwordEncryptor(byte[] password) {
		byte[] encrypted = new byte[password.length];
		for (int i = 0; i < password.length; i++) {
			encrypted[i] = (byte)((i%2 == 0) ? password[i] + 1 : password[i] - 1);
		}
		return encrypted;
	}

	@Override
	public byte[] passwordDecryptor(byte[] password) {
		byte[] encrypted = new byte[password.length];
		for (int i = 0; i < password.length; i++) {
			encrypted[i] = (byte)((i%2 == 0) ? password[i] - 1 : password[i] + 1);
		}
		return encrypted;
	}

}
