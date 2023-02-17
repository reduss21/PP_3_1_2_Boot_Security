package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
@Validated
public class AdminController {

    private final String firstPage = "redirect:/admin";
    private final UserService usServ;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService usServ, RoleService roleService) {
        this.usServ = usServ;
        this.roleService = roleService;
    }

    @GetMapping()
    public String users(Model model) {
        model.addAttribute("users", usServ.allUsers());
        return "users";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "new";
        usServ.saveUser(user);
        return firstPage;
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.allRoles());
        return "new";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", usServ.findUserById(id));
        model.addAttribute("allRoles", roleService.allRoles());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) return "edit";
        usServ.update(user);
        return firstPage;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        usServ.deleteUserById(id);
        return firstPage;
    }

}
