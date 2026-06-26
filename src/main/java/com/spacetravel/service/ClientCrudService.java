package com.spacetravel.service;

import com.spacetravel.entity.Client;
import com.spacetravel.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientCrudService {
    
    public Client createClient(String name) {
        if (name == null || name.length() < 3 || name.length() > 200) {
            throw new IllegalArgumentException("Name must be between 3 and 200 characters");
        }
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = new Client(name);
            session.persist(client);
            transaction.commit();
            return client;
        }
    }
    
    public Client getClientById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Client.class, id);
        }
    }
    
    public List<Client> getAllClients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Client", Client.class).list();
        }
    }
    
    public Client updateClient(Long id, String name) {
        if (name == null || name.length() < 3 || name.length() > 200) {
            throw new IllegalArgumentException("Name must be between 3 and 200 characters");
        }
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            if (client != null) {
                client.setName(name);
                session.merge(client);
                transaction.commit();
                return client;
            }
            transaction.rollback();
            return null;
        }
    }
    
    public boolean deleteClient(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            if (client != null) {
                session.remove(client);
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;
        }
    }
}
