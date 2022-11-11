package com.vishal;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.*;

@org.springframework.stereotype.Service
public class EmployeeServiceImpl implements EmployeeService {
    public void add(Employee employee){
        Transaction transaction=null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            session.save(employee);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Employee getEmployee(int id) {
        Transaction transaction=null;
        Employee emp=null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            emp=(Employee) session.get(Employee.class,id);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        if(emp==null)
            return null;
        return emp;
    }



    public List<Employee> getAllEmployees() {
        Transaction transaction=null;
        List<Employee> employees=new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            employees = session.createNativeQuery("select * from employee", Employee.class).getResultList();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        if(employees.isEmpty() || employees==null){
            return null;
        }
        return employees;
    }

    public Employee updateEmployee(int id,Employee employee) {
        Transaction transaction=null;
        Employee emp=employee;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            if (session.get(Employee.class, id) != null) {
                session.update(employee);
            }
            else{
                emp=null;
            }
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return emp;
    }

    public void deleteEmployee(int id) {
        Transaction transaction=null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.delete(employee);
            }
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
