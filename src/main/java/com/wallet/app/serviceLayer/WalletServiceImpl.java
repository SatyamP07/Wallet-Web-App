package com.wallet.app.serviceLayer;

import java.util.Comparator;
import java.util.List;

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
		return walletDao.save(customer);
	}

	@Override
	public CustomerDetails updateAccount(CustomerDetails customer) {
		return walletDao.save(customer);
	}

	@Override
	public boolean deleteAccountById(int accountID) {
		walletDao.deleteById(accountID);
		return true;
	}

	@Override
	public CustomerDetails getAccountById(int accountId) {
		return walletDao.findById(accountId).get();
	}

	@Override
	public List<CustomerDetails> getAllAccounts() {
		return walletDao.findAll();
	}

	@Override
	public boolean deposit(int accountId, float money) {
		CustomerDetails customer = getAccountById(accountId);
		customer.setBalance(customer.getBalance() + money);
		
		TransactionDetails tData = new TransactionDetails();
		tData.setAccountId(accountId);
		tData.setType("Deposit Rs." + money);
		
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
		tData.setType("Withdraw Rs." + money);
		
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
		tData.setType("Fund Transfered Rs." + money + " to " + receiverId);
		
		java.util.Date dateJava=new java.util.Date();
		java.sql.Date date=new java.sql.Date(dateJava.getTime());
		tData.setDate(date);
		
		customer.getTransactionDetails().add(tData);
		updateAccount(customer);
		
		customer = getAccountById(receiverId);
		customer.setBalance(customer.getBalance() + money);
		tData.setType("Fund Transfered Rs." + money + " from " + senderId);
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
		return getAccountById(accountId);
	}


}
