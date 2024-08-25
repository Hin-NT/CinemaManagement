package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.config.Utils;
import com.example.CinemaManagement.dto.AccountDTO;
import com.example.CinemaManagement.dto.PayRollDTO;
import com.example.CinemaManagement.entity.Account;
import com.example.CinemaManagement.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("")
    public ResponseEntity<List<AccountDTO>> getAllAccount() {
        List<Account> accounts = accountService.getAll();
        List<AccountDTO> accountDTOList = accounts.stream().map(AccountDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(accountDTOList);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable int accountId) {
        Account createAccount = new Account();
        createAccount.setAccountId(accountId);
        Account account = accountService.getById(createAccount);

        AccountDTO accountDTO = new AccountDTO(account);
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("/login")
    public AccountDTO login(@RequestBody Account account) {
        return accountService.verify(account);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Account account) {
        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid account data.");
        }

        if (account.getEmail() == null || account.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password are required.");
        }

        if(!Utils.isValidPassword(account.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 12 characters long, include an uppercase letter, a lowercase letter, a digit, a special character, and no spaces.");
        }

        return accountService.register(account);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/add")
    public ResponseEntity<String> createAccByAdmin(@RequestBody Account account) {
        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid account data.");
        }

        if (account.getEmail() == null || account.getPassword() == null || account.getRoles() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email, password and role are required.");
        }

        if(!Utils.isValidPassword(account.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 12 characters long, include an uppercase letter, a lowercase letter, a digit, a special character, and no spaces.");
        }

        return accountService.createAccByAdmin(account);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAccount(@RequestBody Account account) {
        return accountService.update(account);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable int accountId) {
        Account createAccount = new Account();
        createAccount.setAccountId(accountId);

        return accountService.delete(createAccount);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountDTO>> findAccountsByUsername(@RequestParam String username) {
        List<Account> accounts = accountService.findAccountByUsername(username);
        List<AccountDTO> accountDTOList = accounts.stream().map(AccountDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(accountDTOList);
    }

    //1. Hiển thị bảng lương của tất cả nhân viên trong tháng hiện tại (user / ngày công / số giờ làm / tổng số lương)
    @GetMapping("/payroll-all")
    public ResponseEntity<List<PayRollDTO>> findAccountsByPayroll() {
        return ResponseEntity.ok(accountService.getPayrollAllEmployees());
    }

    // 1. Hiển thị bảng lương của một nhân viên
    @GetMapping("/employee/{accountId}")
    public ResponseEntity<PayRollDTO> getPayrollForEmployee(@PathVariable int accountId) {
        Account account = new Account();
        account.setAccountId(accountId);
        PayRollDTO payRollDTO = accountService.getPayrollForEmployee(account);

        return new ResponseEntity<>(payRollDTO, HttpStatus.OK);
    }

    // 2. Hiển thị tổng lương của tất cả nhân viên
    @GetMapping("/total-salary")
    public ResponseEntity<Double> getTotalSalaryForAllEmployees() {
        double totalSalary = accountService.getTotalSalaryForAllEmployees();
        return new ResponseEntity<>(totalSalary, HttpStatus.OK);
    }

}
