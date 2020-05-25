package test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.*;
import wjm.User;
import wjm.UserController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserControllerTest {

    private static SessionFactory ourSessionFactory;
    private Session session;
    private UserController userController;
    private List<User> users;

    @BeforeClass
    public static void initAll() {
        try {
            ourSessionFactory = new Configuration().
                    configure("hibernate.cfg.xml").
                    buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
        Session session = ourSessionFactory.openSession();
        session.beginTransaction();
        int deletedEntities = session.createQuery(
                "delete wjm.User ")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @AfterClass
    public static void tearDownAll() {
        ourSessionFactory.close();
    }

    @Before
    public void setUp() throws Exception {
        session = ourSessionFactory.openSession();
        userController = new UserController(session);
        users = new ArrayList<>();
        User user1 = new User("Henry", "henry@harrysplace.org");
        User user2 = new User("Timothy", "timothy@harrysplace.org");
        users.add(user1);
        users.add(user2);
    }

    @After
    public void tearDown() throws Exception {
        session.beginTransaction();
        int deletedEntities = session.createQuery(
                "delete wjm.User ")
                .executeUpdate();
        session.getTransaction().commit();
        users.clear();
        session.close();
    }

    @Test
    public void addUsers() {
        List<User> usersAdded = userController.addUsers(users);
        List<User> myUsers = userController.getUsers();
        assertEquals(usersAdded.size(), myUsers.size());
    }

    @Test
    public void getUsers() {
        List<User> usersAdded = userController.addUsers(users);
        List myUsers = userController.getUsers();
        assertEquals(myUsers.size(), usersAdded.size());
    }

    @Test
    public void delUsers() {
        List<User> usersAdded = userController.addUsers(users);
        int deletedUsers = userController.delUsers(users);
        assertEquals(deletedUsers, usersAdded.size());
    }
}