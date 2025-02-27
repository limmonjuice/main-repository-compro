package com.limmon.sampol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class SampolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampolApplication.class, args);
	}

	@GetMapping("/about-me")
	public String aboutMe(Model model) {
		String fullName = "Zaimond Lim";
		model.addAttribute("name", fullName);
		String favoriteQuote = "Do nothing if you wish to be nothing";
		model.addAttribute("quote", favoriteQuote);
		String descriptionAboutSelf = """
			I am a caring and soft-spoken individual who believes in the power of patience and dedication.
			With a strong work ethic and a passion for continuous learning, I always strive to give my best in everything I do.
			Whether it’s tackling complex problems or helping others, I approach each task with diligence and perseverance.
			My goal is to create meaningful solutions while fostering a positive and supportive environment for those around me.
			""";
		model.addAttribute("description", descriptionAboutSelf);
	return "about_me";
	}
}
