package com.jungtin.repository;

import com.jungtin.config.DBConfig;
import com.jungtin.model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    
    private Connection conn = null;
    
    private void closeConn(Connection conn) {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
    
    public void createAccount(Account account) {
        conn  = DBConfig.getConn();
    
        String CREATE_ACCOUNT_SQL = "INSERT INTO account(username, password) VALUES(?, ?)";
        ResultSet rs = null;
    
        try (
            PreparedStatement CREATE_PS = conn.prepareStatement(CREATE_ACCOUNT_SQL, Statement.RETURN_GENERATED_KEYS)){
        
            CREATE_PS.setString(1, account.getUsername());
            CREATE_PS.setString(2, account.getPassword());
            CREATE_PS.execute();
        
            rs = CREATE_PS.getGeneratedKeys();
            rs.next();
            account.setId(rs.getInt(1));
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
    }
    
    public List<Account> getAllAccount() {
        conn  = DBConfig.getConn();

        String GET_ACCOUNT_SQL = "SELECT id, username, password FROM account";
        ResultSet rs = null;

        try (
            PreparedStatement ps = conn.prepareStatement(GET_ACCOUNT_SQL)){
            List<Account> accounts = new ArrayList<>();
            
            rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(
                    rs.getString(2),
                    rs.getString(3)
                );
                account.setId(rs.getInt(1));

                accounts.add(account);
            }
            return accounts;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
        return null;
    }
    
    public Account getAccount(Integer id) {
        conn  = DBConfig.getConn();

        String GET_ACCOUNT_SQL = "SELECT id, username, password FROM account a WHERE a.id = ?";
        ResultSet rs = null;

        try (
            PreparedStatement ps = conn.prepareStatement(GET_ACCOUNT_SQL)){
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                Account account = new Account(
                    rs.getString(2),
                    rs.getString(3)
                );
                account.setId(rs.getInt(1));

                return account;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
        return null;
    }

    public boolean deleteAccount(Integer id) {
        conn  = DBConfig.getConn();
        String DELETE_ACCOUNT_SQL = "DELETE FROM account WHERE id = ?";
        try (
            PreparedStatement ps = conn.prepareStatement(DELETE_ACCOUNT_SQL)){
            ps.setInt(1, id);
            if(ps.executeUpdate() >= 1)
                return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
        return false;
    }


    public boolean updateAccount(Account account) {
        conn  = DBConfig.getConn();
        int rs = -1;
        String UPDATE_ACCOUNT_SQL = "UPDATE account SET username = ?, "
            + "password = ? "
            + "WHERE id = ?";

        try (
            PreparedStatement ps = conn.prepareStatement(UPDATE_ACCOUNT_SQL)){
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setInt(3, account.getId());
            rs = ps.executeUpdate();

        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } finally {
            closeConn(conn);
        }
        return rs >= 1;
    }

    public Account getAccountByUserAndPass(String username, String password) {
        conn  = DBConfig.getConn();

        String GET_ACCOUNT_SQL = "SELECT id, username, password FROM account a "
            + "WHERE a.username = ? AND a.password = ?";
        ResultSet rs = null;

        try (
            PreparedStatement ps = conn.prepareStatement(GET_ACCOUNT_SQL)){
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt(1));
                account.setUsername(rs.getString(2));
                account.setPassword(rs.getString(3));
                return account;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
        return null;
    }
    
    /*
    *   Tool
    * */
    public boolean checkExistAccount(String username) {
        conn  = DBConfig.getConn();
    
        String CHECK_EXISTING_ACCOUNT_SQL = "SELECT id FROM account WHERE username = ?";
        ResultSet rs = null;
    
        try (
            PreparedStatement CHECK_PS = conn.prepareStatement(CHECK_EXISTING_ACCOUNT_SQL)){
        
            // check exist user
            CHECK_PS.setString(1, username);
            rs = CHECK_PS.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
    
        return false;
    }
    
    public boolean checkExistAccount_withId(int id) {
        conn  = DBConfig.getConn();
        
        String CHECK_EXISTING_ACCOUNT_SQL = "SELECT id FROM account WHERE id = ?";
        ResultSet rs = null;
        
        try (
            PreparedStatement CHECK_PS = conn.prepareStatement(CHECK_EXISTING_ACCOUNT_SQL)){
            
            // check exist user
            CHECK_PS.setInt(1, id);
            rs = CHECK_PS.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
        
        return false;
    }
}
