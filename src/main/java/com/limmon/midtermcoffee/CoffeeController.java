package com.limmon.midtermcoffee;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/")
    public String getCoffees(Model model) {
        model.addAttribute("coffees", coffeeService.getCoffees());
        return "index";
    }

    @GetMapping("/add")
    public String addCoffeeForm(Model model) {
        model.addAttribute("coffee", new Coffee());
        return "new";
    }

    @PostMapping("/save")
    public String saveCoffee(@Valid @ModelAttribute("coffee") Coffee coffee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "new";  // Return the form view with validation errors
        }
        int newId = coffeeService.getCoffees().size() + 1;
        coffee.setId(newId);
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        if (coffee != null) {
            model.addAttribute("coffee", coffee);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateCoffee(@Valid @ModelAttribute("coffee") Coffee coffee, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";  // Return the form view with validation errors
        }
        coffeeService.updateCoffee(coffee.getId(), coffee);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id) {
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
        return "index";
    }
}
