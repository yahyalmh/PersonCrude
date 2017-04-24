package servlets;

import object.EntityManager;
import object.Person;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by asus x555L on 2017/02/25.
 */
@WebServlet(name = "CrudeServlet",urlPatterns = {"/CrudeServlet"})
public class CrudeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        Person p = new Person();
        p.setID(1);
        p.setName("yahya");
        p.setLname("lor");
        p.setJob("sdsd");
        EntityManager.addUser(p);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!loginCheck(request,response)){return;}

            String id = request.getParameter("id");
            if (id == null) {
                showUser("all", response);
            } else {
                showUser(id, response);
            }


    }

    private void showUser(String id, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if ("all".equals(id)) {
            ArrayList<Person> person = EntityManager.readUser(-1);
            if (person.size() == 0) {
                response.sendError(404, "there is no user to show");

            } else {
                for (Iterator iterator = person.iterator(); iterator.hasNext(); ) {
                    Person p = (Person) iterator.next();
                    String s = mapper.writeValueAsString(p);
                    response.getWriter().print(s + "\n");

                }
                return;
            }
        } else {
            ArrayList<Person> person = EntityManager.readUser(Integer.parseInt(id));
            if (Integer.parseInt(id) < 0) {
                response.sendError(406, "id is negative!");
                return;
            }
            for (Iterator iterator = person.iterator(); iterator.hasNext(); ) {
                Person p = (Person) iterator.next();
                if (p.getID() == Integer.parseInt(id)) {
                    String s = mapper.writeValueAsString(p);
                    response.getWriter().print(s);
                    return;
                }

            }
            response.sendError(404, "this user not exist");
            return;
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!loginCheck(request,response)){return;}
        Person p1;
        ObjectMapper mapper = new ObjectMapper();
        StringBuffer jb = new StringBuffer();
        String line = null;
        int j = 1;
        boolean k = true;
        BufferedReader reader = request.getReader();
        try {
            while ((line = reader.readLine()) != null) {
                jb.append(line);
                String s = jb.toString();
                p1 = mapper.readValue(s, Person.class);
                List<Person> person = EntityManager.readUser(p1.getID());
                for (Person per : person) {
                    if (per.getID() == p1.getID()) {
                        response.sendError(400, "the " + j + "'th user is duplicated!");
                        j++;
                        k = false;
                        break;
                    }
                }

                if (k) {
                    EntityManager.addUser(p1);
                    response.getWriter().print(
                            " The " + j + "'th user added:)\n");
                    j++;
                }

                jb.delete(0, jb.length());
                k = true;
            }
        } catch (Exception e) {
            response.getWriter().print("do post exception");
            e.printStackTrace();
        }
    }
    protected void doPut(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        if(!loginCheck(request,response)){return;}
        Person p;
        StringBuffer jb = new StringBuffer();
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader reader = request.getReader();
        String line;

        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }
        String s = jb.toString();
        p = mapper.readValue(s, Person.class);
        boolean temp = true;

        List<Person> person = EntityManager.readUser(p.getID());
        if (person.size()==1) {
            EntityManager.updateUser(p);
            response.getWriter().print("the user updated:)");
            temp = false;
        }

        if (temp) {
            response.getWriter()
                    .print("there is no user with " + p.getID() + " ID:(");
        }

    }

    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        if(!loginCheck(request,response)){return;}
        String id = request.getParameter("id");
        ArrayList<Person> person = EntityManager.readUser(-1);

        if (id == null) {

            for (Person person1:person){
                EntityManager.deleteUser(person1.getID());
            }
            response.getWriter().print("all users deleted:)");
            return;
        } else {
            for (Person person2:person){
                if (person2.getID()==Integer.parseInt(id)){
                    EntityManager.deleteUser(person2.getID());
                    response.getWriter().print("user deleted:)");
                    return;
                }
            }
            response.getWriter().print("user not exist:(");
        }

    }
    private boolean loginCheck(HttpServletRequest request, HttpServletResponse response)throws IOException{
        HttpSession session=request.getSession(false);
        if (session==null||session.getAttribute("signin").equals(false)){
            response.sendRedirect("/index.html");
            return false;
        }
        return true;
    }
   }
