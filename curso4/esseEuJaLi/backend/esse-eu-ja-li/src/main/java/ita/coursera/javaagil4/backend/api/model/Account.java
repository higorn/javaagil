/*
 * File:   Account.java
 *
 * Created on 24/06/17, 19:14
 */
package ita.coursera.javaagil4.backend.api.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author higor
 */
@Entity
@Table(name = "account", indexes = {@Index(columnList = "name")})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(nullable = false)
    private String id;
    @OrderColumn
    private String name;
    private String displayName;
    private String role;
    private String password;
    private String token;

    public Account() {
    }

    public Account(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}