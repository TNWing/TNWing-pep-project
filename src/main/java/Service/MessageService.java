package Service;
import java.util.List;
import DAO.MessageDAO;
import Model.Message;
public class MessageService {
    MessageDAO dao;

    public MessageService(){
        dao=new MessageDAO();
    }
    public MessageService(MessageDAO d){
        dao=d;
    }


/*
 * 
- The creation of the message will be successful if and
only if the message_text is not blank, is not over 255 characters,
and posted_by refers to a real, existing user.

If successful, the response body should contain a JSON of the message, including its message_id.
The response status should be 200, which is the default. The new message should be persisted to the database.

- If the creation of the message is not successful, the response status should be 400. (Client error)
 */
    public Message createMessage(Message m,AccountService accountService){

        if (!m.getMessage_text().isEmpty() && m.getMessage_text().length()<=255 &&  accountService.getUserFromID(m.getPosted_by())!=null){//TODO: posted-by
            return dao.createMessage(m);
        }
        return null;
    }
    public Message deleteMessage(int id){
        return dao.deleteMessage(id);
    }
    public Message getMessageFromID(int id){
        return dao.getMessageByID(id);
    }

    public List<Message> getAllMessages(){
        return dao.getAllMessages();
    }
    public List<Message> getAllMessagesFromAccount(int id){
        return dao.getAllMessagesFromAccount(id);
    }
/* */
    public Message updateMessage(int msg_id,String text){
        return dao.updateMessage(msg_id, text);
    }
}
