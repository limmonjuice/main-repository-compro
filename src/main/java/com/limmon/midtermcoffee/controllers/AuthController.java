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

@Controller
public class AuthController {

    @Autowired
    AppUserService appUserService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new AppUser());

        return "components/login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid AppUser formUser, BindingResult bindingResult, HttpSession session, Model model){
        if(bindingResult.hasErrors()){
            return "components/login";
        }

        //authenticate
        AppUser foundUser = appUserService.findByUsername(formUser.getUsername());
        if(foundUser != null && new BCryptPasswordEncoder().matches(formUser.getPassword(), foundUser.getPassword())){
            session.setAttribute("user", foundUser);
            return "redirect:/";
        }else{
            String error ="Invalid credentials";
            model.addAttribute("error", error);
        }


        return "components/login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:components/login";
    }


}

