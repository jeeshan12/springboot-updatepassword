package com.password.controller;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.password.model.User;
import com.password.request.UserRequest;
import com.password.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/changepassword/{id}")
	public ResponseEntity<String> changePassword(@PathVariable int id, @RequestBody UserRequest userRequest) {
		User user = userService.findByIdAndOldPassword(Long.valueOf(id), userRequest.getOldPassword());
		
		if (user != null) {

			if (!(user.getOldPassword().equals(userRequest.getOldPassword()))) {
				System.out.println("adaldkalk");
				throw new ConstraintViolationException("System Password and provided Old Password doesn't match", null, null);
			}

			if (StringUtils.isBlank(userRequest.getOldPassword())) {
				throw new ConstraintViolationException("It's mandatory to give old Password, to set a new Password", null, null);
			}

			if (StringUtils.isBlank(userRequest.getNewPassword())) {
				throw new ConstraintViolationException("New Password can not be empty or null", null, null);
			}

			double percentageMatched = StringUtils.getJaroWinklerDistance(user.getOldPassword(), userRequest.getNewPassword()) * 100;
			if (percentageMatched >= 80) {
				throw new ConstraintViolationException("Percentage of old and new password is equal and more than 80%", null, null);
			}
			//Number of digit present in string
			int validCriteriaForNoOfDigit = userRequest.getNewPassword().length() / 2;
			int digitCounter = 0;
			char[] charArray = userRequest.getNewPassword().toCharArray();
			for (char c : charArray) {
				if (Character.isDigit(c)) {
					digitCounter++;
				}
			}
			
			if (digitCounter >= validCriteriaForNoOfDigit) {
				throw new ConstraintViolationException("50% of password is number", null, null);
			}

			// special characters match
			String newPassword = userRequest.getNewPassword();

			String specialChars = "!@#$&*";
			int charCounter = 0;
			for (int i =0; i< newPassword.length(); i++) {
				if (specialChars.contains(newPassword.charAt(i)+"")) {
					charCounter++;
				}
			}

			if (charCounter > 4) {
				throw new ConstraintViolationException("More than 4 special Characters", null, null);
			}
			
			user.setOldPassword(userRequest.getNewPassword());
			user.setNewPassword(userRequest.getNewPassword());
			userService.save(user);
			return new ResponseEntity<String>("Password updated successfully", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Password not updated", HttpStatus.INTERNAL_SERVER_ERROR);
	}



}
