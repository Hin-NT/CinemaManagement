package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.config.Utils;
import com.example.CinemaManagement.dto.AccountDTO;
import com.example.CinemaManagement.dto.HoursWorkerDTO;
import com.example.CinemaManagement.dto.PayRollDTO;
import com.example.CinemaManagement.entity.Account;
import com.example.CinemaManagement.entity.TimeKeeping;
import com.example.CinemaManagement.enums.AccountType;
import com.example.CinemaManagement.repository.AccountRepository;
import com.example.CinemaManagement.repository.TimeKeepingRepository;
import com.example.CinemaManagement.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TimeKeepingRepository timeKeepingRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    ApplicationContext context;

    //Register a user account
    @Override
    public ResponseEntity<String> register(Account account) {
        try {

            if(accountRepository.findAccountByEmail(account.getEmail()) == null) {
                account.setPassword(Utils.encrypt(account.getPassword()));
                if(account.getRoles() == null) {
                    account.setRoles(AccountType.ROLE_CUSTOMER);
                }
                accountRepository.save(account);
                return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Register Admin account
    @Override
    public ResponseEntity<String> createAccByAdmin(Account account) {
        try {
            // Check if email does not exist then register
            if(accountRepository.findAccountByEmail(account.getEmail()) == null) {
                // Encrypt password
                account.setPassword(Utils.encrypt(account.getPassword()));
                accountRepository.save(account);
                return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Accounts are based on email and password.
    @Override
    public AccountDTO verify(Account account) {
        Authentication auth =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword()));
        if (auth.isAuthenticated()) {
            Account acc = accountRepository.findAccountByEmail(account.getEmail());
            String token = jwtService.generateToken(account.getEmail());
            return new AccountDTO(acc, token);
        }
        return null;
    }

    //Search account by username
    @Override
    public List<Account> findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    //Get a list of all accounts
    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    //Update account information
    @Override
    public ResponseEntity<String> update(Account account) {
        Optional<Account> existingAccount = accountRepository.findById(account.getAccountId());
        if (existingAccount.isPresent()) {
            try {
                Account accountToUpdate = existingAccount.get();
                accountToUpdate.setAddress(account.getAddress());
                accountToUpdate.setDateOfBirth(account.getDateOfBirth());
                accountToUpdate.setFullName(account.getFullName());
                accountToUpdate.setGender(account.getGender());
                accountToUpdate.setPhone(account.getPhone());
                accountToUpdate.setRoles(account.getRoles());
                accountToUpdate.setSalary(account.getSalary());
                accountToUpdate.setUsername(account.getUsername());
                accountRepository.save(accountToUpdate);
                return ResponseEntity.status(HttpStatus.OK).body("Account updated successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update account due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with ID: " + account.getAccountId());
        }
    }

    //Get account information by ID
    @Override
    public Account getById(Account account) {
        return accountRepository.findById(account.getAccountId()).orElse(null);
    }

    @Override
    public ResponseEntity<String> add(Account account) {
        return null;
    }

    //Delete account
    @Override
    public ResponseEntity<String> delete(Account account) {
        Account exsitedAccount = this.getById(account);
        if (exsitedAccount != null) {
            try {
                accountRepository.delete(exsitedAccount);
                return ResponseEntity.status(HttpStatus.OK).body("Account deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update account due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with ID: " + account.getAccountId());
        }
    }

    //Calculate and display payroll of all employees in the current month
    //Including number of working days, number of working hours, and total salary
    public List<PayRollDTO> getPayrollAllEmployees() {
        YearMonth currentMonth = YearMonth.now(); //Get the current month and year
        LocalDate startOfMonth = currentMonth.atDay(1); // Get the first day of the current month
        LocalDate endOfMonth = currentMonth.atEndOfMonth(); // Get the last day of the current month

        // Get a list of all staff / admin accounts
        List<Account> employees = accountRepository.findAll().stream()
                .filter(account -> account.getRoles() == AccountType.ROLE_EMPLOYEE ||
                        account.getRoles() == AccountType.ROLE_ADMINISTRATOR)
                .toList();

        List<PayRollDTO> payRollDTOList = new ArrayList<>();

        for (Account employee : employees) {
            List<TimeKeeping> timeKeepings = timeKeepingRepository.findByAccountAndStartTimeBetween(
                    employee, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59)
            );

            double totalHoursWorked = 0.0;

            System.out.println("Employee: " + employee.getFullName());
            System.out.println("Date\t\tStart Time\tEnd Time\tHours Worked");

            List<HoursWorkerDTO> hoursWorkerList = new ArrayList<>();

            for (TimeKeeping timeKeeping : timeKeepings) {
                LocalDateTime startTime = timeKeeping.getStartTime();
                LocalDateTime endTime = timeKeeping.getEndTime();

                Duration duration = Duration.between(startTime, endTime);
                long hoursWorked = duration.toHours();
                totalHoursWorked += hoursWorked;

                hoursWorkerList.add(new HoursWorkerDTO(startTime.toLocalDate(), startTime.toLocalTime(), endTime.toLocalTime(), hoursWorked));
            }

            double totalSalary = totalHoursWorked * employee.getSalary();
            int totalDaysWorked = timeKeepings.size();

            payRollDTOList.add(new PayRollDTO(employee.getAccountId(), employee.getFullName(), totalDaysWorked, totalHoursWorked, totalSalary, hoursWorkerList));
        }

        return payRollDTOList;
    }

    //Calculate and display the payroll for a specific employee for the current month
    public PayRollDTO getPayrollForEmployee(Account account) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        Account employee = accountRepository.findById(account.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        List<TimeKeeping> timeKeepings = timeKeepingRepository.findByAccountAndStartTimeBetween(
                employee, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59)
        );

        double totalHoursWorked = 0.0;
        List<HoursWorkerDTO> hoursWorkerList = new ArrayList<>();

        for (TimeKeeping timeKeeping : timeKeepings) {
            LocalDateTime startTime = timeKeeping.getStartTime();
            LocalDateTime endTime = timeKeeping.getEndTime();

            Duration duration = Duration.between(startTime, endTime);
            long hoursWorked = duration.toHours();
            totalHoursWorked += hoursWorked;

            hoursWorkerList.add(new HoursWorkerDTO(startTime.toLocalDate(), startTime.toLocalTime(), endTime.toLocalTime(), hoursWorked));
        }

        double totalSalary = totalHoursWorked * employee.getSalary();
        int totalDaysWorked = timeKeepings.size();

        return new PayRollDTO(employee.getAccountId(), employee.getFullName(), totalDaysWorked, totalHoursWorked, totalSalary, hoursWorkerList);
    }

    // 3. Display total salary of all employees
    public double getTotalSalaryForAllEmployees() {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        List<Account> employees = accountRepository.findAll().stream()
                .filter(account -> account.getRoles() == AccountType.ROLE_EMPLOYEE ||
                        account.getRoles() == AccountType.ROLE_ADMINISTRATOR)
                .toList();

        double totalSalaryForAllEmployees = 0.0;

        for (Account employee : employees) {
            List<TimeKeeping> timeKeepings = timeKeepingRepository.findByAccountAndStartTimeBetween(
                    employee, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59)
            );

            double totalHoursWorked = 0.0;

            for (TimeKeeping timeKeeping : timeKeepings) {
                LocalDateTime startTime = timeKeeping.getStartTime();
                LocalDateTime endTime = timeKeeping.getEndTime();

                Duration duration = Duration.between(startTime, endTime);
                long hoursWorked = duration.toHours();
                totalHoursWorked += hoursWorked;
            }

            double totalSalary = totalHoursWorked * employee.getSalary();
            totalSalaryForAllEmployees += totalSalary;
        }

        return totalSalaryForAllEmployees;
    }

}
