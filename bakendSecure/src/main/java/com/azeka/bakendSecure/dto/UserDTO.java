package com.azeka.bakendSecure.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 100;

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;


    private Set<String> authorities;

    public UserDTO(UserDTO user) {
    }

    public UserDTO(String login, String password, Set<String> authorities) {

        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
    
    public Set<String> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +            
            ", authorities=" + authorities +
            "}";
    }
}
