package com.monitorjbl.model;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Audited
public class LineItem {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private Double value;

  LineItem() {
  }

  private LineItem(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setValue(builder.value);
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

  public Double getValue() {
    return value;
  }

  private void setValue(Double value) {
    this.value = value;
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
    private Double value;

    private Builder() {
    }

    private Builder(LineItem lineItem) {
      id = lineItem.id;
      name = lineItem.name;
      value = lineItem.value;
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder value(Double value) {
      this.value = value;
      return this;
    }

    public LineItem build() {
      return new LineItem(this);
    }
  }
}
