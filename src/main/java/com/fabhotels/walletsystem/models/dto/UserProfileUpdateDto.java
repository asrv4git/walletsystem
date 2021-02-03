package com.fabhotels.walletsystem.models.dto;

import com.fabhotels.walletsystem.utils.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

public class UserProfileUpdateDto {

    @Pattern(regexp = Constants.USERNAME_REGEX)
    @Size(min = 5, max = 50)
    private String username;

    @Size(min = 2, max = 50)
    private String firstName;

    @Size(min = 1, max = 50)
    private String lastName;

    @Email
    @Size(min = 6, max = 100)
    private String email;

    @Pattern(regexp = Constants.MOBILENUMBER_REGEX)
    private String mobileNumber;

    @Pattern(regexp = Constants.PASSWORD_REGEX)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
