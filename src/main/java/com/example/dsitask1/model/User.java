package com.example.dsitask1.model;
import jakarta.persistence.*;

@Entity
@Table(name = "users")

public class User {
@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
private long  id;

@Column(nullable =false)
private String name;

@Column(nullable =false,unique =true)
private String email;

public User() {}

public User(String name, String email) {
    this.name = name;
    this.email = email;
}
public Long getId() { return id; }

public String getname() {return name;}
public void setname(String name) {this.name =name;}
public String getEmail( ) {return email;}
public void setEmail(String email) {this.email = email;}
}
