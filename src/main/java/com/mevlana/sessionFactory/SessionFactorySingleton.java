package com.mevlana.sessionFactory;

import com.mevlana.entity.Student;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton
{

    private static SessionFactory factory;


    private SessionFactorySingleton()
    {
    }


    public static synchronized SessionFactory getSessionFactory()
    {

        if (factory == null)
        {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Student.class)
                    .buildSessionFactory();
        }
        return factory;
    }


}
