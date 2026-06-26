package com.spacetravel.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planet")
public class Planet {
    
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "name", nullable = false, length = 500)
    private String name;
    
    @OneToMany(mappedBy = "fromPlanet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> ticketsFrom = new ArrayList<>();
    
    @OneToMany(mappedBy = "toPlanet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> ticketsTo = new ArrayList<>();
    
    public Planet() {
    }
    
    public Planet(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Ticket> getTicketsFrom() {
        return ticketsFrom;
    }
    
    public void setTicketsFrom(List<Ticket> ticketsFrom) {
        this.ticketsFrom = ticketsFrom;
    }
    
    public List<Ticket> getTicketsTo() {
        return ticketsTo;
    }
    
    public void setTicketsTo(List<Ticket> ticketsTo) {
        this.ticketsTo = ticketsTo;
    }
}
