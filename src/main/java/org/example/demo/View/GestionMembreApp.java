package org.example.demo.View;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class GestionMembreApp extends Application {

    private Set<Membre> membres = new HashSet<>(); // Ensemble pour stocker les membres sans doublons

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestion des Membres");

        // Interface utilisateur
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblNom = new Label("Nom:");
        TextField txtNom = new TextField();
        Label lblPrenom = new Label("Prénom:");
        TextField txtPrenom = new TextField();
        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        Label lblTelephone = new Label("Téléphone:");
        TextField txtTelephone = new TextField();

        Button btnAjouter = new Button("Ajouter");
        Button btnImporterCSV = new Button("Importer CSV");

        grid.add(lblNom, 0, 0);
        grid.add(txtNom, 1, 0);
        grid.add(lblPrenom, 0, 1);
        grid.add(txtPrenom, 1, 1);
        grid.add(lblEmail, 0, 2);
        grid.add(txtEmail, 1, 2);
        grid.add(lblTelephone, 0, 3);
        grid.add(txtTelephone, 1, 3);
        grid.add(btnAjouter, 0, 4);
        grid.add(btnImporterCSV, 1, 4);

        // Action pour ajouter un membre
        btnAjouter.setOnAction(e -> {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            String email = txtEmail.getText();
            String telephone = txtTelephone.getText();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            // Générer un identifiant unique
            String id = UUID.randomUUID().toString();
            Membre membre = new Membre(id, nom, prenom, email, telephone);

            if (membres.add(membre)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Membre ajouté avec succès.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Doublon", "Ce membre existe déjà.");
            }

            txtNom.clear();
            txtPrenom.clear();
            txtEmail.clear();
            txtTelephone.clear();
        });

        // Action pour importer un fichier CSV
        btnImporterCSV.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir un fichier CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));

            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    Set<Membre> membresImportes = chargerListeMembre(file.getAbsolutePath());
                    membres.addAll(membresImportes);
                    showAlert(Alert.AlertType.INFORMATION, "Importation réussie", membresImportes.size() + " membres importés.");
                } catch (IOException ex) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de lire le fichier CSV.");
                }
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour importer des membres à partir d'un fichier CSV
    public Set<Membre> chargerListeMembre(String nomFichier) throws IOException {
        Set<Membre> membresImportes = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = UUID.randomUUID().toString();
                    String nom = parts[0].trim();
                    String prenom = parts[1].trim();
                    String email = parts[2].trim();
                    String telephone = parts[3].trim();
                    membresImportes.add(new Membre(id, nom, prenom, email, telephone));
                }
            }
        }
        return membresImportes;
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Classe représentant un membre
    static class Membre {
        private String id;
        private String nom;
        private String prenom;
        private String email;
        private String telephone;

        public Membre(String id, String nom, String prenom, String email, String telephone) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.telephone = telephone;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Membre membre = (Membre) o;
            return Objects.equals(nom, membre.nom) &&
                    Objects.equals(prenom, membre.prenom) &&
                    Objects.equals(email, membre.email) &&
                    Objects.equals(telephone, membre.telephone);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nom, prenom, email, telephone);
        }
    }
}
