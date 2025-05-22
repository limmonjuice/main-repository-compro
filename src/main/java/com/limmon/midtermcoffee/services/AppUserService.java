package com.limmon.midtermcoffee.services;

import com.limmon.midtermcoffee.models.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing application users.
 * Loads user data from a CSV file and provides methods to find and save users.
 */
@Service
public class AppUserService {
    private List<AppUser> appUsers;

    /**
     * Initializes the service by loading user data from the CSV file.
     * This method is called automatically after the service is constructed.
     *
     * @throws IOException if there is an error reading the users.csv file.
     */
    @PostConstruct
    public void init() throws IOException {
        appUsers = new ArrayList<>();
        File file = new File("data/users.csv"); // path relative to project root

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        reader.readLine(); // skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            AppUser appUser = new AppUser();
            appUser.setUsername(parts[0]);
            appUser.setPassword(parts[1]);
            appUsers.add(appUser);
        }
    }

    /**
     * Finds and returns an AppUser by their username.
     *
     * @param username the username to search for.
     * @return the AppUser object if found; otherwise, null.
     */
    public AppUser findByUsername(String username) {
        return appUsers.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void save(AppUser appUser) {

    }
}
