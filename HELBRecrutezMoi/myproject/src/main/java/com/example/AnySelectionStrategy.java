package com.example;

import java.util.List;
import java.util.Random;

/**
 * Représente une stratégie de sélection de match aléatoire.
 *
 * Rôle :
 * - Implémente une stratégie de sélection hybride
 *
 * Fonction :
 * - Délègue la sélection soit à une stratégie basée sur la localisation
 *   soit à une stratégie basée sur le niveau de compétence
 *
 * Contexte :
 * - Utilisée dans le moteur de matchmaking pour diversifier les résultats
 *
 * Design :
 * - Pattern Strategy + Singleton
 * - Justification : améliore la cohésion en centralisant la logique de sélection
 *   et réduit le couplage avec les stratégies concrètes
 */
public class AnySelectionStrategy implements MatchSelectionStrategy {

    // Instance unique (Singleton)
    private static AnySelectionStrategy INSTANCE;

    private final Random random = new Random();

    private final MatchSelectionStrategy localStrategy;
    private final MatchSelectionStrategy skillStrategy;

    /**
     * Constructeur privé de la stratégie de sélection hybride.
     *
     * Rôle :
     * - Empêcher l’instanciation externe (pattern Singleton)
     *
     * Fonction :
     * - Initialise les deux stratégies internes (local et skill)
     *
     * Contexte :
     * - Utilisé uniquement via getInstance() pour garantir une instance unique
     *
     * Design :
     * - Singleton + Strategy
     * - Justification : améliore la cohésion en centralisant la logique de sélection
     *   et réduit le couplage avec les stratégies concrètes
     */
    private AnySelectionStrategy() {
        this.localStrategy = LocalScoreSelectionStrategy.getInstance();
        this.skillStrategy = SkillScoreSelectionStrategy.getInstance();
    }

    /**
     * Retourne l'instance unique de la stratégie.
     *
     * Fonction :
     * - Implémente un Singleton lazy
     *
     * Retour :
     * - Instance unique de AnySelectionStrategy
     *
     */
    public static AnySelectionStrategy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AnySelectionStrategy();
        }
        return INSTANCE;
    }

    /**
     * Sélectionne un match en déléguant à une stratégie concrète.
     *
     * Paramètres :
     * - candidates : liste des candidats disponibles pour le match
     *
     * Retour :
     * - match sélectionné selon une stratégie aléatoire
     */
    @Override
    public Match selectMatch(List<Candidate> candidates) {//candidates Nom de la variable qui contient cette liste

        //Génère aléatoirement true ou false.
        //Si le résultat est true, le bloc if est exécuté.
        if (random.nextBoolean()) {
            return localStrategy.selectMatch(candidates);
        }

        return skillStrategy.selectMatch(candidates);
    }
}