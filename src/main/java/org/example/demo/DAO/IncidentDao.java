package org.example.demo.DAO;

import org.example.demo.models.Incident;

import java.util.Set;

public interface IncidentDao {
    void inserer(Incident incident);
    void inserer(Set<Incident> incidents);
}
