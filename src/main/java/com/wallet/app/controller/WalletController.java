package com.wallet.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.app.models.CustomerDetails;
import com.wallet.app.models.TransactionDetails;
import com.wallet.app.serviceLayer.WalletServiceInterface;

@RestController
@RequestMapping("/XYZWallet")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class WalletController {
	@Autowired
	private WalletServiceInterface walletRepository;
	
	@GetMapping("/accounts")
	public List<CustomerDetails> getAccounts(){
		return walletRepository.getAllAccounts();
	}
	
	@GetMapping("/account/{accountId}")
	public CustomerDetails getAccount(@PathVariable int accountId){
		return walletRepository.getAccountById(accountId);
	}
	
	@DeleteMapping("/account/{accountId}")
	public boolean deleteAccount(@PathVariable int accountId){
		walletRepository.deleteAccountById(accountId);
		return true; 
	}
	
	@PostMapping("/account")
	public CustomerDetails createAccount(@RequestBody CustomerDetails customer){
		return walletRepository.createAccount(customer);
	}
	
	@PutMapping("/account")
	public CustomerDetails updateAccount(@RequestBody CustomerDetails customer){
		return walletRepository.updateAccount(customer);
	}
	
	@PutMapping("/account/{accountId}/deposit")
	public boolean deposit(@PathVariable int accountId, @RequestBody String money) {
		float amount = Float.parseFloat(money);
		return walletRepository.deposit(accountId, amount);
	}
	
	@PutMapping("/account/{accountId}/withdraw")
	public boolean withdraw(@PathVariable int accountId, @RequestBody String money) {
		float amount = Float.parseFloat(money);
		return walletRepository.withdraw(accountId, amount);
	}
	
	@PutMapping("/account/{accountId}/fundTransfer/{receiverId}/{amount}")
	public boolean fundTransfer(@PathVariable int accountId,@PathVariable int receiverId, @PathVariable float amount) {
		return walletRepository.fundTransfer(accountId, receiverId, amount);
	}
	
	@GetMapping("/account/{accountId}/printTransactions")
	public List<TransactionDetails> printTransactions(@PathVariable int accountId){
		return walletRepository.printTransactions(accountId);
	}
	
	@GetMapping("/account/signedIn")
	public CustomerDetails getSignedInAccount() {
		return walletRepository.getSignedInAccount();
	}
	
}
