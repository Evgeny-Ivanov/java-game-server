package databaseService;

import databaseService.daoHibernate.UserProfileDAO;
import databaseService.daoHibernate.UserSessionDAO;
import databaseService.dataSets.UserProfile;
import databaseService.dataSets.UserSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by stalker on 02.02.16.
 */
public class DBServiceHibernate implements AccountService {

    SessionFactory sessionFactory;



    public DBServiceHibernate(){
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserProfile.class);
        configuration.addAnnotatedClass(UserSession.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/hibernate");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "1");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans","true");

        sessionFactory = createSessionFactory(configuration);
        UserSessionDAO dao = new UserSessionDAO(sessionFactory.openSession());
        dao.removeAllSessions();
    }

    private static SessionFactory createSessionFactory(Configuration configuration){
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void shutdown() {
        sessionFactory.close();//полностью разрывает соединение с базой закрывая все выданные сессии
    }

    public UserProfile getUser(String login){
        Session session = sessionFactory.openSession();
        UserProfileDAO dao = new UserProfileDAO(session);
        UserProfile profile = dao.getUser(login);
        session.close();
        return profile;
    }

    public boolean addUser(String login, UserProfile profile){
        Session session = sessionFactory.openSession();
        UserProfileDAO dao = new UserProfileDAO(session);
        boolean result = dao.addUser(profile);
        session.close();
        return result;
    }

    public boolean addSession(String sessionId, UserProfile profile){
        Session session = sessionFactory.openSession();
        UserSessionDAO dao = new UserSessionDAO(session);
        UserSession userSession = new UserSession(sessionId,profile.getLogin());
        boolean result = dao.addSession(userSession);
        session.close();
        return result;
    }


    public UserProfile getSession(String sessionId){
        Session session = sessionFactory.openSession();
        UserSessionDAO dao = new UserSessionDAO(session);
        UserProfile result = dao.getSession(sessionId);
        session.close();
        return result;
    }

    public boolean removeSession(String sessionId){
        Session session = sessionFactory.openSession();
        UserSessionDAO dao = new UserSessionDAO(session);
        boolean result = dao.removeSession(sessionId);
        session.close();
        return result;
    }

    public int getCountUsers(){
        Session session = sessionFactory.openSession();
        UserProfileDAO dao = new UserProfileDAO(session);
        int result = dao.getCountUsers();
        session.close();
        return result;
    }

    public int getCountSessions(){
        Session session = sessionFactory.openSession();
        UserSessionDAO dao = new UserSessionDAO(session);
        int result = dao.getCountSessions();
        session.close();
        return result;
    }

}
