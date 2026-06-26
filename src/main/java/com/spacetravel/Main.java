package com.spacetravel;

import com.spacetravel.entity.Client;
import com.spacetravel.entity.Planet;
import com.spacetravel.entity.Ticket;
import com.spacetravel.service.ClientCrudService;
import com.spacetravel.service.PlanetCrudService;
import com.spacetravel.service.TicketCrudService;
import com.spacetravel.util.HibernateUtil;

import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Running Flyway Migrations ===");
        FlywayMigration.main(args);
        
        System.out.println("\n=== Testing Client CRUD Operations ===");
        testClientCrud();
        
        System.out.println("\n=== Testing Planet CRUD Operations ===");
        testPlanetCrud();
        
        System.out.println("\n=== Testing Ticket CRUD Operations ===");
        testTicketCrud();
        
        System.out.println("\n=== Testing Ticket Validation ===");
        testTicketValidation();
        
        HibernateUtil.shutdown();
    }
    
    private static void testClientCrud() {
        ClientCrudService clientService = new ClientCrudService();
        
        System.out.println("Creating client...");
        Client newClient = clientService.createClient("Test Client");
        System.out.println("Created client: " + newClient.getId() + " - " + newClient.getName());
        
        System.out.println("Reading client by ID...");
        Client retrievedClient = clientService.getClientById(newClient.getId());
        System.out.println("Retrieved client: " + retrievedClient.getName());
        
        System.out.println("Updating client...");
        Client updatedClient = clientService.updateClient(newClient.getId(), "Updated Test Client");
        System.out.println("Updated client: " + updatedClient.getName());
        
        System.out.println("Reading all clients...");
        List<Client> allClients = clientService.getAllClients();
        System.out.println("Total clients: " + allClients.size());
        
        System.out.println("Deleting client...");
        boolean deleted = clientService.deleteClient(newClient.getId());
        System.out.println("Client deleted: " + deleted);
    }
    
    private static void testPlanetCrud() {
        PlanetCrudService planetService = new PlanetCrudService();
        
        System.out.println("Creating planet...");
        Planet newPlanet = planetService.createPlanet("TEST", "Test Planet");
        System.out.println("Created planet: " + newPlanet.getId() + " - " + newPlanet.getName());
        
        System.out.println("Reading planet by ID...");
        Planet retrievedPlanet = planetService.getPlanetById(newPlanet.getId());
        System.out.println("Retrieved planet: " + retrievedPlanet.getName());
        
        System.out.println("Updating planet...");
        Planet updatedPlanet = planetService.updatePlanet(newPlanet.getId(), "Updated Test Planet");
        System.out.println("Updated planet: " + updatedPlanet.getName());
        
        System.out.println("Reading all planets...");
        List<Planet> allPlanets = planetService.getAllPlanets();
        System.out.println("Total planets: " + allPlanets.size());
        
        System.out.println("Deleting planet...");
        boolean deleted = planetService.deletePlanet(newPlanet.getId());
        System.out.println("Planet deleted: " + deleted);
    }
    
    private static void testTicketCrud() {
        TicketCrudService ticketService = new TicketCrudService();
        ClientCrudService clientService = new ClientCrudService();
        PlanetCrudService planetService = new PlanetCrudService();
        
        Client client = clientService.getClientById(1L);
        Planet fromPlanet = planetService.getPlanetById("EAR");
        Planet toPlanet = planetService.getPlanetById("MARS");
        
        System.out.println("Creating ticket...");
        Ticket newTicket = ticketService.createTicket(client.getId(), fromPlanet.getId(), toPlanet.getId());
        System.out.println("Created ticket: " + newTicket.getId());
        
        System.out.println("Reading ticket by ID...");
        Ticket retrievedTicket = ticketService.getTicketById(newTicket.getId());
        System.out.println("Retrieved ticket: " + retrievedTicket.getId());
        
        System.out.println("Reading tickets by client...");
        List<Ticket> clientTickets = ticketService.getTicketsByClientId(client.getId());
        System.out.println("Client tickets count: " + clientTickets.size());
        
        System.out.println("Reading all tickets...");
        List<Ticket> allTickets = ticketService.getAllTickets();
        System.out.println("Total tickets: " + allTickets.size());
        
        System.out.println("Deleting ticket...");
        boolean deleted = ticketService.deleteTicket(newTicket.getId());
        System.out.println("Ticket deleted: " + deleted);
    }
    
    private static void testTicketValidation() {
        TicketCrudService ticketService = new TicketCrudService();
        
        System.out.println("Testing null client validation...");
        try {
            ticketService.createTicket(null, "EAR", "MARS");
            System.out.println("ERROR: Should have thrown exception for null client");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }
        
        System.out.println("Testing non-existent client validation...");
        try {
            ticketService.createTicket(999L, "EAR", "MARS");
            System.out.println("ERROR: Should have thrown exception for non-existent client");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }
        
        System.out.println("Testing null planet validation...");
        try {
            ticketService.createTicket(1L, null, "MARS");
            System.out.println("ERROR: Should have thrown exception for null planet");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }
        
        System.out.println("Testing non-existent planet validation...");
        try {
            ticketService.createTicket(1L, "NONEXIST", "MARS");
            System.out.println("ERROR: Should have thrown exception for non-existent planet");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }
        
        System.out.println("Testing null to planet validation...");
        try {
            ticketService.createTicket(1L, "EAR", null);
            System.out.println("ERROR: Should have thrown exception for null to planet");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }
        
        System.out.println("Testing non-existent to planet validation...");
        try {
            ticketService.createTicket(1L, "EAR", "NONEXIST");
            System.out.println("ERROR: Should have thrown exception for non-existent to planet");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }
    }
}
