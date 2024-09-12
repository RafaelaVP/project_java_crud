package br.com.syonet.model;

public class Student {
  private final long id;
  private final String name;
  private final int age;
  private final String email;

  public Student(String name, int age, String email) {
    this.id = 0;
    this.name = name;
    this.age = age;
    this.email = email;
  }
  
  public Student(long id, String name, int age, String email) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }
  
  public String getEmail() {
    return email;
  }

  public Student setName(String name) {
    return new Student(this.id, name, this.age, this.email);
  }

  public Student setEmail(String email) {
      return new Student(this.id, this.name, this.age, email);
  }

  public Student setAge(Integer idade) {
      return new Student(this.id, this.name, idade, this.email);
  }
}
