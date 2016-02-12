package databaseService.daoHibernate;

import databaseService.dataSets.UserProfile;
import databaseService.dataSets.UserSession;
import org.hibernate.*;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import java.util.List;

/**
 * Created by stalker on 02.02.16.
 */
public class UserSessionDAO {

    Session session;

    public UserSessionDAO(Session session){
        this.session = session;
    }

    public boolean addSession(UserSession userSession) {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(userSession);
            transaction.commit();
            return true;
        }catch (HibernateException e){
            e.printStackTrace();
            return false;
        }
    }

    public UserProfile getSession(String sessionId) {
        try {
            UserSession userSession = session.load(UserSession.class, sessionId);
            return session.load(UserProfile.class, userSession.getLogin());
        }catch (HibernateException e){
            System.out.println("Такого пользователя нет");
            return null;
        }
    }

    public boolean removeSession(String sessionId) {
        try {
            UserSession userSession = new UserSession();
            userSession.setSession(sessionId);

            Transaction transaction = session.beginTransaction();
            session.delete(userSession);
            transaction.commit();

            return true;
        } catch (HibernateException e){
            e.printStackTrace();
            return false;
        }
    }

    public int getCountSessions() {
        Criteria criteria = session.createCriteria(UserSession.class);
        Object count = criteria.setProjection(Projections.rowCount()).uniqueResult();
        return Integer.parseInt(count.toString());
    }

    public void removeAllSessions() {
        Transaction transaction = session.beginTransaction();
        String hql = String.format("DELETE FROM %s", UserSession.class.getSimpleName());
        Query query = session.createQuery(hql);
        //SQLQuery query1 = session.createSQLQuery("DELETE FROM users");
        query.executeUpdate();
        transaction.commit();
    }

}
