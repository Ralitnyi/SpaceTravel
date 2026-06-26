package com.spacetravel;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

public class FlywayMigration {
    
    public static void main(String[] args) {
        FluentConfiguration configuration = Flyway.configure()
                .dataSource("jdbc:h2:~/SpaceTravel;DB_CLOSE_DELAY=-1", "sa", "")
                .locations("classpath:db/migration");
        
        Flyway flyway = configuration.load();
        
        System.out.println("Starting Flyway migration...");
        flyway.migrate();
        System.out.println("Flyway migration completed successfully!");
    }
}
