package org.example.demo.DAOimpl;

import org.example.demo.DAO.MembreDao;
import org.example.demo.models.Incident;
import org.example.demo.models.Membre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDaoImpl implements MembreDao {
    private Connection connection;

    public MembreDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void inserer(Membre membre) {
        String query = "INSERT INTO Membre (identifiant, nom, prenom, email, phone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, membre.getIdentifiant());
            ps.setString(2, membre.getNom());
            ps.setString(3, membre.getPrenom());
            ps.setString(4, membre.getEmail());
            ps.setString(5, membre.getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Incident> chargerListIncidents(int membreId) {
        List<Incident> incidents = new ArrayList<>();
        String query = "SELECT * FROM Incident WHERE membre_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, membreId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incidents.add(new Incident(
                        rs.getInt("reference"),
                        rs.getTimestamp("time").toLocalDateTime(),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incidents;
    }
}
