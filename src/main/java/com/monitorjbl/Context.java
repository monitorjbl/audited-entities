package com.monitorjbl;

import com.monitorjbl.persistence.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Context {
  @Bean
  public Main main() {
    return new Main();
  }
}
