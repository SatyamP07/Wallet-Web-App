package com.wallet.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.wallet.app.controller", "com.wallet.app.daoLayer", 
		"com.wallet.app.models", "com.wallet.app.servicelayer", "com.wallet.app.Exceptions"})
public class WalletPhase3v1Application {
	
	public static void main(String[] args) {
		SpringApplication.run(WalletPhase3v1Application.class, args);
	}
	
	

}
