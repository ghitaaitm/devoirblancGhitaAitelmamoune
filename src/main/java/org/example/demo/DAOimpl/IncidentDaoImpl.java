package org.example.demo.DAOimpl;

import org.example.demo.DAO.IncidentDao;
import org.example.demo.models.Incident;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Set;

public class IncidentDaoImpl implements IncidentDao {
    private Connection connection;

    public IncidentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void inserer(Incident incident) {
        String query = "INSERT INTO Incident (reference, time, status, membre_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, incident.getReference());
            ps.setTimestamp(2, Timestamp.valueOf(incident.getTime()));
            ps.setString(3, incident.getStatus());
            ps.setInt(4, incident.getReference()); // Replace with actual membre_id
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inserer(Set<Incident> incidents) {
        for (Incident incident : incidents) {
            inserer(incident);
        }
    }
}
