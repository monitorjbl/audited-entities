package com.monitorjbl.persistence;

import com.monitorjbl.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
  public Customer findById(Long id);
}
