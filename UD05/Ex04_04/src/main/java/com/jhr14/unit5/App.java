package com.jhr14.unit5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    static Scanner scanner;
    static DatabaseManager databaseManager;
    public static void main( String[] args )
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        databaseManager = new DatabaseManager(session);
        scanner = new Scanner(System.in);

        List<DeptEntity> deptEntities = databaseManager.retrieveDeptList();
        List<EmployeeEntity> employeeEntities = databaseManager.retrieveEmployeeList();
/*        databaseManager.saveEmployee(new EmployeeEntity(123, "Navarrete",
                "Futbol", deptEntities.get(0)));*/
/*        employeeEntities.get(employeeEntities.size() -1).setJob("Pobre");
        databaseManager.updateEmployee(employeeEntities.get(employeeEntities.size() -1));*/
        readDatabase();
    }

    public static void readDatabase()
    {
        for (DeptEntity dept: databaseManager.retrieveDeptList()) {
            System.out.println(dept.toString());
            for (EmployeeEntity employee: dept.getEmployeeList()) {
                System.out.println("\t" + employee.toString());
            }
        }
    }
}
