package databaseService.daoHibernate;

import databaseService.dataSets.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

/**
 * Created by stalker on 02.02.16.
 */
public class UserProfileDAO {
    Session session;

    public UserProfileDAO(Session session){
        this.session = session;
    }

    public boolean addUser(UserProfile profile) {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(profile);
            transaction.commit();
            return true;
        }catch(HibernateException e){
            e.printStackTrace();
            return false;
        }
    }

    public UserProfile getUser(String login) {
        try {
            return session.get(UserProfile.class, login);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int getCountUsers() {
        Criteria criteria = session.createCriteria(UserProfile.class);
        Object count = criteria.setProjection(Projections.rowCount()).uniqueResult();
        return Integer.parseInt(count.toString());
    }

}
