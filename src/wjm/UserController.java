package wjm;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    private final Session session;

    public UserController(Session session) {
        this.session = session;
    }

    public List<User> addUsers(List<User> users) {
        session.beginTransaction();
        List<User> myUsers = new ArrayList<>();
        for (User user : users) {
            session.save(user);
            myUsers.add(user);
        }
        session.getTransaction().commit();
        return myUsers;
    }

    public List getUsers() {
        session.beginTransaction();
        List users = session.createQuery( "from wjm.User" ).list();
        session.getTransaction().commit();
        return users;
    }


    public int delUsers(List<User> users) {
        int deletedEntities = 0;
        int myDeleted = 0;
        session.beginTransaction();
        for (User user : users) {
            deletedEntities = session.createQuery(
                    "delete wjm.User " +
                            "where name = :name")
                    .setParameter("name", user.getName())
                    .executeUpdate();
            myDeleted += deletedEntities;
        }
        session.getTransaction().commit();
        return myDeleted;
    }

}
