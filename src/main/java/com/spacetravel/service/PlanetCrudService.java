package com.spacetravel.service;

import com.spacetravel.entity.Planet;
import com.spacetravel.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlanetCrudService {
    
    public Planet createPlanet(String id, String name) {
        if (id == null || !id.matches("^[A-Z0-9]+$")) {
            throw new IllegalArgumentException("Planet ID must contain only uppercase letters and digits");
        }
        if (name == null || name.length() < 1 || name.length() > 500) {
            throw new IllegalArgumentException("Planet name must be between 1 and 500 characters");
        }
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Planet planet = new Planet(id, name);
            session.persist(planet);
            transaction.commit();
            return planet;
        }
    }
    
    public Planet getPlanetById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Planet.class, id);
        }
    }
    
    public List<Planet> getAllPlanets() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Planet", Planet.class).list();
        }
    }
    
    public Planet updatePlanet(String id, String name) {
        if (name == null || name.length() < 1 || name.length() > 500) {
            throw new IllegalArgumentException("Planet name must be between 1 and 500 characters");
        }
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Planet planet = session.get(Planet.class, id);
            if (planet != null) {
                planet.setName(name);
                session.merge(planet);
                transaction.commit();
                return planet;
            }
            transaction.rollback();
            return null;
        }
    }
    
    public boolean deletePlanet(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Planet planet = session.get(Planet.class, id);
            if (planet != null) {
                session.remove(planet);
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;
        }
    }
}
