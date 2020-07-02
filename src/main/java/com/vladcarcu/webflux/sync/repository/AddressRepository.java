package com.vladcarcu.webflux.sync.repository;

import com.vladcarcu.webflux.sync.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
