-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 13 déc. 2024 à 11:30
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `devoirblanc`
--

-- --------------------------------------------------------

--
-- Structure de la table `incident`
--

CREATE TABLE `incident` (
                            `reference` varchar(100) NOT NULL,
                            `description` text NOT NULL,
                            `date` date NOT NULL,
                            `time` time NOT NULL,
                            `status` enum('Open','In Progress','Resolved','Closed') NOT NULL DEFAULT 'Open',
                            `responsable_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `membre`
--

CREATE TABLE `membre` (
                          `id` varchar(255) NOT NULL,
                          `nom` varchar(100) NOT NULL,
                          `prenom` varchar(100) NOT NULL,
                          `email` varchar(150) NOT NULL,
                          `phone` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `membre`
--

INSERT INTO `membre` (`id`, `nom`, `prenom`, `email`, `phone`) VALUES
    ('3457edc3-8e54-4618-a831-3bcde0e33c10', 'ait', 'vksf', 'ghitaam73@gmail.com', '123456789');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `incident`
--
ALTER TABLE `incident`
    ADD PRIMARY KEY (`reference`),
  ADD KEY `responsable_id` (`responsable_id`);

--
-- Index pour la table `membre`
--
ALTER TABLE `membre`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `incident`
--
ALTER TABLE `incident`
    ADD CONSTRAINT `incident_ibfk_1` FOREIGN KEY (`responsable_id`) REFERENCES `membre` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
