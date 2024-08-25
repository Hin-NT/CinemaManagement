package com.example.CinemaManagement.entity;

import com.example.CinemaManagement.enums.AccountType;
import com.example.CinemaManagement.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_account")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "roles")
    private AccountType roles;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "salary")
    private double salary;

    @OneToMany(mappedBy = "account")
    private List<ProductOrder> productOrders;

    @OneToMany(mappedBy = "account")
    private List<MovieOrder> movieOrders;

    @OneToMany(mappedBy = "account")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "account")
    private List<TimeKeeping> timekeepings;

    public Account(AccountType roles, String username, String password, String fullName, Gender gender,
                   String phone, String email, String address, LocalDate dateOfBirth,
                   LocalDate createdDate, double salary) {
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.createdDate = createdDate;
        this.salary = salary;
    }

}
