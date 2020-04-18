package com.wallet.app.daoLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallet.app.models.CustomerDetails;

@Repository
public interface WalletDaoInterface extends JpaRepository<CustomerDetails, Integer> {

}
