package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.ArrayList;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    /*
     * endpoints
     * localhost:8080/register
     */
    ObjectMapper om = new ObjectMapper();
    AccountService accountService=new AccountService();
    MessageService messageService=new MessageService();
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registrationHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages",this::createMessageHandler);
        app.delete("/messages/{message_id}",this::deleteMessageHandler);
        app.get("/messages",this::getAllMessagesHandler);
        app.get("/messages/{message_id}",this::getSpecificMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);//updatemsg
        app.get("/accounts/{account_id}/messages",this::getAllMessagesFromAccountHandler);//retrieve all messages written by a particular user.
        return app;
    }

    private void registrationHandler(Context context) throws JsonProcessingException {
        String json=context.body();
        Account acc=accountService.createUser(om.readValue(json,Account.class));
        if (acc!=null){
            context.status(200);
            context.json(acc);
        }
        else{
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException{
        String json=context.body();
        Account acc=accountService.validateUser(om.readValue(json,Account.class));
        if (acc!=null){
            context.status(200);
            context.json(acc);
        }
        else{
            context.status(401);
        }
    }
    private void createMessageHandler(Context context) throws JsonProcessingException{
        String json=context.body();
        Message msg=messageService.createMessage(om.readValue(json,Message.class), accountService);
        if (msg!=null){
            context.status(200);
            context.json(msg);
        }
        else{
            context.status(400);
        }
    }
    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        String msgIDStr=context.pathParam("message_id");
        int msgID=Integer.parseInt(msgIDStr);
        Message msg=messageService.deleteMessage(msgID);
        context.status(200);
        if (msg!=null){
            context.json(msg);
        }
    }
    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        List<Message> messages=messageService.getAllMessages();
        context.status(200);
        context.json(messages);
    }
    private void getAllMessagesFromAccountHandler(Context context) throws JsonProcessingException{
        String accIDStr=context.pathParam("account_id");
        int accID=Integer.parseInt(accIDStr);
        context.status(200);
        context.json(new ArrayList<Message>());
        if (accountService.getUserFromID(accID)!=null){
            List<Message> messages=messageService.getAllMessagesFromAccount(accID);
            context.json(messages);   
        }
    }
    private void getSpecificMessageHandler(Context context) throws JsonProcessingException{
        String msgIDStr=context.pathParam("message_id");
        int msgID=Integer.parseInt(msgIDStr);
        Message msg=messageService.getMessageFromID(msgID);
        context.status(200);
        if (msg==null){
            context.json("");
        }
        else{
            context.json(msg);
        }
    }
    /*
     * - The update of a message should be successful if and only if the message id already exists
     * and the new message_text is not blank and is not over 255 characters.
     * 
     * If the update is successful, the response body should contain the full updated message (including message_id, posted_by, message_text, and time_posted_epoch), and the response status should be 200, which is the default. The message existing on the database should have the updated message_text.
     */
    private void updateMessageHandler(Context context) throws JsonProcessingException{
        String msgIDStr=context.pathParam("message_id");
        int msgID=Integer.parseInt(msgIDStr);
        String json=context.body();
        String newMessageText=om.readValue(json, Message.class).getMessage_text();
        if (newMessageText.length()<=255 && !newMessageText.isEmpty() && messageService.getMessageFromID(msgID)!=null){
            Message newMsg=messageService.updateMessage(msgID, newMessageText);
            if (newMsg==null){
                context.status(400);
                context.json("");
            }
            else{
                context.status(200);
                context.json(newMsg);
            }
        }
        else{
            context.status(400);
            context.json("");
        }
      
    }
}