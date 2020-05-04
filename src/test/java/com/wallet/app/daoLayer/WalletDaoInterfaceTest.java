package com.wallet.app.daoLayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.wallet.app.models.CustomerDetails;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class WalletDaoInterfaceTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private WalletDaoInterface walletRepository;
	
	private CustomerDetails getCustomer() {
		CustomerDetails customer = new CustomerDetails();
		customer.setName("Steve Rogers");
		customer.seteMail("captain.america@avengers.com");
		customer.setMobileNumber("9876872170");
		customer.setAccountPassword("Assemble");
		customer.setTransactionPin("4269");
		customer.setNationalIdType("Pan Card");
		customer.setNationalIdNumber("STEVR8635C");
		return customer;
	}
	
	@Test
	public void testSaveCustomer() {
		CustomerDetails customer = getCustomer();
		CustomerDetails savedCustomer = walletRepository.save(customer);
		CustomerDetails entityManagerCustomer = entityManager.find(CustomerDetails.class, savedCustomer.getAccountId());
		
		assertThat(savedCustomer).isEqualToComparingFieldByField(entityManagerCustomer);
	}
	
	@Test
	public void testFindByIdCustomer() {
		CustomerDetails savedCustomer = entityManager.persist(getCustomer());
		
		CustomerDetails entityManagerCustomer = entityManager.find(CustomerDetails.class, savedCustomer.getAccountId());
		CustomerDetails retrivedCustomer = walletRepository.findById(savedCustomer.getAccountId()).get();
		
		assertThat(retrivedCustomer).isEqualToComparingFieldByField(entityManagerCustomer);
	}
	
	@Test
	public void deleteCustomerById() {
		CustomerDetails savedCustomer = entityManager.persist(getCustomer());
		walletRepository.deleteById(savedCustomer.getAccountId());
		assertNull(entityManager.find(CustomerDetails.class, savedCustomer.getAccountId()));
	}
	
	@Test
	public void updateCustomer() {
		CustomerDetails savedCustomer = entityManager.persist(getCustomer());
		savedCustomer.setName("Tony Stark");
		CustomerDetails updatedCustomer = walletRepository.save(savedCustomer);
		assertThat(updatedCustomer.getName()).isEqualTo("Tony Stark");
	}
	
	public void findallCustomers() {
		assertNotNull(walletRepository.findAll());
	}
	

}
