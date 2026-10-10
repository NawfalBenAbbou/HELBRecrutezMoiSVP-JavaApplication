package com.example;

import javafx.application.Application;

/**
 * Point d’entrée minimal de l’application.
 *
 * Rôle :
 * - Démarrer le runtime JavaFX
 *
 * Fonction :
 * - Lance l’application JavaFX principale
 *
 * Contexte :
 * - Utilisé uniquement au démarrage du programme
 *
 * Design :
 * - Main volontairement minimaliste
 * - Respecte les contraintes JavaFX du projet
 * - Réduit le couplage avec l’interface utilisateur
 */
public class Main {

    /**
     * Point d’entrée principal du programme.
     *
     * Rôle :
     * - Démarrer l’application JavaFX
     *
     * Fonction :
     * - Délègue le lancement au runtime JavaFX
     *
     * Paramètres :
     * - args : arguments de lancement éventuels
     *
     * Contexte :
     * - Méthode appelée automatiquement au démarrage
     *
     * Design :
     * - Main minimaliste conformément aux règles projet
     */
    public static void main(String[] args) {

        Application.launch(//instance mainview et donc lance start dans mainview
                MainView.class,
                args
        );
    }
}