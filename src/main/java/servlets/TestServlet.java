package servlets;

import object.Child;
import object.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus x555L on 2017/03/03.
 */
@WebServlet(name = "TestServlet",urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory sessionFactory;
        List<Child> l=new ArrayList<Child>();
        Person p=new Person();
        p.setID(12345);
        p.setName("lyla");
        Child ch=new Child();
        ch.setId(15);
        ch.setName("yoyo");
        ch.setPerson(p);
        l.add(ch);
        p.setChilds(l);
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(p);
            session.save(ch);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("hibernate exception!");
        } finally {
            session.close();
            response.getWriter().print("kshdkjshadkjhsakjdh");
        }
    }
}
