package wjm;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Simple {

    private static final SessionFactory ourSessionFactory;

    static {
        try {
            ourSessionFactory = new Configuration().
                    configure("hibernate.cfg.xml").
                    buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void closeSessionFactory() {
        ourSessionFactory.close();
    }

    public static void main(final String[] args) throws Exception {

        Session session = ourSessionFactory.openSession();
        UserController userController = new UserController(session);
        List<User> users = new ArrayList<>();
        User user1 = new User("Henry", "henry@harrysplace.org");
        User user2 = new User("Timothy", "timothy@harrysplace.org");
        users.add(user1);
        users.add(user2);
        userController.addUsers(users);
        List myUsers = userController.getUsers();
        for ( Object user : myUsers) {
            User myUser = (User)user;
            System.out.println( "user (" + myUser.getName() + ") : " + myUser.getEmail() );
        }
        userController.delUsers(users);
        session.close();
        closeSessionFactory();
    }

}
