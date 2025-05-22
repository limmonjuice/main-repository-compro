package com.limmon.midtermcoffee.controllers;

import com.limmon.midtermcoffee.models.AppUser;
import com.limmon.midtermcoffee.models.Coffee;
import com.limmon.midtermcoffee.services.CoffeeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Controller class for managing coffee data.
 */
@Controller
public class CoffeeController {

    private final CoffeeService coffeeService;

    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }
    @GetMapping("/home")
    public String home(Model model) {
        return "layouts/master";
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, HttpSession session, Model model) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("coffees", coffeeService.getCoffees());
        model.addAttribute("activeMenu", "home");

        return "pages/index";
    }

    @GetMapping("/add")
    public String addCoffeeForm(Model model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("coffee", new Coffee());
        model.addAttribute("activeMenu", "add");
        return "pages/new";
    }

    @PostMapping("/save")
    public String storeSave(@Valid @ModelAttribute("coffee") Coffee coffee, BindingResult result,
                            @RequestParam("coffeePic") MultipartFile coffeePicture, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "pages/new";
        }

        // Handle image upload
        if (!coffeePicture.isEmpty()) {
            String path = "data/coffee_pictures/";
            File uploadFolder = new File(path);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String fileName = UUID.randomUUID() + coffeePicture.getOriginalFilename().substring(coffeePicture.getOriginalFilename().lastIndexOf("."));
            System.out.println("Saving file as: " + fileName);
            File destination = new File(uploadFolder.getAbsolutePath() + File.separator + fileName);
            System.out.println("Destination path: " + destination.getAbsolutePath());

            try {
                System.out.println("Received file: " + coffeePicture.getOriginalFilename());
                System.out.println("Size: " + coffeePicture.getSize());
                System.out.println("IsEmpty: " + coffeePicture.isEmpty());

                coffeePicture.transferTo(new File(uploadFolder.getAbsolutePath() + File.separator + fileName));
                coffee.setCoffeePicture(fileName);
            } catch (IOException e) {
                System.out.println("File upload error: " + e.getMessage());
            }
        }
        int newId = coffeeService.getCoffees().size() + 1;
        coffee.setId(newId);
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Coffee coffee = coffeeService.getCoffeeById(id);
        if (coffee != null) {
            model.addAttribute("coffee", coffee);
            return "pages/edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateCoffee(@Valid @ModelAttribute("coffee") Coffee coffee, BindingResult result,
                            @RequestParam("coffeePic") MultipartFile coffeePicture, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (result.hasErrors()) {
            return "pages/edit";  // Return the form view with validation errors
        }

        if (!coffeePicture.isEmpty()) {
            String path = "data/coffee_pictures/";
            File uploadFolder = new File(path);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String fileName = UUID.randomUUID() + coffeePicture.getOriginalFilename().substring(coffeePicture.getOriginalFilename().lastIndexOf("."));
            System.out.println("Saving file as: " + fileName);
            File destination = new File(uploadFolder.getAbsolutePath() + File.separator + fileName);
            System.out.println("Destination path: " + destination.getAbsolutePath());

            try {
                System.out.println("Received file: " + coffeePicture.getOriginalFilename());
                System.out.println("Size: " + coffeePicture.getSize());
                System.out.println("IsEmpty: " + coffeePicture.isEmpty());

                coffeePicture.transferTo(new File(uploadFolder.getAbsolutePath() + File.separator + fileName));
                coffee.setCoffeePicture(fileName);
            } catch (IOException e) {
                System.out.println("File upload error: " + e.getMessage());
            }
        }


        coffeeService.updateCoffee(coffee.getId(), coffee);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        coffeeService.deleteCoffeeById(id);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchCoffees(@RequestParam(required = false) String keyword, Model model) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/";
        }
        List<Coffee> searchResults = coffeeService.searchCoffeesByName(keyword);
        model.addAttribute("coffees", searchResults);
        model.addAttribute("activeMenu", "home");
        return "pages/index";
    }

    @GetMapping("/coffee/{id}")
    public String view(@PathVariable int id, Model model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Coffee coffee = coffeeService.getCoffeeById(id);
        model.addAttribute("coffee", coffee);
        return "pages/coffee";
    }

    @GetMapping("/menu")
    public String menu(@RequestParam(defaultValue = "") String search, HttpSession session, Model model) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("coffeeList", coffeeService.searchCoffeesByName(search));
        model.addAttribute("activeMenu", "menu");
        return "pages/coffee-menu";
    }


}
