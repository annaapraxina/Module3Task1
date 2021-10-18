package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<User> getUsersAndCars(String model, int series) {
      Session session = sessionFactory.openSession();
      List<User> users = null;
      try {
         session.beginTransaction();
         users = session.createQuery("FROM User u WHERE car.model =:model and car.series =:series", User.class)
                 .setParameter("model", model)
                 .setParameter("series", series)
                 .getResultList();
      } catch(Exception e){
         session.getTransaction().rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return users;
   }


}
