package com.example;

import java.util.List;

/**
 * Stratégie de sélection locale des candidats.
 *
 * Rôle :
 * - Sélectionner un match basé sur l’écart de distance
 *
 * Fonction :
 * - Compare les distances entre tous les candidats
 * - Retourne la paire présentant le plus grand écart
 *
 * Contexte :
 * - Utilisé dans le moteur de matchmaking
 *
 * Design :
 * - Pattern Strategy
 * - Justification : améliore la cohésion en isolant
 *   une logique de sélection spécialisée
 */
public class LocalScoreSelectionStrategy
        implements MatchSelectionStrategy {

    // =========================
    // Constantes
    // =========================

    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static final int INDEX_INCREMENT = 1;

    // =========================
    // Singleton
    // =========================

    private static LocalScoreSelectionStrategy INSTANCE;

    // =========================
    // Constructeur
    // =========================

    /**
     * Constructeur privé du Singleton.
     *
     * Rôle :
     * - Empêcher l’instanciation externe
     *
     * Fonction :
     * - Garantit une unique instance de la stratégie
     *
     * Contexte :
     * - Utilisé par le pattern Singleton
     *
     * Design :
     * - Réduction du couplage
     * - Centralisation de la stratégie de sélection
     */
    private LocalScoreSelectionStrategy() {
    }

    // =========================
    // API publique
    // =========================

    /**
     * Retourne l’instance unique de la stratégie.
     *
     * Rôle :
     * - Fournir un accès global contrôlé
     *
     * Fonction :
     * - Initialise l’instance si nécessaire
     *
     * Contexte :
     * - Utilisé par le moteur de matchmaking
     *
     * Retour :
     * - Instance unique de LocalScoreSelectionStrategy
     */
    public static LocalScoreSelectionStrategy getInstance() {

        if (INSTANCE == null) {
            INSTANCE =
                    new LocalScoreSelectionStrategy();
        }

        return INSTANCE;
    }

    /**
     * Sélectionne un match basé sur l’écart de distance.
     *
     * Rôle :
     * - Identifier la paire de candidats retenue
     *
     * Fonction :
     * - Analyse toutes les combinaisons possibles
     * - Recherche le plus grand écart de distance
     * - Construit ensuite un objet Match
     *
     * Paramètres :
     * - candidates : liste des candidats disponibles
     *
     * Retour :
     * - Match calculé selon la stratégie locale
     *
     * Contexte :
     * - Utilisé par le moteur principal de sélection
     *
     * Design :
     * - Encapsulation complète de l’algorithme de sélection
     */
    @Override
    public Match selectMatch(//choisir le duo plus grande distance a chaque fois puis diminue diminue se resserrer
            List<Candidate> candidates
    ) {

        Candidate bestA =
                candidates.get(FIRST_INDEX);

        Candidate bestB =
                candidates.get(SECOND_INDEX);

        int bestGap = Math.abs(
                bestA.getDistanceKm()
                - bestB.getDistanceKm()
        );

        /*
         Compare toutes les combinaisons possibles
         afin d’identifier la paire présentant
         le plus grand écart de distance.
        */
        for (
                int i = FIRST_INDEX;
                i < candidates.size();
                i++
        ) {

            for (
                    int j = i + INDEX_INCREMENT;
                    j < candidates.size();
                    j++
            ) {

                Candidate a =
                        candidates.get(i);

                Candidate b =
                        candidates.get(j);

                int gap = Math.abs(
                        a.getDistanceKm()
                        - b.getDistanceKm()
                );

                if (gap > bestGap) {

                    bestGap = gap;
                    bestA = a;
                    bestB = b;
                }
            }
        }

        List<String> keywords =
                KeywordRepository
                        .getInstance()
                        .getKeywords();

        return new Match(
                bestA,
                bestB,
                bestA.computeScore(keywords),
                bestB.computeScore(keywords)
        );
    }
}