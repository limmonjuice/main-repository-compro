package com.limmon.midtermcoffee.models;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents an application user with username and password fields.
 * Includes validation to ensure that both fields are not blank.
 */
public class AppUser {

    @NotBlank(message = "Username should not be blank")
    private String username;

    @NotBlank(message = "Password should not be blank")
    private String password;

    /**
     * Gets the username of the user.
     *
     * @return The username as a String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password as a String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
