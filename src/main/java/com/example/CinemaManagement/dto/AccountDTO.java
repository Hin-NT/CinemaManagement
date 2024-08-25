package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.Account;
import com.example.CinemaManagement.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AccountDTO {
    private int accountId;
    private String roles;
    private String username;
    private String fullName;
    private Gender gender;
    private String phone;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate createdDate;
    private double salary;
    private String token;

    public AccountDTO(Account account) {
        this.accountId = account.getAccountId();
        this.roles = account.getRoles().name();
        this.username = account.getUsername();
        this.fullName = account.getFullName();
        this.gender = account.getGender();
        this.phone = account.getPhone();
        this.email = account.getEmail();
        this.address = account.getAddress();
        this.dateOfBirth = account.getDateOfBirth();
        this.createdDate = account.getCreatedDate();
        this.salary = account.getSalary();
    }

    public AccountDTO(Account account, String token) {
        this.accountId = account.getAccountId();
        this.roles = account.getRoles().name();
        this.username = account.getUsername();
        this.fullName = account.getFullName();
        this.gender = account.getGender();
        this.phone = account.getPhone();
        this.email = account.getEmail();
        this.address = account.getAddress();
        this.dateOfBirth = account.getDateOfBirth();
        this.createdDate = account.getCreatedDate();
        this.salary = account.getSalary();
        this.token = token;
    }
}
