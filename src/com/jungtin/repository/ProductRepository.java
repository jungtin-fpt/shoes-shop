package com.jungtin.repository;

import com.jungtin.config.DBConfig;
import com.jungtin.model.Product;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    
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
    
    public void createProduct(Product product) {
        conn  = DBConfig.getConn();
    
        String CREATE_PRODUCT_SQL = "INSERT INTO product(name, img_url, price, quantity, last_import_date) VALUES(?, ?, ?, ?, ?)";
        ResultSet rs = null;
    
        try (
            PreparedStatement CREATE_PS = conn.prepareStatement(CREATE_PRODUCT_SQL, Statement.RETURN_GENERATED_KEYS)){
        
            CREATE_PS.setString(1, product.getName());
            CREATE_PS.setString(2, product.getImageUrl());
            CREATE_PS.setDouble(3, product.getPrice());
            CREATE_PS.setInt(4, product.getQuantity());
            CREATE_PS.setDate(5, Date.valueOf(product.getLastImportDate()));
            CREATE_PS.execute();
        
            rs = CREATE_PS.getGeneratedKeys();
            rs.next();
            product.setId(rs.getInt(1));
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
    }
    
    public void updateQuantity(int productId, int buyQuantity) {
        conn  = DBConfig.getConn();
        
        String UPDATE_QUANTITY_SQL = "UPDATE product SET quantity = (SELECT quantity FROM product WHERE id = ?) - ? WHERE id = ?";
        ResultSet rs = null;
        try (
            PreparedStatement CREATE_PS = conn.prepareStatement(UPDATE_QUANTITY_SQL)){
            
            CREATE_PS.setInt(1, productId);
            CREATE_PS.setInt(2, buyQuantity);
            CREATE_PS.setInt(3, productId);
            CREATE_PS.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
    }
    
    public List<Product> getAllProduct() {
        conn  = DBConfig.getConn();

        String GET_PRODUCT_SQL = "SELECT id, name, img_url, price, quantity, last_import_date FROM product";
        ResultSet rs = null;

        try (
            PreparedStatement ps = conn.prepareStatement(GET_PRODUCT_SQL)){
            List<Product> products = new ArrayList<>();
            
            rs = ps.executeQuery();
            while(rs.next()) {
                Product product = new Product(
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDouble(4),
                    rs.getInt(5),
                    rs.getDate(6).toLocalDate()
                );
                product.setId(rs.getInt(1));

                products.add(product);
            }
            
            rs.close();
            return products;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
        return null;
    }
    
    public Product getProduct(Integer id) {
        conn  = DBConfig.getConn();

        String GET_PRODUCT_SQL = "SELECT id, name, img_url, price, quantity, last_import_date FROM product a WHERE a.id = ?";
        ResultSet rs = null;

        try (
            PreparedStatement ps = conn.prepareStatement(GET_PRODUCT_SQL)){
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                Product product = new Product(
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDouble(4),
                    rs.getInt(5),
                    rs.getDate(6).toLocalDate()
                );
                product.setId(rs.getInt(1));

                return product;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeConn(conn);
        }
        return null;
    }

    public boolean deleteProduct(Integer id) {
        conn  = DBConfig.getConn();
        String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE id = ?";
        try (
            PreparedStatement ps = conn.prepareStatement(DELETE_PRODUCT_SQL)){
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


    public boolean updateProduct(Product product) {
        conn  = DBConfig.getConn();
        int rs = -1;
        String UPDATE_PRODUCT_SQL = "UPDATE product SET name = ?, "
            + "img_url = ?, "
            + "price = ?, "
            + "quantity = ?, "
            + "last_import_date = ? "
            + "WHERE id = ?";

        try (
            PreparedStatement ps = conn.prepareStatement(UPDATE_PRODUCT_SQL)){
            ps.setString(1, product.getName());
            ps.setString(2, product.getImageUrl());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setDate(5, Date.valueOf(product.getLastImportDate()));
            
            ps.setInt(6, product.getId());
            rs = ps.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } finally {
            closeConn(conn);
        }
        return rs >= 1;
    }
    
    /*
    *   Tools
    * */
    public boolean checkExistProduct(String name) {
        conn  = DBConfig.getConn();
    
        String CHECK_EXISTING_PRODUCT_SQL = "SELECT id FROM product WHERE name = ?";
        ResultSet rs = null;
        try (
            PreparedStatement CHECK_PS = conn.prepareStatement(CHECK_EXISTING_PRODUCT_SQL)){
            // check exist user
            CHECK_PS.setString(1, name);
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
    
    public boolean checkExistProduct_withId(int id) {
        conn  = DBConfig.getConn();
    
        String CHECK_EXISTING_PRODUCT_SQL = "SELECT id FROM product WHERE id = ?";
        ResultSet rs = null;
        try (
            PreparedStatement CHECK_PS = conn.prepareStatement(CHECK_EXISTING_PRODUCT_SQL)){
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
