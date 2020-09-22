package com.jungtin.repository;

import com.jungtin.config.DBConfig;
import com.jungtin.model.Invoice;
import com.jungtin.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceRepository {
    
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
    
    public void createInvoice(Invoice invoice) {
        conn  = DBConfig.getConn();
    
        String CREATE_INVOICE_SQL = "INSERT INTO invoice(cust_name, cust_addr, cust_phone_no, create_time) VALUES(?, ?, ?, ?)";
        String INSERT_INVOICE_ITEM_SQL = "INSERT INTO invoice_detail(invoice_id, product_id, quantity) VALUES(?, ?, ?)";
        ResultSet rs = null;
    
        try (
            PreparedStatement CREATE_PS = conn.prepareStatement(CREATE_INVOICE_SQL, Statement.RETURN_GENERATED_KEYS)){
        
            CREATE_PS.setString(1, invoice.getCustName());
            CREATE_PS.setString(2, invoice.getCustAddress());
            CREATE_PS.setString(3, invoice.getCustPhoneNo());
            CREATE_PS.setTimestamp(4, Timestamp.valueOf(invoice.getCreateDateTime()));
            CREATE_PS.execute();
        
            rs = CREATE_PS.getGeneratedKeys();
            rs.next();
            
            int returnedInvoiceId = rs.getInt(1);
            insertItem(returnedInvoiceId, invoice.getProducts());
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
    }
    
    private void insertItem(int invoiceId, HashMap<Product, Integer> products) {
        String INSERT_INVOICE_ITEM_SQL = "INSERT INTO invoice_detail(invoice_id, product_id, quantity) VALUES(?, ?, ?)";
        ResultSet rs = null;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            try (
                PreparedStatement CREATE_PS = conn.prepareStatement(INSERT_INVOICE_ITEM_SQL, Statement.RETURN_GENERATED_KEYS)){
                CREATE_PS.setInt(1, invoiceId);
                CREATE_PS.setInt(2, entry.getKey().getId());
                CREATE_PS.setInt(3, entry.getValue());
                CREATE_PS.execute();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }
    
    public List<Invoice> getAllInvoice() {
        conn  = DBConfig.getConn();

        String GET_INVOICE_SQL = "SELECT id, cust_name, cust_addr, cust_phone_no, "
            + "create_time FROM invoice";
        ResultSet rs = null;

        try (
            PreparedStatement ps = conn.prepareStatement(GET_INVOICE_SQL)){
            List<Invoice> invoices = new ArrayList<>();
            rs = ps.executeQuery();
            while(rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt(1));
                invoice.setCustName(rs.getString(2));
                invoice.setCustAddress(rs.getString(3));
                invoice.setCustPhoneNo(rs.getString(4));
                invoice.setCreateDateTime(rs.getTimestamp(5).toLocalDateTime());

                invoices.add(invoice);
            }
            return invoices;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
        return null;
    }

    public Invoice getInvoice(Integer id) {
        conn  = DBConfig.getConn();
    
        String GET_INVOICE_SQL = "SELECT id, cust_name, cust_addr, cust_phone_no, "
            + "create_time FROM invoice WHERE id = ?";
        ResultSet rs = null;

        try (
            PreparedStatement ps = conn.prepareStatement(GET_INVOICE_SQL)){
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt(1));
                invoice.setCustName(rs.getString(2));
                invoice.setCustAddress(rs.getString(3));
                invoice.setCustPhoneNo(rs.getString(4));
                invoice.setCreateDateTime(rs.getTimestamp(5).toLocalDateTime());
    
                HashMap<Product, Integer> products = getInvoiceDetail(id);
                invoice.setProducts(products);
                return invoice;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
        return null;
    }
    
    private HashMap<Product, Integer> getInvoiceDetail(int id) {
        conn  = DBConfig.getConn();
    
        String GET_INVOICE_SQL = "SELECT p.id as prod_id, "
                + "p.name as prod_name, "
                + "p.img_url as prod_img, "
                + "p.price as prod_price, "
                + "i_de.quantity as quantity "
            + "FROM invoice_detail as i_de INNER JOIN product as p ON i_de.product_id = p.id "
            + "WHERE i_de.invoice_id = ?";
        
        ResultSet rs = null;
        try (
            PreparedStatement ps = conn.prepareStatement(GET_INVOICE_SQL)){
            ps.setInt(1, id);
            
            HashMap<Product, Integer> details = new HashMap<>();
            rs = ps.executeQuery();
            while(rs.next()) {
                Product product = new Product(
                    rs.getString("prod_name"),
                    rs.getString("prod_img"),
                    rs.getDouble("prod_price"),
                0, null);
                product.setId(rs.getInt("prod_id"));
                
                details.put(product, rs.getInt("quantity"));
            }
            return details;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
        return null;
    }

//    public boolean deleteInvoice(Integer id) {
//        conn  = DBConfig.getConn();
//        String DELETE_INVOICE_SQL = "DELETE FROM invoice WHERE id = ?";
//        try (
//            PreparedStatement ps = conn.prepareStatement(DELETE_INVOICE_SQL)){
//            ps.setInt(1, id);
//            if(ps.executeUpdate() >= 1)
//                return true;
//        } catch (SQLException exc) {
//            exc.printStackTrace();
//        } finally {
//            closeConn(conn);
//        }
//        return false;
//    }


//    public boolean updateInvoice(Invoice invoice) {
//        conn  = DBConfig.getConn();
//        int rs = -1;
//        String UPDATE_INVOICE_SQL = "UPDATE invoice SET name = ?, "
//            + "img_url = ? "
//            + "price = ? "
//            + "quantity = ? "
//            + "last_import_date = ? "
//            + "WHERE id = ?";
//
//        try (
//            PreparedStatement ps = conn.prepareStatement(UPDATE_INVOICE_SQL)){
//            ps.setString(1, invoice.getName());
//            ps.setString(2, invoice.getImageUrl());
//            ps.setDouble(3, invoice.getPrice());
//            ps.setInt(4, invoice.getQuantity());
//            ps.setDate(5, Date.valueOf(invoice.getLastImportDate()));
//            rs = ps.executeUpdate();
//        } catch (SQLException exc) {
//            exc.printStackTrace();
//            return false;
//        } finally {
//            closeConn(conn);
//        }
//        return rs >= 1;
//    }
    
    /*
    *   Tool
    * */
    public boolean checkExistInvoice_withId(int id) {
        conn  = DBConfig.getConn();
    
        String CHECK_EXISTING_INVOICE_SQL = "SELECT id FROM invoice WHERE id = ?";
        ResultSet rs = null;
        try (
            PreparedStatement CHECK_PS = conn.prepareStatement(CHECK_EXISTING_INVOICE_SQL)){
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
