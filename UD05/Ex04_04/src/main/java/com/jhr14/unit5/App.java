package com.jhr14.unit5;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Scanner;

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
                    modifyMenu();
                    break;
                case"4":
                    deleteMenu();
                    break;
                default:
                    break;
            }
            System.out.println();
        }while (!option.equals("0"));

    }

    public static void createMenu()
    {
        String option = "";
        do {
            System.out.println("-- Create menu --");
            System.out.println("1. Create dept");
            System.out.println("2. Create employee");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            try
            {
                switch (option = scanner.nextLine())
                {
                    case "1":
                        createDept();
                        break;
                    case "2":
                        createEmployee();
                        break;
                    default:
                        break;
                }
            }
            catch (org.hibernate.HibernateException hibernateException)
            {
                System.out.println("Exception: " + hibernateException.getMessage());
            }
            catch (PersistenceException persistenceException)
            {
                System.out.println("Exception: " + persistenceException.getMessage());
            }
            catch (NumberFormatException numberFormatException)
            {
                System.out.println("Exception: " + numberFormatException.getMessage() + ", must be a number");
            }
            System.out.println();
        }while (!option.equals("0"));
    }

    public static void modifyMenu()
    {
        System.out.println("-- Modify menu --");
        String option = "";

        do {
            System.out.println("1. Modify dept");
            System.out.println("2. Modify employee");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            try
            {
                switch (option = scanner.nextLine())
                {
                    case "1":
                        modifyDept();
                        break;
                    case "2":
                        modifyEmployee();
                        break;
                    default:
                        break;
                }

            }
            catch (HibernateException hibernateException)
            {
                System.out.println("Exception: " + hibernateException.getMessage());
            }
            catch (PersistenceException persistenceException)
            {
                System.out.println("Exception: " + persistenceException.getMessage());
            }
            catch (NumberFormatException numberFormatException)
            {
                System.out.println("Exception: " + numberFormatException.getMessage() + ", must be a number");
            }
            System.out.println();
        }while (!option.equals("0"));
    }

    public static void deleteMenu()
    {
        System.out.println("-- Delete menu --");
        String option = "";
        do {
            System.out.println("1. Delete dept");
            System.out.println("2. Delete employee");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            try
            {
                switch (option = scanner.nextLine())
                {
                    case "1":
                        deleteDept();
                        break;
                    case "2":
                        deleteEmployee();
                        break;
                    default:
                        break;
                }
            }
            catch (HibernateException hibernateException)
            {
                System.out.println("Exception: " + hibernateException.getMessage());
            }
            catch (PersistenceException persistenceException)
            {
                System.out.println("Exception: " + persistenceException.getMessage());
            }
            catch (NumberFormatException numberFormatException)
            {
                System.out.println("Exception: " + numberFormatException.getMessage() + ", must be a number");
            }
            System.out.println();
        }while (!option.equals("0"));
    }

    public static void deleteDept()
    {
        System.out.print("Introduce the dept id you want to delete: ");
        DeptEntity dept = databaseManager.retrieveDeptByID(Integer.parseInt(scanner.nextLine()));
        System.out.println(dept.toString());
        String option = "";
        do {
            System.out.println("Do you truly want to delete it? (Y/N)");
            switch (option = scanner.nextLine().toUpperCase()) {
                case "Y":
                    databaseManager.deleteDept(dept);
                    System.out.println("Department deleted successfully");
                    break;
                case "N":
                    break;
                default:
                    break;
            }
            System.out.println();
        }while (!option.equals("N") && !option.equals("Y"));
    }

    public static void deleteEmployee()
    {
        System.out.print("Introduce the employee id you want to delete: ");
        EmployeeEntity employee = databaseManager.retrieveEmployeeByID(Integer.parseInt(scanner.nextLine()));
        System.out.println(employee.toString());
        String option = "";
        do {
            System.out.println("Do you truly want to delete it? (Y/N)");
            switch (option = scanner.nextLine().toUpperCase()) {
                case "Y":
                    databaseManager.deleteEmployee(employee);
                    System.out.println("Employee deleted sucessfully");
                    break;
                case "N":
                    break;
                default:
                    break;
            }
            System.out.println();
        }while (!option.equals("N") && !option.equals("Y"));
    }

    public static void modifyDept()
    {
        System.out.print("Introduce a dept id you want to modify: ");
        DeptEntity dept = databaseManager.retrieveDeptByID(Integer.parseInt(scanner.nextLine()));
        System.out.print("Previous dept name: " + dept.getDname() + ", introduce the new dept name: ");
        dept.setDname(scanner.nextLine());
        System.out.print("Previous dept location: " + dept.getLoc() + ", introduce the new dept location: ");
        dept.setLoc(scanner.nextLine());
        databaseManager.updateDept(dept);
    }

    public static void modifyEmployee()
    {
        System.out.print("Introduce the employee id you want to modify: ");
        EmployeeEntity employee = databaseManager.retrieveEmployeeByID(Integer.parseInt(scanner.nextLine()));
        System.out.print("Previous employee name: " + employee.getEname() + ", introduce the new employee name: ");
        employee.setEname(scanner.nextLine());
        System.out.print("Previous employee's job: " + employee.getJob() + ", introduce the new employee's job: ");
        employee.setJob(scanner.nextLine());
        System.out.print("Previous employee's department: " + employee.getDepartment() + ", introduce the id of the new dept the employee will be working in: ");
        employee.setDepartment(databaseManager.retrieveDeptByID(Integer.parseInt(scanner.nextLine())));
        databaseManager.updateEmployee(employee);
    }

    public static void createDept()
    {
        DeptEntity dept = new DeptEntity();
        System.out.print("Introduce a dept id: ");
        dept.setDeptno(Integer.parseInt(scanner.nextLine()));
        System.out.print("Introduce a dept name: ");
        dept.setDname(scanner.nextLine());
        System.out.print("Introduce a dept location: ");
        dept.setLoc(scanner.nextLine());
        databaseManager.saveDept(dept);
    }

    public static void createEmployee()
    {
        EmployeeEntity employee = new EmployeeEntity();
        System.out.print("Introduce a employee id: ");
        employee.setEmpno(Integer.parseInt(scanner.nextLine()));
        System.out.print("Introduce the employee name: ");
        employee.setEname(scanner.nextLine());
        System.out.print("Introduce employee's job: ");
        employee.setJob(scanner.nextLine());
        System.out.print("Introduce the id of the dept the employee will be working in: ");
        employee.setDepartment(databaseManager.retrieveDeptByID(Integer.parseInt(scanner.nextLine())));
        databaseManager.saveEmployee(employee);
    }

    public static void readDatabase()
    {
        for (DeptEntity dept: databaseManager.retrieveDeptList()) {
            System.out.println(dept.toString());
            if(!dept.getEmployeeList().isEmpty())
            {
                for (EmployeeEntity employee: dept.getEmployeeList()) {
                    System.out.println("\t" + employee.toString());
                }
            }

        }
    }
}
