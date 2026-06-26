# SpaceTravel

A Java application for managing space travel tickets between planets. This project demonstrates the use of Hibernate ORM, H2 database, and Flyway migrations.

## Features

 Manage clients, planets, and tickets for interplanetary travel
- CRUD operations for Clients, Planets, and Tickets
- Database migrations using Flyway
- ORM with Hibernate
- Validation for ticket creation (ensures client and planets exist)

## Technologies

- Java
- Gradle
- Hibernate 6.4.4.Final
- H2 Database 2.2.224
- Flyway 10.10.0

## Project Structure

```
SpaceTravel/
├── src/main/java/com/spacetravel/
│   ├── entity/
│   │   ├── Client.java       # Client entity with Hibernate mapping
│   │   ├── Planet.java       # Planet entity with Hibernate mapping
│   │   └── Ticket.java       # Ticket entity with one-to-many mappings
│   ├── service/
│   │   ├── ClientCrudService.java   # CRUD operations for clients
│   │   ├── PlanetCrudService.java   # CRUD operations for planets
│   │   └── TicketCrudService.java   # CRUD operations for tickets with validation
│   ├── util/
│   │   └── HibernateUtil.java      # Hibernate session factory configuration
│   ├── FlywayMigration.java         # Flyway migration runner
│   └── Main.java                    # Main application with tests
├── src/main/resources/
│   ├── hibernate.cfg.xml           # Hibernate configuration
│   └── db/migration/
│       ├── V1__create_db.sql        # Database schema creation
│       └── V2__populate_db.sql      # Sample data insertion
└── build.gradle.kts                 # Gradle build configuration
```

## Database Schema

### Client
- `id` (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
- `name` (VARCHAR(200), NOT NULL, 3-200 characters)

### Planet
- `id` (VARCHAR, PRIMARY KEY, uppercase letters and digits only)
- `name` (VARCHAR(500), NOT NULL, 1-500 characters)

### Ticket
- `id` (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
- `created_at` (TIMESTAMP, NOT NULL, UTC)
- `client_id` (BIGINT, FOREIGN KEY → client.id)
- `from_planet_id` (VARCHAR, FOREIGN KEY → planet.id)
- `to_planet_id` (VARCHAR, FOREIGN KEY → planet.id)

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle 9.2.0 or higher

### Installation

1. Clone the repository
2. Navigate to the project directory
3. Build the project:
```bash
./gradlew build
```

### Running the Application

Run the main application which executes Flyway migrations and tests all CRUD operations:

```bash
./gradlew run
```

### Running Tests

The Main.java file includes comprehensive tests for:
- Client CRUD operations (Create, Read, Update, Delete)
- Planet CRUD operations (Create, Read, Update, Delete)
- Ticket CRUD operations (Create, Read, Delete)
- Ticket validation (null checks, existence checks)

## Usage Examples

### Client Operations

```java
ClientCrudService clientService = new ClientCrudService();

// Create a client
Client client = clientService.createClient("John Doe");

// Read a client
Client retrieved = clientService.getClientById(1L);

// Update a client
Client updated = clientService.updateClient(1L, "Jane Doe");

// Delete a client
boolean deleted = clientService.deleteClient(1L);

// Get all clients
List<Client> allClients = clientService.getAllClients();
```

### Planet Operations

```java
PlanetCrudService planetService = new PlanetCrudService();

// Create a planet
Planet planet = planetService.createPlanet("MARS", "Mars - The Red Planet");

// Read a planet
Planet retrieved = planetService.getPlanetById("MARS");

// Update a planet
Planet updated = planetService.updatePlanet("MARS", "Mars Updated");

// Delete a planet
boolean deleted = planetService.deletePlanet("MARS");

// Get all planets
List<Planet> allPlanets = planetService.getAllPlanets();
```

### Ticket Operations

```java
TicketCrudService ticketService = new TicketCrudService();

// Create a ticket (validates that client and planets exist)
Ticket ticket = ticketService.createTicket(1L, "EAR", "MARS");

// Read a ticket
Ticket retrieved = ticketService.getTicketById(1L);

// Get tickets by client
List<Ticket> clientTickets = ticketService.getTicketsByClientId(1L);

// Get all tickets
List<Ticket> allTickets = ticketService.getAllTickets();

// Delete a ticket
boolean deleted = ticketService.deleteTicket(1L);
```

## Validation

The TicketCrudService includes validation to ensure:
- Client ID cannot be null
- Client must exist in the database
- From planet ID cannot be null
- From planet must exist in the database
- To planet ID cannot be null
- To planet must exist in the database

## Database Migrations

Flyway migrations are located in `src/main/resources/db/migration/`:

- **V1__create_db.sql**: Creates the database schema with all tables and constraints
- **V2__populate_db.sql**: Inserts sample data (10 clients, 5 planets, 10 tickets)

Migrations are automatically executed when running the application.

## License

This project is for educational purposes.
