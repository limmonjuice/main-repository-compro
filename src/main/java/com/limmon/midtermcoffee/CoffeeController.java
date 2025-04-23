package com.limmon.midtermcoffee;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CoffeeController {

    private final CoffeeService coffeeService;

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
        model.addAttribute("coffee", new Coffee()); // Add empty Coffee object for form binding
        return "new";
    }

    @PostMapping("/save")
    public String saveCoffee(@ModelAttribute("coffee") @Valid Coffee coffee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new";
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
    public String updateCoffee(@ModelAttribute("coffee") @Valid Coffee coffee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
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
