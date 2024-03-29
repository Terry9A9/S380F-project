package hkmu.comps380f.model;

import java.io.Serializable;

public class WebUser implements Serializable {

    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String role;

    public WebUser() {
    }

    public WebUser(String username, String password, String fullName,
                   String phoneNumber, String address, String role) {
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = "{noop}" + password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordFromDB(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
