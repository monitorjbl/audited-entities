package com.monitorjbl.model;

import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Entity
@Audited
public class Receipt {
  @Id
  @GeneratedValue
  private Long id;
  private Date date;
  @OneToMany(targetEntity = LineItem.class, cascade = {CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE})
  private List<LineItem> lineItems;

  Receipt() {
  }

  private Receipt(Builder builder) {
    setId(builder.id);
    setDate(builder.date);
    setLineItems(builder.lineItems);
  }

  public Long getId() {
    return id;
  }

  private void setId(Long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  private void setDate(Date date) {
    this.date = date;
  }

  public List<LineItem> getLineItems() {
    return lineItems;
  }

  private void setLineItems(List<LineItem> lineItems) {
    this.lineItems = lineItems;
  }

  public Builder copyBuilder() {
    return new Builder(this);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Long id;
    private Date date;
    private List<LineItem> lineItems = newArrayList();

    private Builder() {
    }

    private Builder(Receipt receipt) {
      id = receipt.id;
      date = receipt.date;
      lineItems = receipt.lineItems;
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder date(Date date) {
      this.date = date;
      return this;
    }

    public Builder lineItems(List<LineItem> lineItems) {
      this.lineItems = lineItems;
      return this;
    }

    public Receipt build() {
      return new Receipt(this);
    }
  }
}
