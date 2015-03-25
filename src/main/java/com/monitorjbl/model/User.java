package com.monitorjbl.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
  @Id
  @GeneratedValue
  private Long id;
  private String name;

  public User() {
  }

  private User(Builder builder) {
    setId(builder.id);
    setName(builder.name);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Long id;
    private String name;

    private Builder() {
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}
