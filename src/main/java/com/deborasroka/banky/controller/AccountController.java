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
import com.deborasroka.banky.model.Account;
import com.deborasroka.banky.model.AccountType;
import com.deborasroka.banky.service.AccountService;
import com.deborasroka.banky.service.RoleService;
import com.deborasroka.banky.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/banky")
public class AccountController {

	@Autowired
	AccountService accountService;
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;


	@GetMapping(value="/allAccounts", produces = {"application/json" })
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public List<Account> list() {
		return accountService.listAllAccounts();
	}

	@PostMapping(value = "/createAccount")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public ResponseEntity<?> addAccount(@Valid @RequestBody Account account ){
		account.setAccountCreationDate(LocalDateTime.now());
		
		try {
			
			if (userService.findUserByIDNoOptional(account.getUserID())==null) {
				System.out.println("No user was found in order to add an account ");
				return new ResponseEntity<>("User ID is invalid", HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			System.out.println("No user was found in order to add an account "+e);
			return new ResponseEntity<>("User ID invalid, try again ", HttpStatus.CONFLICT);
		}


		System.out.println("This is the account " + account);

		try {
			account.setAccountType(AccountType.SAVINGS);
			accountService.save(account);
			return new ResponseEntity<>(accountService.findAccountsByUser(account.getUserID()), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to save, please check fields " , HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping(value="/findAccountByID/{AccountID}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public Account findAccountByID(@PathVariable String AccountID) {

		try {
			return accountService.findAccountByID(AccountID);
		} catch(Exception e) {

			return null;
		}

	}


	@GetMapping(value="/findAllAccountsByUserID/{UserID}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public List<Account> findAccountsByUser(@PathVariable String UserID) {

		try {
			return accountService.findAccountsByUser(UserID);
		} catch(Exception e) {

			return null;
		}

	}
	
	@GetMapping(value="/findAllSavingsAccountsByUserID/{UserID}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public List<Account> findSavingAccountsByUser(@PathVariable String UserID) {

		try {
			return accountService.findSavingAccountsByUser(UserID);
		} catch(Exception e) {

			return null;
		}
	}

	@DeleteMapping(value="/deleteAccount/{AccountID}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Map<String, Object> deleteAccount(@PathVariable String AccountID) {
		Map<String, Object> responseMap = new HashMap<>();
		try {
			accountService.delete(accountService.findAccountByID(AccountID));
			responseMap.put("status", 200);
			responseMap.put("message", "Success");
			return responseMap;

		} catch(Exception e) {
			responseMap.put("status", 404);
			responseMap.put("message", "Failed deletion");
			return responseMap;

		}

	}

	@PutMapping(value="/updateAccount")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String updateAccount(@RequestBody Map<String, String> update) {

		update.forEach((k,v) -> System.out.println("Key = "
				+ k + ", Value = " + v));

		accountService.updateAccount(update);
		return "updates sucessfull";

	}


}
