package com.wallet.app.serviceLayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.wallet.app.models.CustomerDetails;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class WalletServiceImplTest {

	@Autowired
	private WalletServiceImpl walletService;
	
	// returns a CustomerDetails object
	private CustomerDetails getCustomer() {
		CustomerDetails customer = new CustomerDetails();
		customer.setName("Steve Rogers");
		customer.seteMail("captain.america@avengers.com");
		customer.setMobileNumber("9876872170");
		customer.setAccountPassword("Assemble");
		customer.setTransactionPin("4269");
		customer.setNationalIdType("Pan Card");
		customer.setNationalIdNumber("STEVR8635C");
		customer.setBalance(5000);
		return customer;
	}
	
	//tests createAccount method from WalletServiceImpl class
	@Test
	public void testCreateAccount() {
		CustomerDetails customer = getCustomer();
		System.out.println("Customer: " + customer);
		CustomerDetails savedCustomer = walletService.createAccount(customer);
		System.out.println("savedCustomer: " + savedCustomer);
		assertNotNull(savedCustomer.getAccountId());
	}

	//tests updateAccount method from WalletServiceImpl class
	@Test
	public void testUpdateAccount() {
		CustomerDetails customer = getCustomer();
		CustomerDetails savedCustomer = walletService.createAccount(customer);
		savedCustomer.setName("Tony Stark");
		CustomerDetails updatedCustomer = walletService.updateAccount(savedCustomer);
		assertThat(updatedCustomer.getName()).isEqualTo("Tony Stark");		
	}

	//tests deleteAccountById method from WalletServiceImpl class
	@Test
	public void testDeleteAccountById() {
		CustomerDetails customer = getCustomer();
		CustomerDetails savedCustomer = walletService.createAccount(customer);
		walletService.deleteAccountById(savedCustomer.getAccountId());
		
		assertThatThrownBy(() -> walletService.getAccountById(savedCustomer.getAccountId())).
			isInstanceOf(NoSuchElementException.class);
	}

	//tests getAccountById method from WalletServiceImpl class
	@Test
	public void testGetAccountById() {
		CustomerDetails customer = getCustomer();
		CustomerDetails savedCustomer = walletService.createAccount(customer);
		CustomerDetails retrivedCustomer = walletService.getAccountById(savedCustomer.getAccountId());
		assertThat(retrivedCustomer).isEqualToComparingFieldByField(savedCustomer);
	}

	//tests getAllAccounts method from WalletServiceImpl class
	@Test
	public void testGetAllAccounts() {
		assertNotNull(walletService.getAllAccounts());
	}

	//tests deposit method from WalletServiceImpl class
	@Test
	public void testDeposit() {
		int accountId = walletService.createAccount(getCustomer()).getAccountId();
		walletService.deposit(accountId, 2366);
		assertThat(walletService.getAccountById(accountId).getBalance()).
			isEqualTo(getCustomer().getBalance() + 2366);
	}

	//tests withdraw method from WalletServiceImpl class
	@Test
	public void testWithdraw() {
		int accountId = walletService.createAccount(getCustomer()).getAccountId();
		walletService.withdraw(accountId, 2366);
		assertThat(walletService.getAccountById(accountId).getBalance()).
			isEqualTo(getCustomer().getBalance() - 2366);
	}

	//tests fundTransfer method from WalletServiceImpl class
	@Test
	public void testFundTransfer() {
		int senderAccountId = walletService.createAccount(getCustomer()).getAccountId();
		int receiverAccountId = walletService.createAccount(getCustomer()).getAccountId();
		walletService.fundTransfer(senderAccountId, receiverAccountId, 2366);
		assertTrue(
				walletService.getAccountById(senderAccountId).getBalance() == getCustomer().getBalance() - 2366 
				&&
				walletService.getAccountById(receiverAccountId).getBalance() == getCustomer().getBalance() + 2366
				);
	}

	//tests printTransaction method from WalletServiceImpl class
	@Test
	public void testPrintTransactions() {
		int accountId = walletService.createAccount(getCustomer()).getAccountId();
		walletService.deposit(accountId, 500);
		walletService.withdraw(accountId, 1000);
		assertNotNull(walletService.printTransactions(accountId));
	}
	
	//tests isAccountPasswordCorrect method from WalletServiceImpl class
	@Test
	public void testIsAccountPasswordCorrect() {
		CustomerDetails customer = walletService.createAccount(getCustomer());
		assertEquals(walletService.isAccountPasswordCorrect(customer.getAccountId(), "Assemble"), true);
	}

	//tests isAccountPasswordCorrect method from WalletServiceImpl class for incorrect password
	@Test
	public void testIsAccountPasswordCorrectFail() {
		CustomerDetails customer = walletService.createAccount(getCustomer());
		assertEquals(walletService.isAccountPasswordCorrect(customer.getAccountId(), "Asemble"), false);
	}
	
	//tests isTransactionPinCorrect method from WalletServiceImpl class
	@Test
	public void testIsTransactionPinCorrect() {
		CustomerDetails customer = walletService.createAccount(getCustomer());
		assertEquals(walletService.isTransactionPinCorrect(customer.getAccountId(), "4269"), true);
	}
	
	//tests isTrancationPinCorrect method from WalletServiceImpl class for incorrect pin
	@Test
	public void testIsTransactionPinCorrectFail() {
		CustomerDetails customer = walletService.createAccount(getCustomer());
		assertEquals(walletService.isTransactionPinCorrect(customer.getAccountId(), "4242"), false);
	}


}
