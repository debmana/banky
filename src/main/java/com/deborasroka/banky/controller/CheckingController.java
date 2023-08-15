package com.deborasroka.banky.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deborasroka.banky.model.AccountType;
import com.deborasroka.banky.model.CheckingAccount;
import com.deborasroka.banky.service.CheckingAccountService;
import com.deborasroka.banky.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/banky/checking")
public class CheckingController {

	@Autowired
	CheckingAccountService checkingAccountService;
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/createCheckingAccount")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public ResponseEntity<?> addCheckingAccount(@Valid @RequestBody CheckingAccount checkingAccount ){
		checkingAccount.setAccountCreationDate(LocalDateTime.now());

		try {
			if (userService.findUserByIDNoOptional(checkingAccount.getUserID())==null) {
				System.out.println("No user was found in order to add an account ");
				return new ResponseEntity<>("User ID is invalid", HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			System.out.println("No user was found in order to add an account "+e);
			return new ResponseEntity<> ("User ID is invalid", HttpStatus.CONFLICT);
		}


		System.out.println("This is the account " + checkingAccount);

		try {
			checkingAccount.setAccountType(AccountType.CHECKING);
			checkingAccountService.save(checkingAccount);
			return new ResponseEntity<>(checkingAccountService.findCheckingAccountsByUser(checkingAccount.getUserID()), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to save, please check fields ",  HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value="/allCheckingAccounts", produces = {"application/json" })
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public List<CheckingAccount> list() {
		return checkingAccountService.listAllAccounts();
	}
	
	@GetMapping(value="/findCheckingAccountByID/{AccountID}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public CheckingAccount findCheckingAccountByID(@PathVariable String AccountID) {

		try {
			return checkingAccountService.findCheckingAccountByID(AccountID);
		} catch(Exception e) {

			return null;
		}
	}
	
	@GetMapping(value="/findAllAccountsByUserID/{UserID}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public List<CheckingAccount> findAccountsByUser(@PathVariable String UserID) {

		try {
			return checkingAccountService.findCheckingAccountsByUser(UserID);
		} catch(Exception e) {

			return null;
		}

	}
	
	@PutMapping(value="/updateCheckingAccount")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String updateCheckingAccount(@RequestBody Map<String, String> update) {

		update.forEach((k,v) -> System.out.println("Key = "
				+ k + ", Value = " + v));

		checkingAccountService.updateAccount(update);
		return "updates sucessfull";

	}
	
	@DeleteMapping(value="/deleteCheckingAccount/{AccountID}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Map<String, Object> deleteAccount(@PathVariable String AccountID) {
		Map<String, Object> responseMap = new HashMap<>();
		try {
			checkingAccountService.delete(checkingAccountService.findCheckingAccountByID(AccountID));
			responseMap.put("status", 200);
			responseMap.put("message", "Success");
			return responseMap;

		} catch(Exception e) {
			System.out.println("Exception trying to delete "+e);
			responseMap.put("status", 404);
			responseMap.put("message", "Failed deletion");
			return responseMap;

		}

	}
	
	
	
}
