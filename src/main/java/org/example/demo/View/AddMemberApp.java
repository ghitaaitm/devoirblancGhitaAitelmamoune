package org.example.demo.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

import org.example.demo.DAO.DBConnection;  // Import the DBConnection class
import java.util.UUID;

public class AddMemberApp extends Application {

    private TextField nameField, surnameField, emailField, phoneField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ajouter un membre");

        // Labels et champs de texte
        Label nameLabel = new Label("Nom:");
        nameField = new TextField();

        Label surnameLabel = new Label("Prénom:");
        surnameField = new TextField();

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label phoneLabel = new Label("Téléphone:");
        phoneField = new TextField();

        // Bouton Ajouter
        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> handleAddMember());

        // Mise en page
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(surnameLabel, 0, 1);
        gridPane.add(surnameField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(phoneLabel, 0, 3);
        gridPane.add(phoneField, 1, 3);
        gridPane.add(addButton, 1, 4);

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleAddMember() {
        // Génération d'un identifiant unique
        String id = UUID.randomUUID().toString();
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        // Vérification des champs
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur de validation", "Tous les champs doivent être remplis.");
            return;
        }

        // Insertion dans la base de données
        String sql = "INSERT INTO Membre (id, nom, prenom, email, phone) VALUES (?, ?, ?, ?, ?)";

        // Try-with-resources to automatically close resources
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (connection == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Impossible de se connecter à la base de données.");
                return;
            }

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Membre ajouté à la base de données.");
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter le membre à la base de données.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nameField.clear();
        surnameField.clear();
        emailField.clear();
        phoneField.clear();
    }
}
