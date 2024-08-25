package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.Account;
import com.example.CinemaManagement.entity.AccountPrincipal;
import com.example.CinemaManagement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepo.findAccountByEmail(email);
        if(account == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new AccountPrincipal(account);
    }

}
