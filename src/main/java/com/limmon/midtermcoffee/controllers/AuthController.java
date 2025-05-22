package com.limmon.midtermcoffee.controllers;

import com.limmon.midtermcoffee.models.AppUser;
import com.limmon.midtermcoffee.services.AppUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling user authentication such as login and logout.
 */
@Controller
public class AuthController {

    @Autowired
    AppUserService appUserService;

    /**
     * Displays the login form.
     *
     * @param model the model to hold the form data
     * @return the login view
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new AppUser());
        return "components/login";
    }

    /**
     * Processes the login form submission.
     * Validates user credentials and starts a session if successful.
     *
     * @param formUser      the submitted login form data
     * @param bindingResult result of validation checks
     * @param session       the current HTTP session
     * @param model         the model to hold data for the view
     * @return redirect to the home page on success, or back to login on failure
     */
    @PostMapping("/login")
    public String login(
            @ModelAttribute("user") @Valid AppUser formUser,
            BindingResult bindingResult,
            HttpSession session,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "components/login";
        }

        // Attempt to authenticate the user
        AppUser foundUser = appUserService.findByUsername(formUser.getUsername());
        if (foundUser != null && new BCryptPasswordEncoder().matches(formUser.getPassword(), foundUser.getPassword())) {
            session.setAttribute("user", foundUser);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid credentials");
        }

        return "components/login";
    }

    /**
     * Logs the user out by invalidating the current session.
     *
     * @param session the current HTTP session
     * @return a redirect to the login page
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
