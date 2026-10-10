package com.example;

/**
 * Interface Observer (pattern GoF).
 *
 * Rôle :
 * - Définir le contrat de mise à jour pour les objets observateurs
 *
 * Fonction :
 * - Recevoir une notification lors d’un changement d’état du Subject
 *
 * Contexte :
 * - Utilisée dans le pattern Observer pour découpler le modèle (Subject)
 *   et les vues (Observers)
 *
 * Design :
 * - Pattern Observer (GoF)
 * - Améliore la cohésion en séparant la logique métier des mises à jour UI
 * - Réduit le couplage entre le modèle et les composants d’affichage
 */
public interface Observer {

    /**
     * Méthode appelée lors d’un changement d’état du Subject.
     *
     * Rôle :
     * - Synchroniser l’état de l’observateur avec le modèle
     *
     * Fonction :
     * - Met à jour l’affichage ou les données dépendantes du state
     *
     * Contexte :
     * - Appelée automatiquement par RecruiterSession (Subject)
     *
     * parametres session état courant de la session recruteur
     */
    void update(RecruiterSession session);
}