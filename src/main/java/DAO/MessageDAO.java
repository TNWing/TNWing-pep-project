package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Message;
import Util.ConnectionUtil;
public class MessageDAO {
    public Message createMessage(Message messageToBeCreated){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,messageToBeCreated.getPosted_by());
            preparedStatement.setString(2, messageToBeCreated.getMessage_text());
            preparedStatement.setLong(3,messageToBeCreated.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                int id = (int) rs.getLong(1);
                return new Message(id,messageToBeCreated.getPosted_by(),messageToBeCreated.getMessage_text(),messageToBeCreated.getTime_posted_epoch());
            }
        }
        catch(Exception e){

        }
        return null;
    }

    public Message deleteMessage(int msg_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id=?";//TODO: refactor to use getmsgbyid
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,msg_id);
            ResultSet rs=preparedStatement.executeQuery();
            if (rs.next()){
                sql="DELETE FROM Message WHERE message_id=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,msg_id);
                preparedStatement.execute();
                return new Message(msg_id,rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            }
        }
        catch(Exception e){

        }
        return null;
    }

    public Message getMessageByID(int msg_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,msg_id);
            ResultSet rs=preparedStatement.executeQuery();
            if (rs.next()){
                return new Message(msg_id,rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            }
        }
        catch(Exception e){

        }
        return null;
    }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        ArrayList<Message> arrList=new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                arrList.add(new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch")));
            }
        }
        catch(Exception e){

        }
        return arrList;
    }

    public List<Message> getAllMessagesFromAccount(int accID){
        Connection connection = ConnectionUtil.getConnection();
        ArrayList<Message> arrList=new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message WHERE posted_by=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accID);
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                arrList.add(new Message(rs.getInt("message_id"),accID,rs.getString("message_text"),rs.getLong("time_posted_epoch")));
            }
        }
        catch(Exception e){

        }
        return arrList;
    }

    public Message updateMessage(int id,String text){
        Connection connection = ConnectionUtil.getConnection();
        try{

            String sql="UPDATE Message SET message_text=? WHERE message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            sql="SELECT * FROM Message WHERE message_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet rs=preparedStatement.executeQuery();
            if (rs.next()){
                return new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            }
        }
        catch(Exception e){

        }
        return null;
    }
}
