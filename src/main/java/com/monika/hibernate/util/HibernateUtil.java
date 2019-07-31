package com.monika.hibernate.util;

import com.monika.hibernate.entity.Student;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registry
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                Configuration configuration = new Configuration();

                Properties dbSettings = new Properties();

                dbSettings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                dbSettings.put(Environment.URL, "jdbc:mysql://localhost:3306/hibernate_db?useSSL=false");
                dbSettings.put(Environment.USER, "root");
                dbSettings.put(Environment.PASS, "metadesi");
                dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                configuration.setProperties(dbSettings);
                configuration.addAnnotatedClass(Student.class);

                registry = registryBuilder.applySettings(configuration.getProperties()).build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}