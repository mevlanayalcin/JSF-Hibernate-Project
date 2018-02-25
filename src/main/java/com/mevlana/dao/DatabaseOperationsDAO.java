package com.mevlana.dao;

import com.mevlana.entity.Student;
import com.mevlana.sessionFactory.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperationsDAO
{
    private SessionFactory factory= SessionFactorySingleton.getSessionFactory();

    public List<Student> getStudents()
    {
        List<Student> studentList=new ArrayList<Student>();
        Session session=factory.getCurrentSession();

        session.beginTransaction();
        studentList=session.createQuery("from Student").getResultList();
        session.getTransaction().commit();
        session.close();
        return studentList;

    }

    public void addStudent(Student theStudent)
    {
        Session session=factory.getCurrentSession();
        session.beginTransaction();
        session.save(theStudent);
        session.getTransaction().commit();
        session.close();
    }


    public Student getStudent(int studentId)
    {
        Student theStudent;
        Session session=factory.getCurrentSession();
        session.beginTransaction();
        theStudent=session.get(Student.class,studentId);
        session.getTransaction().commit();
        session.close();
        return theStudent;
    }

    public void updateStudent(Student theStudent)
    {

        Session session=factory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(theStudent);
        session.getTransaction().commit();
        session.close();

    }

    public void deleteStudent(int studentId)
    {
        Session session=factory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from Student where id=?").setParameter(0,studentId).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }


}
