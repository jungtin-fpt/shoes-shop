package com.jungtin.service;

import com.jungtin.exception.DuplicateEntityException;
import com.jungtin.exception.EntityNotFoundException;
import com.jungtin.model.Account;
import com.jungtin.repository.AccountRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class AccountService {
    private final AccountRepository accountRepository;
    
    public AccountService() {
        this.accountRepository = new AccountRepository();
    }
    
    public void saveOrUpdate(HttpServletRequest request) {
        String username = request.getParameter("username");
        
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(request.getParameter("password"));
    
        if(request.getParameter("id") == null || request.getParameter("id").isBlank()) {
            // Create
            boolean existAccount = accountRepository.checkExistAccount(username);
            if(existAccount)
                throw new DuplicateEntityException(String.format("Duplicated : Account [username : %s] already existed", username));
            
            accountRepository.createAccount(account);
        } else {
            // Update
            Integer id = Integer.parseInt(request.getParameter("id"));
            account.setId(id);
            accountRepository.updateAccount(account);
        }
        
    }
    
    public List<Account> findAll() {
        return accountRepository.getAllAccount();
    }
    
    public Account findById(int id) {
        boolean exist = accountRepository.checkExistAccount_withId(id);
        if (!exist)
            throw new EntityNotFoundException(
                String.format("Account [id : %s] isn't exists", id));
        
        return accountRepository.getAccount(id);
    }
    
    public void delete(int id) {
        accountRepository.deleteAccount(id);
    }
    
    public Account findByUsernamePassword(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
    
        return accountRepository
            .getAccountByUserAndPass(username, password);
    }
    
    /*
    *   REGISTER
    * */
    public void register(HttpServletRequest request) throws DuplicateEntityException{
        String username = request.getParameter("username");
    
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(request.getParameter("password"));
    
        boolean existAccount = accountRepository.checkExistAccount(username);
        if(existAccount)
            throw new DuplicateEntityException(String.format("Duplicated : Account [username : %s] already existed", username));
    
        accountRepository.createAccount(account);
    }
}
