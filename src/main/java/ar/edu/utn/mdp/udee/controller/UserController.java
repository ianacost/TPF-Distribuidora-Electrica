package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.model.UserType;
import ar.edu.utn.mdp.udee.model.response.PostResponse;
import ar.edu.utn.mdp.udee.service.UserService;
import ar.edu.utn.mdp.udee.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserTypeService userTypeService;

    @Autowired
    public UserController(UserService userService, UserTypeService userTypeService) {
        this.userService = userService;
        this.userTypeService = userTypeService;
    }

    @PostMapping("/login")
    public Integer login(@RequestHeader("authorization") String credentials) {
        String[] userData = credentials.split(" ")[1].split(":");
        String username = userData[0];
        String password = userData[1];
        return userService.login(username, password);
    }

    @PostMapping
    public PostResponse add(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping
    public PaginationResponse<User> get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "50") Integer size) {
        return userService.get(page, size);
    }

    @GetMapping("{id}")
    public User getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PostMapping("/type")
    public PostResponse addType(@RequestBody UserType userType) {
        return userTypeService.addType(userType);
    }

    @GetMapping("/type")
    public List<UserType> getTypes() {
        return userTypeService.getTypes();
    }

    @PutMapping("/{id}/type/{typeId}")
    public Integer addTypeToUser(@PathVariable Integer id, @PathVariable Integer typeId) {
        return userService.addTypeToUser(id, typeId);
    }
}
