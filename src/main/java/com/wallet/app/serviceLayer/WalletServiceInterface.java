package com.wallet.app.serviceLayer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wallet.app.models.CustomerDetails;
import com.wallet.app.models.TransactionDetails;
@Service
public interface WalletServiceInterface {

	CustomerDetails createAccount(CustomerDetails customer);
	CustomerDetails updateAccount(CustomerDetails customer);
	boolean deleteAccountById(int accountID);
	CustomerDetails getAccountById(int accountId);
	List<CustomerDetails> getAllAccounts();
	
	boolean deposit(int accountId, float money);
	boolean withdraw(int accountId, float money);
	boolean fundTransfer(int senderId, int receiverId, float money);
	List<TransactionDetails> printTransactions(int accountId);
	
	byte[] passwordEncryptor(byte[] password);
	byte[] passwordDecryptor(byte[] encrypted);
}
