package Service;
import Model.Account;
import DAO.AccountDAO;
public class AccountService {
    AccountDAO dao;
    public AccountService(){
        dao=new AccountDAO();
    }
    public AccountService(AccountDAO d){
        dao=d;
    }
    
/*
 - The registration will be successful if and only if the username is not blank,
 the password is at least 4 characters long,
 and an Account with that username does not already exist.
 
 If all these conditions are met, the response body should contain a JSON of the Account,
 including its account_id.
 
 The response status should be 200 OK, which is the default. The new account should be persisted to the database.
 */
    public Account createUser(Account account){
        String user=account.getUsername();
        String pass=account.getPassword();
        if (pass.length()>=4 && user!="" && dao.getUser(user)==null){
            System.out.println("ATTEMPT CREATE");
            return dao.createUser(user, pass);
        }
        return null;
    }
    public Account validateUser(Account account){
        String user=account.getUsername();
        String pass=account.getPassword();
        return dao.validateUser(user,pass);

    }

    public Account getUserFromID(int id){
        return dao.getUserFromID(id);
    }
}
