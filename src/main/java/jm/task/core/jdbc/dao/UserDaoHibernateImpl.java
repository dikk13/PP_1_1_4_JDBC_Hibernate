package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

//NOT NULL AUTO_INCREMENT PRIMARY KEY
public class UserDaoHibernateImpl implements UserDao {
    Session session = null;
    Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            String SQL = "CREATE TABLE IF NOT EXISTS users(" +
                    "id LONG, " +
                    "name varchar(45), " +
                    "lastName varchar(45), " +
                    "age INT)";
            Query query = session.createSQLQuery(SQL);
            query.executeUpdate();
            transaction.commit();
//            session.close();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
//        finally {
//            if (session != null) {
//                session.close();
//            }
//        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            String SQL = "DROP TABLE IF EXISTS users";
            Query query = session.createSQLQuery(SQL);
            query.executeUpdate();
            transaction.commit();
//            session.close();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
//        finally {
//            if (session != null) {
//                session.close();
//            }
//        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.save(new User(name,lastName,age));
            transaction.commit();
//            session.close();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
//        finally {
//            if (session != null) {
//                session.close();
//            }
//        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
//            session.close();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
//        finally {
//            if (session != null) {
//                session.close();
//            }
//        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = Util.getSessionFactory().openSession())
        {
            users = session.createQuery("From User", User.class).list();
//            session.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
//        finally {
//            if (session != null) {
//                session.close();
//            }
//        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.createQuery("DELETE From User").executeUpdate();
            transaction.commit();
//            session.close();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
//        finally {
//            if (session != null) {
//                session.close();
//            }
//        }
    }
}
