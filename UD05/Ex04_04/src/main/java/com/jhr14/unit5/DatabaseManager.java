package com.jhr14.unit5;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DatabaseManager {
    Session session;

    public DatabaseManager(Session session) {
        this.session = session;
    }

    public List<DeptEntity> retrieveDeptList()
    {
        Query<DeptEntity> myQuery =
                session.createQuery("from com.jhr14.unit5.DeptEntity");
        return myQuery.list();
    }

    public List<EmployeeEntity> retrieveEmployeeList()
    {
        Query<EmployeeEntity> myQuery =
                session.createQuery("from com.jhr14.unit5.EmployeeEntity");
        return myQuery.list();
    }
    public void saveDept(DeptEntity deptEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.save( deptEntity );
        transaction.commit();
    }
    public void saveEmployee(EmployeeEntity employeeEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.save( employeeEntity );
        transaction.commit();
    }
    public void updateDept(DeptEntity deptEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.update(deptEntity);
        transaction.commit();
    }

    public void updateEmployee(EmployeeEntity employeeEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.update(employeeEntity);
        transaction.commit();
    }

    public void deleteDept(DeptEntity deptEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.delete(deptEntity);
        transaction.commit();
        System.out.println("The employee has been deleted.");
    }

    public void deleteEmployee(EmployeeEntity employeeEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.delete(employeeEntity);
        transaction.commit();
    }

}
