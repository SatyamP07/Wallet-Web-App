package com.wallet.app.daoLayer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wallet.app.models.CustomerDetails;
import com.wallet.app.models.TransactionDetails;

@Repository
public interface WalletDaoInterface extends JpaRepository<CustomerDetails, Integer> {

}
