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
 * Controller class for handling coffee-related routes and views.
 * This includes CRUD operations and listing/searching coffees.
 */
@Controller
public class CoffeeController {

    private final CoffeeService coffeeService;

    /**
     * Constructor-based dependency injection for the coffee service.
     *
     * @param coffeeService Service to handle coffee logic.
     */
    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    /**
     * Returns the base layout view.
     *
     * @param model Spring's model object.
     * @return The layout master view.
     */
    @GetMapping("/home")
    public String home(Model model) {
        return "layouts/master";
    }

    /**
     * Handles the main index page that lists all coffees.
     *
     * @param search  Search parameter (optional).
     * @param session User session.
     * @param model   Spring's model object.
     * @return Coffee index page view.
     */
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, HttpSession session, Model model) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("coffees", coffeeService.getCoffees());
        model.addAttribute("activeMenu", "home");

        return "pages/index";
    }

    /**
     * Shows the form to add a new coffee entry.
     *
     * @param model   Spring's model object.
     * @param session User session.
     * @return Coffee form view.
     */
    @GetMapping("/add")
    public String addCoffeeForm(Model model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("coffee", new Coffee());
        model.addAttribute("activeMenu", "add");
        return "pages/new";
    }

    /**
     * Stores a new coffee entry and handles image upload.
     *
     * @param coffee        Coffee model object with form data.
     * @param result        Binding result for validation.
     * @param coffeePicture Uploaded coffee picture.
     * @param session       User session.
     * @return Redirects to the homepage.
     */
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
            File destination = new File(uploadFolder.getAbsolutePath() + File.separator + fileName);

            try {
                coffeePicture.transferTo(destination);
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

    /**
     * Displays the form to edit a coffee entry.
     *
     * @param id      Coffee ID.
     * @param model   Spring's model object.
     * @param session User session.
     * @return Edit page view.
     */
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

    /**
     * Updates an existing coffee entry.
     *
     * @param coffee        Updated coffee model.
     * @param result        Validation result.
     * @param coffeePicture New picture file (optional).
     * @param session       User session.
     * @return Redirects to homepage.
     */
    @PostMapping("/update")
    public String updateCoffee(@Valid @ModelAttribute("coffee") Coffee coffee, BindingResult result,
                               @RequestParam("coffeePic") MultipartFile coffeePicture, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (result.hasErrors()) {
            return "pages/edit";
        }

        Coffee existingCoffee = coffeeService.getCoffeeById(coffee.getId());

        if (!coffeePicture.isEmpty()) {
            String path = "data/coffee_pictures/";
            File uploadFolder = new File(path);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String fileName = UUID.randomUUID() + coffeePicture.getOriginalFilename().substring(coffeePicture.getOriginalFilename().lastIndexOf("."));
            try {
                coffeePicture.transferTo(new File(uploadFolder.getAbsolutePath() + File.separator + fileName));
                coffee.setCoffeePicture(fileName);
            } catch (IOException e) {
                System.out.println("File upload error: " + e.getMessage());
                coffee.setCoffeePicture(existingCoffee.getCoffeePicture());
            }
        } else {
            coffee.setCoffeePicture(existingCoffee.getCoffeePicture());
        }

        coffeeService.updateCoffee(coffee.getId(), coffee);
        return "redirect:/";
    }

    /**
     * Deletes a coffee entry.
     *
     * @param id      Coffee ID.
     * @param session User session.
     * @return Redirects to homepage.
     */
    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        coffeeService.deleteCoffeeById(id);
        return "redirect:/";
    }

    /**
     * Searches for coffees by name.
     *
     * @param keyword Search term.
     * @param model   Spring's model object.
     * @return Search results view.
     */
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

    /**
     * Displays details of a specific coffee.
     *
     * @param id      Coffee ID.
     * @param model   Spring's model object.
     * @param session User session.
     * @return Detailed coffee view page.
     */
    @GetMapping("/coffee/{id}")
    public String view(@PathVariable int id, Model model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Coffee coffee = coffeeService.getCoffeeById(id);
        model.addAttribute("coffee", coffee);
        return "pages/coffee";
    }

    /**
     * Displays the coffee menu with optional search functionality.
     *
     * @param search  Search query.
     * @param session User session.
     * @param model   Spring's model object.
     * @return Coffee menu view.
     */
    @GetMapping("/menu")
    public String menu(@RequestParam(defaultValue = "") String search, HttpSession session, Model model) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("coffeeList", coffeeService.searchCoffeesByName(search));
        model.addAttribute("activeMenu", "menu");
        return "pages/coffee-menu";
    }

}
