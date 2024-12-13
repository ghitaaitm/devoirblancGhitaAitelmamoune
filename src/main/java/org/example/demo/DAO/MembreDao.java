package org.example.demo.DAO;

import org.example.demo.models.Incident;
import org.example.demo.models.Membre;

import java.util.List;

public interface MembreDao {
    void inserer(Membre membre);
    List<Incident> chargerListIncidents(int membreId);
}
