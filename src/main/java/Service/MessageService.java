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
