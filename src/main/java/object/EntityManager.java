package object;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by yahya on 2017/02/24.
 */
public class EntityManager {
    public static void addUser(Person person) {
        SessionFactory sessionFactory;

        sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(person);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("hibernate exception!");
        } finally {
            session.close();
        }
    }

    public static ArrayList<Person> readUser(int id) {

        ArrayList<Person> per = new ArrayList<Person>();
        SessionFactory sessionFactory;
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Person p1 = (Person) session.get(Person.class, id);
            if (p1 != null) {
                per.add(p1);

            }else {
                List persons = session.createQuery("FROM Person").list();
                for (Iterator iterator = persons.iterator(); iterator.hasNext(); ) {
                    Person p = (Person) iterator.next();
                    per.add(p);
                }
            }
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();


        }
        return per;
    }

    public static void deleteUser(int id) {
        Person p = new Person();
        p.setID(id);
        SessionFactory sessionFactory;
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(p);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void updateUser(Person person) {
        SessionFactory sessionFactory;
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(person);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("hibernate exception!");
        } finally {
            session.close();

        }
    }
}
