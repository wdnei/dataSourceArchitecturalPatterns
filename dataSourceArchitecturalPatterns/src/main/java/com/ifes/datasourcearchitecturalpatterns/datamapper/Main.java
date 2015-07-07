/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifes.datasourcearchitecturalpatterns.datamapper;

import java.util.List;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
/**
 *
 * @author admindev
 */
public class Main {
    
    private static SessionFactory sessionFactory = null;  
	private static ServiceRegistry serviceRegistry = null;  
	  
	private static SessionFactory configureSessionFactory() throws HibernateException {  
	    Configuration configuration = new Configuration();  
	    configuration.configure();  
	    
	    Properties properties = configuration.getProperties();
	    
		serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();          
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);  
	    
	    return sessionFactory;  
	}

    public static void main(String[] args) {
		// Configure the session factory
		configureSessionFactory();
		
		Session session = null;
		Transaction tx=null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			// Creating pessoa entity that will be save to the sqlite database
			Pessoa ps1 = new Pessoa(3, "My Name", 19,0);
			Pessoa ps2 = new Pessoa(24, "Your Name", 20,1);
			
			// Saving to the database
			session.save(ps1);
			session.save(ps2);
			
			// Committing the change in the database.
			session.flush();
			tx.commit();
			
			// Fetching saved data
			List<Pessoa> pessoaList = session.createQuery("from Pessoa").list();
			
			for (Pessoa pessoa : pessoaList) {
				System.out.println("Id: " + pessoa.getId() + " | Name:"  + pessoa.getNome() + " | Idade:" + pessoa.getIdade());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			// Rolling back the changes to make the data consistent in case of any failure 
			// in between multiple database write operations.
			tx.rollback();
		} finally{
			if(session != null) {
				session.close();
			}
		}
	}

}
