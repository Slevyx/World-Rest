package it.objectmethod.world.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.world.models.User;

@RestController
public class LoginController {

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestParam(name = "username", required = false) String username) {
		User user = null;
		ResponseEntity<User> response = null;
		if(username == null || username.isBlank()) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			user = new User();
			user.setUsername(username);		
			response = new ResponseEntity<>(user, HttpStatus.OK);
		}
		return response;
	}
}
