package com.wallet.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@Autowired
	private WalletServiceInterface walletService;
	
	@GetMapping("/accounts")
	public List<CustomerDetails> getAccounts(){
		return walletService.getAllAccounts();
	}
	
	@GetMapping("/account/{accountId}")
	public CustomerDetails getAccount(@PathVariable int accountId) {
		if(walletService.isCustomerExits(accountId))
			return walletService.getAccountById(accountId);
		else
			throw new UserNotFoundException("No user exists with this Account Id");
	}
	
	@DeleteMapping("/account/{accountId}")
	public boolean deleteAccount(@PathVariable int accountId){
		walletService.deleteAccountById(accountId);
		return true;
	}
	
	@PostMapping("/account")
	public CustomerDetails createAccount(@RequestBody CustomerDetails customer){		
		return walletService.createAccount(customer);
	}
	
	@PutMapping("/account")
	public CustomerDetails updateAccount(@RequestBody CustomerDetails customer){
		return walletService.updateAccount(customer);
	}
	
	@PutMapping("/account/{accountId}/deposit/{amount}")
	public boolean deposit(@PathVariable int accountId, @PathVariable float amount) {
		return walletService.deposit(accountId, amount);
	}
	
	@PutMapping("/account/{accountId}/withdraw/{amount}")
	public boolean withdraw(@PathVariable int accountId, @PathVariable float amount) {
		return walletService.withdraw(accountId, amount);
	}
	
	@PutMapping("/account/{accountId}/fundTransfer/{receiverId}/{amount}")
	public boolean fundTransfer(@PathVariable int accountId,@PathVariable int receiverId, @PathVariable float amount) {
		return walletService.fundTransfer(accountId, receiverId, amount);
	}
	
	@GetMapping("/account/{accountId}/printTransactions")
	public List<TransactionDetails> printTransactions(@PathVariable int accountId){
		return walletService.printTransactions(accountId);
	}
	
	@PostMapping("/account/{accountId}/checkAccountPassword")
	public boolean checkAccountPassword(@PathVariable("accountId") int accountId,@RequestBody String password) {
		if(walletService.isAccountPasswordCorrect(accountId, password.substring(1, password.length()-1)))
			return true;
		else
			throw new IncorrectPasswordException("Incorrect Password");
	}
	
	@PostMapping("/account/{accountId}/checkTransactionPin")
	public boolean checkTransactionPin(@PathVariable("accountId") int accountId,@RequestBody String password) {
		if(walletService.isTransactionPinCorrect(accountId, password.substring(1, password.length()-1)))
			return true;
		else
			throw new IncorrectPasswordException("Incorrect Password");
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public String noUserErrorMessage(UserNotFoundException unf) {
		return unf.getMessage();
	}
	
	@ExceptionHandler(IncorrectPasswordException.class)
	public String incorrectPasswordErrorMessage(IncorrectPasswordException ipe) {
		return ipe.getMessage();
	}
}
