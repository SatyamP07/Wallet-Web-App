package com.wallet.app.serviceLayer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.app.Exceptions.UserNotFoundException;
import com.wallet.app.daoLayer.WalletDaoInterface;
import com.wallet.app.models.CustomerDetails;
import com.wallet.app.models.TransactionDetails;

@Service
public class WalletServiceImpl implements WalletServiceInterface {
	
	@Autowired
	private WalletDaoInterface walletRepository;
	
	//persists new CustomerDetails object in database
	//encrypts the account password and transaction pin 
	@Override
	public CustomerDetails createAccount(CustomerDetails customer) {
		String encryptedPassword = new String(passwordEncryptor(customer.getAccountPassword().getBytes()));
		customer.setAccountPassword(encryptedPassword);
		String encryptedTpin = new String(passwordEncryptor(customer.getTransactionPin().getBytes()));
		customer.setTransactionPin(encryptedTpin);
		return walletRepository.save(customer);
	}

	//updates the existing CustomerDetails object
	@Override
	public CustomerDetails updateAccount(CustomerDetails customer) {
		if(!customer.getAccountPassword().equals(walletRepository.findById(customer.getAccountId()).get().getAccountPassword())) {
			String encryptedPassword = new String(passwordEncryptor(customer.getAccountPassword().getBytes()));
			customer.setAccountPassword(encryptedPassword);
		}
		if(!customer.getTransactionPin().equals(walletRepository.findById(customer.getAccountId()).get().getTransactionPin())) {
			String encryptedTpin = new String(passwordEncryptor(customer.getTransactionPin().getBytes()));
			customer.setTransactionPin(encryptedTpin);
		}
		return walletRepository.save(customer);
	}

	//deletes account with the given accountId
	@Override
	public boolean deleteAccountById(int accountId) {
		walletRepository.deleteById(accountId);
		return true;
	}

	//fetches account with given accountId
	@Override
	public CustomerDetails getAccountById(int accountId) {
		return walletRepository.findById(accountId).get();
	}

	//fetches all accounts
	@Override
	public List<CustomerDetails> getAllAccounts() {
		List<CustomerDetails> allAccounts =  walletRepository.findAll();
		return allAccounts;
	}

	//updates account's amount field
	//deposits amount with given accountId and amount  
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

	//updates account's amount field
	//withdraws amount with given accountId and amount
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

	//updates account's amount field
	//transfers amount with given accountId and amount and receiverId
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

	//returns list of TransactionDetails objects associated with accountId
	@Override
	public List<TransactionDetails> printTransactions(int accountId) {
		CustomerDetails customer = getAccountById(accountId);
		return customer.getTransactionDetails();
	}

	//encrypts password
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
	
	@Override
	public boolean isCustomerExits(int accountId) {
		List<CustomerDetails> customers= walletRepository.findAll();
		boolean check=false;
		for(CustomerDetails customer:customers) {
			if(customer.getAccountId() == accountId) {
				check=true;
				break;
			}
		}
		if(check)
			return check;
		else
			throw new UserNotFoundException("No user exists with this Account Id");
	}
	
	@Override
	public boolean isAccountPasswordCorrect(int accountId, String password) {
		boolean flag = new String(passwordEncryptor(password.getBytes())).equals(walletRepository.findById(accountId).get().getAccountPassword());
		return flag;
	}
	
	@Override
	public boolean isTransactionPinCorrect(int accountId, String password) {
		boolean flag = new String(passwordEncryptor(password.getBytes())).equals(walletRepository.findById(accountId).get().getTransactionPin());
		return flag;
	}
}
