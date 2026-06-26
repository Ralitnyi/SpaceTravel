package com.spacetravel.service;

import com.spacetravel.entity.Client;
import com.spacetravel.entity.Planet;
import com.spacetravel.entity.Ticket;
import com.spacetravel.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.Instant;
import java.util.List;

public class TicketCrudService {
    
    private final ClientCrudService clientCrudService;
    private final PlanetCrudService planetCrudService;
    
    public TicketCrudService() {
        this.clientCrudService = new ClientCrudService();
        this.planetCrudService = new PlanetCrudService();
    }
    
    public Ticket createTicket(Long clientId, String fromPlanetId, String toPlanetId) {
        if (clientId == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }
        if (fromPlanetId == null) {
            throw new IllegalArgumentException("From planet ID cannot be null");
        }
        if (toPlanetId == null) {
            throw new IllegalArgumentException("To planet ID cannot be null");
        }
        
        Client client = clientCrudService.getClientById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client with ID " + clientId + " does not exist");
        }
        
        Planet fromPlanet = planetCrudService.getPlanetById(fromPlanetId);
        if (fromPlanet == null) {
            throw new IllegalArgumentException("Planet with ID " + fromPlanetId + " does not exist");
        }
        
        Planet toPlanet = planetCrudService.getPlanetById(toPlanetId);
        if (toPlanet == null) {
            throw new IllegalArgumentException("Planet with ID " + toPlanetId + " does not exist");
        }
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Ticket ticket = new Ticket(Instant.now(), client, fromPlanet, toPlanet);
            session.persist(ticket);
            transaction.commit();
            return ticket;
        }
    }
    
    public Ticket getTicketById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Ticket.class, id);
        }
    }
    
    public List<Ticket> getAllTickets() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Ticket", Ticket.class).list();
        }
    }
    
    public List<Ticket> getTicketsByClientId(Long clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Ticket WHERE client.id = :clientId", Ticket.class)
                    .setParameter("clientId", clientId)
                    .list();
        }
    }
    
    public boolean deleteTicket(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            if (ticket != null) {
                session.remove(ticket);
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;
        }
    }
}
