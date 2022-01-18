package com.jhr14;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.logging.Level;

/**
 * Hello world!
 *
 */
//usar tipo set y no tipo list
public class App 
{
    public static void main( String[] args )
    {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger =
                org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate")
                .setLevel(Level.SEVERE);

        SessionFactory sessionFactory =
                new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        if (session != null) {
            System.out.println("Session successfully opened!");
        } else {
            System.out.println("Error opening session!");
        }

        Query<EmployeeEntity> myQuery =
                session.createQuery("from com.jhr14.EmployeeEntity");
        List<EmployeeEntity> employees = myQuery.list();
        for ( Object employeeObject : employees ) {
            EmployeeEntity employee = (EmployeeEntity) employeeObject;
            System.out.printf("Number : %d Name: %s", employee.getEmpno(),
                    employee.getEname() + "\n");
        }
    }
}
