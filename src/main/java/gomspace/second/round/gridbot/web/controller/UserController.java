package gomspace.second.round.gridbot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gomspace.second.round.gridbot.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
    private final UserService userService; 

    
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
