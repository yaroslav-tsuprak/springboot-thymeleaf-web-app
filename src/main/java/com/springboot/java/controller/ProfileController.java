package com.springboot.java.controller;

import com.springboot.java.entity.Mailbox;
import com.springboot.java.entity.Profile;
import com.springboot.java.entity.User;
import com.springboot.java.repository.ProfileRepository;
import com.springboot.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProfileController {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/list-profiles")
    public String showProfiles(Model model) {
        model.addAttribute("profiles", this.profileRepository.findAll());
        return "/admin/profiles";
    }

    @GetMapping("/admin/profile/showFormAdd")
    public String profileForm(Profile profile) {
        return "/admin/add-profile";
    }

    @PostMapping("/admin/profile/add")
    public String addProfile(@Valid Profile profile, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/admin/add-profile";
        }
        this.profileRepository.save(profile);
        return "redirect:/admin/list-profiles";
    }

    @GetMapping("/admin/profile/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Profile profile = this.profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid profile id: " + id));
        model.addAttribute("profile", profile);
        return "/admin/update-profile";
    }
}
