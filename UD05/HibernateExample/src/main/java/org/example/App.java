package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SessionFactory sessionFactory =
                new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        if (session != null) {
            System.out.println("Session successfully opened!");
        } else {
            System.out.println("Error opening session!");
        }

        Query<EmployeeEntity> myQuery =
                session.createQuery("from org.example.EmployeeEntity");
        List<EmployeeEntity> employees = myQuery.list();
        for ( Object employeeObject : employees ) {
            EmployeeEntity employee = (EmployeeEntity) employeeObject;
            System.out.printf("Number : %d Name: %s", employee.getEmpno(),
                    employee.getEname() + "\n");
        }

        Query<DeptEntity> myQuery2 =
                session.createQuery("from org.example.DeptEntity");
        List<DeptEntity> departments = myQuery2.list();
        for ( Object department : departments ) {
            DeptEntity dept = (DeptEntity) department;
            System.out.printf("Number : %d Name: %s", dept.getDeptno(),
                    dept.getDname() + " " + dept.getLoc() + "\n");
        }

    }
    public static Session openSession() throws Exception {
        SessionFactory sessionFactory =
                new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        if (session == null) {
            throw new Exception("Error opening session!");
        }
        return session;
    }
    public static void updateEmployee( int employeeNumber ) {
        try ( Session session = openSession() ) {
            Query<EmployeeEntity> myQuery =
                    session.createQuery("from com.jrgs2122.Unit5. " +
                            "EmployeeEntity where empno=’"
                            + String.valueOf(employeeNumber)+ "’ ");
            List<EmployeeEntity> employees = myQuery.list();
            Transaction transaction = session.beginTransaction();
            EmployeeEntity employee = (EmployeeEntity) employees.get(0);
            //employee.setDepartment(30);
            session.update(employee);
            transaction.commit(); // End of transaction
        }
        catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }
}
