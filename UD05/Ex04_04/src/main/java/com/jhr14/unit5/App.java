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
        mainMenu();
    }

    public static void mainMenu()
    {
        databaseManager.retrieveDeptByID(1111);
        String option;
        do {
            System.out.println("-- Department manager --");
            System.out.println("1. Create data");
            System.out.println("2. Read data");
            System.out.println("3. Modify data");
            System.out.println("4. Delete Data");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            switch (option = scanner.nextLine())
            {
                case "1":
                    createMenu();
                    break;
                case "2":
                    readDatabase();
                    break;
                case "3":
                    break;
                case"4":
                    break;
                default:
                    break;
            }
            System.out.println();
        }while (!option.equals("0"));

    }

    public static void createMenu()
    {
        String option;
        do {
            System.out.println("1. Create dept");
            System.out.println("2. Create employee");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            switch (option = scanner.nextLine())
            {
                case "1":
                    try
                    {
                        databaseManager.saveDept(createDept());
                    }
                    catch (org.hibernate.NonUniqueObjectException nonUniqueObjectException)
                    {
                        System.out.println("Exception: " + nonUniqueObjectException.getMessage());
                    }
                    catch (NumberFormatException numberFormatException)
                    {
                        System.out.println("Exception: " + numberFormatException.getMessage() + ", it must be a number");
                    }
                    break;
                case "2":
                    break;
                default:
                    break;
            }
            System.out.println();
        }while (!option.equals("0"));
    }

    public static DeptEntity createDept()
    {
        DeptEntity dept = new DeptEntity();
        System.out.print("Introduce a dept number: ");
        dept.setDeptno(Integer.parseInt(scanner.nextLine()));
        System.out.print("Introduce a dept name: ");
        dept.setDname(scanner.nextLine());
        System.out.print("Introduce a dept location: ");
        dept.setLoc(scanner.nextLine());
        return dept;
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
