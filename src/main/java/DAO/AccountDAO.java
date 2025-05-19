package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
public class AccountDAO {
    


    public Account createUser(String user, String pass){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "INSERT INTO Account (username,password) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2, pass);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                int id = (int) rs.getLong(1);
                return new Account(id, user, pass);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getUser(String user){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql="SELECT * FROM account WHERE username=?";
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1,user);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                return new Account(rs.getInt("account_id"), user, rs.getString("password"));
            }
        }
        catch(SQLException e){

        }
        return null;
    }

    public Account getUserFromID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql="SELECT * FROM account WHERE account_id=?";
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                return new Account(id, rs.getString("username"), rs.getString("password"));
            }
        }
        catch(SQLException e){

        }
        return null;
    }
    public Account validateUser(String user,String pass){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql="SELECT * FROM account WHERE username=? AND password=?";
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1,user);
            ps.setString(2,pass);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                return new Account(rs.getInt("account_id"), user, pass);
            }
        }
        catch(SQLException e){

        }
        return null;
    }
}
