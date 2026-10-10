package com.example;

import java.util.List;

/**
 * Interface de stratégie pour la sélection d’un match entre candidats.
 *
 * Rôle :
 * - Définir le contrat de sélection d’un match
 *
 * Fonction :
 * - Fournir une méthode commune à toutes les stratégies de sélection
 *
 * Contexte :
 * - Utilisée dans le pattern Strategy pour varier les algorithmes
 *
 * Design :
 * - Pattern Strategy (GoF)
 * - Améliore la cohésion en isolant les algorithmes de sélection
 * - Permet l’extension sans modification du code existant (OCP)
 */
public interface MatchSelectionStrategy {

    /**
     * Sélectionne un match entre deux candidats.
     *
     * Rôle :
     * - Produire une paire de candidats sous forme de Match
     *
     * Fonction :
     * - Applique une stratégie de sélection sur une liste de candidats
     *
     * Contexte :
     * - Utilisé par le moteur de génération de matchs
     *
     * parametre candidates liste des candidats disponibles
     * renvoie match généré selon la stratégie
     */
    Match selectMatch(List<Candidate> candidates);//fonction avec liste des candidats
}