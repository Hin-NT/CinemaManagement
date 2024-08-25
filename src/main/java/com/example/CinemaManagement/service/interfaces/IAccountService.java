package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.dto.AccountDTO;
import com.example.CinemaManagement.dto.PayRollDTO;
import com.example.CinemaManagement.entity.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAccountService extends IService<Account> {

    ResponseEntity<String> register(Account account);

    ResponseEntity<String> createAccByAdmin(Account account);

    AccountDTO verify(Account account);

    List<PayRollDTO> getPayrollAllEmployees();

    PayRollDTO getPayrollForEmployee(Account account);

    double getTotalSalaryForAllEmployees();

    List<Account> findAccountByUsername(String username);

}
