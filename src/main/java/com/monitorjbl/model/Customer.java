package com.monitorjbl.model;

import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Entity
@Audited
public class Customer {
  public static enum BillingLevel {
    TRIAL, STANDARD, PREMIUM
  }

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String account;
  @Enumerated
  private BillingLevel billingLevel;
  @ElementCollection
  private List<String> contacts;
  @OneToMany(targetEntity = Receipt.class, cascade = {CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE})
  private List<Receipt> receipts;

  Customer() {
  }

  private Customer(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setAccount(builder.account);
    setBillingLevel(builder.billingLevel);
    setContacts(builder.contacts);
    setReceipts(builder.receipts);
  }

  public Long getId() {
    return id;
  }

  private void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  public String getAccount() {
    return account;
  }

  private void setAccount(String account) {
    this.account = account;
  }

  public BillingLevel getBillingLevel() {
    return billingLevel;
  }

  private void setBillingLevel(BillingLevel billingLevel) {
    this.billingLevel = billingLevel;
  }

  public List<String> getContacts() {
    return contacts;
  }

  private void setContacts(List<String> contacts) {
    this.contacts = contacts;
  }

  public List<Receipt> getReceipts() {
    return receipts;
  }

  private void setReceipts(List<Receipt> receipts) {
    this.receipts = receipts;
  }

  public Builder copyBuilder() {
    return new Builder(this);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Long id;
    private String name;
    private String account;
    private BillingLevel billingLevel;
    private List<String> contacts = newArrayList();
    private List<Receipt> receipts = newArrayList();

    private Builder() {
    }

    private Builder(Customer customer) {
      id = customer.id;
      name = customer.name;
      account = customer.account;
      billingLevel = customer.billingLevel;
      contacts = customer.contacts;
      receipts = customer.receipts;
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder account(String account) {
      this.account = account;
      return this;
    }

    public Builder billingLevel(BillingLevel billingLevel) {
      this.billingLevel = billingLevel;
      return this;
    }

    public Builder contacts(List<String> contacts) {
      this.contacts = contacts;
      return this;
    }

    public Builder receipts(List<Receipt> receipts) {
      this.receipts = receipts;
      return this;
    }

    public Customer build() {
      return new Customer(this);
    }
  }
}
