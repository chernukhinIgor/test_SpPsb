package hello.controller;

import javax.validation.Valid;

import hello.model.User;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
public class WebController extends WebMvcConfigurerAdapter {
    @Autowired
    private UserService userService;
    
//    @GetMapping("/")
//    public String showForm(User user, Model model) {
//        addDataToModel(model);
//        return "form";
//    }
//
//    @PostMapping(value = "/", params = "new")
//    public String addNewUser(@Valid User user, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            addDataToModel(model);
//            return "form";
//        }else
//            userService.addUser(user);
//        return "redirect:/";
//    }
//
//    @PostMapping(value = "/", params = "delete")
//    public String deleteUserByID(@ModelAttribute("id")long id, Model model) {
//        try{
//            userService.deleteUserById(id);
//        }catch (Exception ex){
//            // Error...
//        }
//        return "redirect:/";
//    }
//
//    private void addDataToModel(Model model){
//        model.addAttribute("users",userService.getAllUsers());
//    }
}