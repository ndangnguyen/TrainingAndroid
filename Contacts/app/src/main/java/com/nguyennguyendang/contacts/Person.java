package com.nguyennguyendang.contacts;

public class Person {
  private String name, phone;

  public Person(String name, String phone) {
    this.name = name;
    this.phone = phone;
  }

  public Person() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return getName();
  }
}
