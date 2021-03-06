package com.wallet.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.app.Exceptions.IncorrectPasswordException;
import com.wallet.app.Exceptions.UserNotFoundException;
import com.wallet.app.models.CustomerDetails;
import com.wallet.app.models.TransactionDetails;
import com.wallet.app.serviceLayer.WalletServiceInterface;

@RestController
@RequestMapping("/XYZWallet")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class WalletController {
	
	Logger logger = LoggerFactory.getLogger(WalletController.class);
	
	@Autowired
	private WalletServiceInterface walletService;
	
	//fetches all accounts  
	//returns list of CustomerDetails
	@GetMapping("/accounts")
	public ResponseEntity<List<CustomerDetails>> getAccounts(){
		return ResponseEntity.ok().body(walletService.getAllAccounts());
	}
	
	//requests to fetch an account with accountId as parameter
	@GetMapping("/account/{accountId}")
	public ResponseEntity<CustomerDetails> getAccount(@PathVariable int accountId) {
		logger.info("Resquested getAccount for Account Id " + accountId);
		walletService.isCustomerExits(accountId);
		return ResponseEntity.ok().body(walletService.getAccountById(accountId));
	}
	
	//requests to delete an account with accountId as parameter
	@DeleteMapping("/account/{accountId}")
	public ResponseEntity<Boolean> deleteAccount(@PathVariable int accountId){
		logger.info("Request deleteAccount with Account Id " + accountId);
		walletService.deleteAccountById(accountId);
		return ResponseEntity.ok().body(true);
	}

	//requests to create an account with CustomerDetails as parameter
	//returns the persisted CustomerDetails object
	@PostMapping("/account")
	public ResponseEntity<CustomerDetails> createAccount(@RequestBody CustomerDetails customer){
		logger.info("Request createAccount");
		return ResponseEntity.ok().body(walletService.createAccount(customer));
	}
	
	//requests to update an account with CustomerDetails as parameter
	//returns the persisted CustomerDetails object
	@PutMapping("/account")
	public ResponseEntity<CustomerDetails> updateAccount(@RequestBody CustomerDetails customer){
		logger.info("Request updateAccount for Account Id " + customer.getAccountId());
		return ResponseEntity.ok().body(walletService.updateAccount(customer));
	}
	
	//requests to deposit in an account with accountId and amount as parameters
	//returns the boolean
	@PutMapping("/account/{accountId}/deposit/{amount}")
	public ResponseEntity<Boolean> deposit(@PathVariable int accountId, @PathVariable float amount) {
		logger.info("Request deposit for Account Id " + accountId + " done with amount " + amount);
		return ResponseEntity.ok().body(walletService.deposit(accountId, amount));
	}
	
	//requests to withdraw amount from an account with accountId and amount as parameters
	//returns the boolean
	@PutMapping("/account/{accountId}/withdraw/{amount}")
	public ResponseEntity<Boolean> withdraw(@PathVariable int accountId, @PathVariable float amount) {
		logger.info("Request withdraw for Account Id " + accountId + " done with amount " + amount);
		return ResponseEntity.ok().body(walletService.withdraw(accountId, amount));
	}
	
	//requests to transfer amount from an account with accountId to other account with receiverId and amount as parameters
	//returns the boolean
	@PutMapping("/account/{accountId}/fundTransfer/{receiverId}/{amount}")
	public ResponseEntity<Boolean> fundTransfer(@PathVariable int accountId,@PathVariable int receiverId,
													@PathVariable float amount) {
		logger.info("Request fundTransfer for Account Id " + accountId + " done with amount " + amount + 
							"against receiver's Account Id " + receiverId);
		return ResponseEntity.ok().body(walletService.fundTransfer(accountId, receiverId, amount));
	}
	
	//requests to return List of all transaction made by account with accountId
	@GetMapping("/account/{accountId}/printTransactions")
	public ResponseEntity<List<TransactionDetails>> printTransactions(@PathVariable int accountId){
		logger.info("Request printTransaction for Account Id " + accountId);
		return ResponseEntity.ok().body(walletService.printTransactions(accountId));
	}
	
	//requests to check if the account password is correct
	@PostMapping("/account/{accountId}/checkAccountPassword")
	public ResponseEntity<Boolean> checkAccountPassword(@PathVariable("accountId") int accountId,@RequestBody String password) {
		logger.info("Request checkAccount for Account Id " + accountId);
		if(walletService.isAccountPasswordCorrect(accountId, password.substring(1, password.length()-1)))
			return ResponseEntity.ok().body(true);
		else
			throw new IncorrectPasswordException("Incorrect Password");
	}
	
	//requests to check if the transaction pin is correct
	@PostMapping("/account/{accountId}/checkTransactionPin")
	public ResponseEntity<Boolean> checkTransactionPin(@PathVariable("accountId") int accountId,@RequestBody String password) {
		logger.info("Request checkTransactionPin for Account Id " + accountId);
		if(walletService.isTransactionPinCorrect(accountId, password.substring(1, password.length()-1)))
			return ResponseEntity.ok().body(true);
		else
			throw new IncorrectPasswordException("Incorrect Password");
	}
}
