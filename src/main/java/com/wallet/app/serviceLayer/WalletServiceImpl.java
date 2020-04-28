package com.wallet.app.serviceLayer;

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
	private WalletDaoInterface walletRepository;
	@Override
	public CustomerDetails createAccount(CustomerDetails customer) {
		String encryptedPassword = new String(passwordEncryptor(customer.getAccountPassword().getBytes()));
		customer.setAccountPassword(encryptedPassword);
		String encryptedTpin = new String(passwordEncryptor(customer.getTransactionPin().getBytes()));
		customer.setTransactionPin(encryptedTpin);
		return walletRepository.save(customer);
	}

	@Override
	public CustomerDetails updateAccount(CustomerDetails customer) {
		String encryptedPassword = new String(passwordEncryptor(customer.getAccountPassword().getBytes()));
		customer.setAccountPassword(encryptedPassword);
		String encryptedTpin = new String(passwordEncryptor(customer.getTransactionPin().getBytes()));
		customer.setTransactionPin(encryptedTpin);
		return walletRepository.save(customer);
	}

	@Override
	public boolean deleteAccountById(int accountId) {
		walletRepository.deleteById(accountId);
		return true;
	}

	@Override
	public CustomerDetails getAccountById(int accountId) {
		CustomerDetails customer = walletRepository.findById(accountId).get();
		String decryptedPassword = new String(passwordDecryptor(customer.getAccountPassword().getBytes()));
		customer.setAccountPassword(decryptedPassword);
		String decryptedTpin = new String(passwordDecryptor(customer.getTransactionPin().getBytes()));
		customer.setTransactionPin(decryptedTpin);
		return customer;
	}

	@Override
	public List<CustomerDetails> getAllAccounts() {
		List<CustomerDetails> allAccounts =  walletRepository.findAll();
		List<CustomerDetails> allCustomers = allAccounts.stream().map((account) -> {
			CustomerDetails customer = new CustomerDetails();
			customer.setAccountId(account.getAccountId());
			customer.setName(account.getName());
			customer.setBalance(account.getBalance());
			customer.seteMail(account.geteMail());
			customer.setMobileNumber(account.getMobileNumber());
			customer.setTransactionDetails(account.getTransactionDetails());
			String password = new String(passwordDecryptor(account.getAccountPassword().getBytes()));
			customer.setAccountPassword(password);
			String tPin = new String(passwordDecryptor(account.getTransactionPin().getBytes()));
			customer.setTransactionPin(tPin);
			return customer;
		}).collect(Collectors.toList());
		return allCustomers;
	}

	@Override
	public boolean deposit(int accountId, float amount) {
		CustomerDetails customer = getAccountById(accountId);
		customer.setBalance(customer.getBalance() + amount);
		
		TransactionDetails tData = new TransactionDetails();
		tData.setAccountId(accountId);
		tData.setType("Deposit");
		tData.setAmount(amount);
		
		java.util.Date dateJava=new java.util.Date();
		java.sql.Date date=new java.sql.Date(dateJava.getTime());
		tData.setDate(date);
		
		customer.getTransactionDetails().add(tData);
		updateAccount(customer);
		return true;
	}

	@Override
	public boolean withdraw(int accountId, float amount) {
		CustomerDetails customer = getAccountById(accountId);
		customer.setBalance(customer.getBalance() - amount);
		
		TransactionDetails tData = new TransactionDetails();
		tData.setAccountId(accountId);
		tData.setType("Withdraw");
		tData.setAmount(-amount);
		
		java.util.Date dateJava=new java.util.Date();
		java.sql.Date date=new java.sql.Date(dateJava.getTime());
		tData.setDate(date);
		
		customer.getTransactionDetails().add(tData);
		updateAccount(customer);
		return true;
	}

	@Override
	public boolean fundTransfer(int accountId, int receiverId, float amount) {
		CustomerDetails customer = getAccountById(accountId);
		customer.setBalance(customer.getBalance() - amount);
		
		TransactionDetails tData = new TransactionDetails();
		tData.setAccountId(accountId);
		tData.setType("Fund Transfered to " + receiverId);
		tData.setAmount(-amount);
		
		java.util.Date dateJava=new java.util.Date();
		java.sql.Date date=new java.sql.Date(dateJava.getTime());
		tData.setDate(date);
		
		customer.getTransactionDetails().add(tData);
		updateAccount(customer);
		
		customer = getAccountById(receiverId);
		customer.setBalance(customer.getBalance() + amount);
		tData.setType("Fund Transfered from " + accountId);
		tData.setAmount(amount);
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
	public byte[] passwordEncryptor(byte[] password) {
		byte[] encrypted = new byte[password.length];
		for (int i = 0; i < password.length; i++) {
			encrypted[i] = (byte)((i%2 == 0) ? password[i] + 1 : password[i] - 1);
		}
		return encrypted;
	}

	@Override
	public byte[] passwordDecryptor(byte[] password) {
		byte[] decrypted = new byte[password.length];
		for (int i = 0; i < password.length; i++) {
			decrypted[i] = (byte)((i%2 == 0) ? password[i] - 1 : password[i] + 1);
		}
		return decrypted;
	}

}
