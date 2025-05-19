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
