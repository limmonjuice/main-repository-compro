package com.limmon.midtermcoffee;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * Controller class for managing coffee data.
 */
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
    public String addCoffeeForm() {
        return "new";
    }

    @PostMapping("/save")
    public String saveCoffee(@RequestParam String name,
                             @RequestParam String type,
                             @RequestParam String size,
                             @RequestParam double price,
                             @RequestParam String roastLevel,
                             @RequestParam String origin,
                             @RequestParam(defaultValue="false") boolean isDecaf,
                             @RequestParam int stock,
                             @RequestParam String flavorNotes,
                             @RequestParam String brewMethod) {
        int newId = coffeeService.getCoffees().size() + 1;
        Coffee newCoffee = new Coffee(newId, name, type, size, price, roastLevel, origin, isDecaf, stock, flavorNotes, brewMethod);
        coffeeService.addCoffee(newCoffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        if (coffee != null) {
            model.addAttribute("coffee", coffee);
//            model.addAttribute("flavorNotes", coffee.getFlavorNotes()); // Pass flavorNotes directly as a string
            return "edit";
        }
        return "redirect:/";
    }


    @PostMapping("/update")
    public String updateCoffee(@RequestParam int id,
                               @RequestParam String name,
                               @RequestParam String type,
                               @RequestParam String size,
                               @RequestParam double price,
                               @RequestParam String roastLevel,
                               @RequestParam String origin,
                               @RequestParam(defaultValue="false") boolean isDecaf,
                               @RequestParam int stock,
                               @RequestParam String flavorNotes,
                               @RequestParam String brewMethod) {


        Coffee updatedCoffee = new Coffee(id, name, type, size, price, roastLevel, origin, isDecaf, stock,
                flavorNotes , brewMethod);
        coffeeService.updateCoffee(id, updatedCoffee);
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

