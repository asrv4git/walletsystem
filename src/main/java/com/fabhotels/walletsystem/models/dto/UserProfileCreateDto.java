package com.fabhotels.walletsystem.models.dto;

import com.fabhotels.walletsystem.utils.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

public class UserProfileCreateDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userId;

    @NotBlank
    @Pattern(regexp = Constants.USERNAME_REGEX)
    @Size(min = 5, max = 50)
    private String username;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    @Size(min = 6, max = 100)
    private String email;

    @NotNull
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserProfileDataDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
