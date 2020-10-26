package com.springboot.java.controller;

import com.springboot.java.entity.Mailbox;
import com.springboot.java.entity.Profile;
import com.springboot.java.repository.MailboxRepository;
import com.springboot.java.repository.ProfileRepository;
import com.springboot.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MailboxController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailboxRepository mailboxRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/admin/showForm")
    public String mailForm(Mailbox mailbox) {
        return "admin/add-mailbox";
    }

    @GetMapping("/admin/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Mailbox mailbox = mailboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mailbox id: " + id));
        Iterable<Profile> profiles = profileRepository.findAll();
        profiles.forEach((Profile p) -> {
            if (p.getId() == mailbox.getProfileId()) {
                mailbox.setProfileName(p.getProfileName());
            }
        });
        model.addAttribute("profiles", profiles);
        model.addAttribute("mailbox", mailbox);
        return "admin/update-mailbox";
    }

    @GetMapping("/admin/list")
    public String mailList(Model model) {
        Iterable<Profile> _profiles = profileRepository.findAll();
        Map<Integer, Profile> profiles = new HashMap();
        _profiles.forEach((Profile p) -> profiles.put(p.getId(), p));

        Iterable<Mailbox> mailboxes = mailboxRepository.findAll();
        mailboxes.forEach((Mailbox m) -> m.setProfileName(profiles.get(m.getProfileId()).getProfileName()));
        model.addAttribute("mailboxes", mailboxes);
        return "admin/index";
    }

    @PostMapping("admin/add")
    public String addMail(@Valid Mailbox mailbox, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/add-mailbox";
        }

        this.mailboxRepository.save(mailbox);
        return "redirect:admin/list";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteMailbox(@PathVariable("id") int id, Model model) {
        Mailbox mailbox = this.mailboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mailbox id: " + id));

        this.mailboxRepository.delete(mailbox);
        model.addAttribute("mailbox", this.mailboxRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/changestate/{id}")
    public String blockMailbox(@PathVariable("id") int id, Model model) {
        Mailbox mailbox = this.mailboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mailbox id: " + id));
        if(mailbox.isActive())
        {
            mailbox.setActive(false);
        }
        else
        {
            mailbox.setActive(true);
        }
        this.mailboxRepository.save(mailbox);
        model.addAttribute("mailboxes", this.mailboxRepository.findAll());
        return "admin/index";
    }

    @PostMapping("admin/update/{id}")
    public String updateMailbox(@PathVariable("id") int id, @Valid Mailbox mailbox, BindingResult result, Model model) {
        if (result.hasErrors()) {
            mailbox.setId(id);
            return "admin/update-mailbox";
        }
        this.mailboxRepository.save(mailbox);
        model.addAttribute("mailboxes", this.mailboxRepository.findAll());
        return "redirect:admin/list";
    }
}
