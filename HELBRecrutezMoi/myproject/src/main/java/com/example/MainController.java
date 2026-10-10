package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur principal de l’application.
 *
 * Rôle :
 * - Orchestrer les interactions principales du système
 *
 * Fonction :
 * - Gérer les stratégies de sélection
 * - Générer les matchs candidats
 * - Gérer la résolution des matchs
 *
 * Contexte :
 * - Utilisé par l’interface principale JavaFX
 *
 * Design :
 * - Contrôleur central de coordination
 * - Justification : améliore la cohésion en centralisant
 *   la logique métier principale de l’application
 */
public class MainController {

    // =========================
    // Constantes
    // =========================

    private static final int MINIMUM_MATCH_SIZE = 2;

    // =========================
    // Attributs privés
    // =========================

    private final RecruiterSession session;

    private final List<Candidate> candidates;

    private final List<Candidate> availableCandidates;

    private StrategyFactory.Mode currentMode =
            StrategyFactory.Mode.RANDOM;

    // =========================
    // Constructeur
    // =========================

    /**
     * Initialise le contrôleur principal.
     *
     * Rôle :
     * - Préparer les données métier nécessaires
     *
     * Fonction :
     * - Charge les candidats
     * - Initialise la session et les candidats disponibles
     *
     * Paramètres :
     * - session : session recruteur active
     *
     * Contexte :
     * - Utilisé au démarrage de l’interface principale
     *
     * Design :
     * - Centralisation de la logique de coordination
     */
    public MainController(
            RecruiterSession session
    ) {

        this.session = session;

        this.candidates =
                CandidateRepository
                        .getInstance()
                        .getCandidates();

        this.availableCandidates =
                new ArrayList<>(candidates);
    }

    // =========================
    // Gestion stratégie
    // =========================

    /**
     * Définit le mode de sélection actif.
     *
     * Rôle :
     * - Modifier la stratégie utilisée par le système
     *
     * Fonction :
     * - Met à jour le mode courant
     * - Met à jour la stratégie active de la session
     *
     * Paramètres :
     * - mode : mode de sélection choisi
     *
     * Contexte :
     * - Utilisé lors du changement de mode utilisateur
     *
     * Design :
     * - Pattern Strategy
     * - Réduction du couplage avec les algorithmes métier
     */
    public void setMode(
            StrategyFactory.Mode mode
    ) {

        this.currentMode = mode;

        session.setCurrentStrategy(
                StrategyFactory.getStrategy(mode)
        );
    }

    // =========================
    // Génération match
    // =========================

    /**
     * Génère un nouveau match de candidats.
     *
     * Rôle :
     * - Sélectionner les candidats à comparer
     *
     * Fonction :
     * - Choisit le pool adapté au mode courant
     * - Délègue la sélection à la stratégie active
     *
     * Retour :
     * - Match généré par la stratégie active
     *
     * Contexte :
     * - Utilisé lors de la génération d’un duel candidat
     *
     * Design :
     * - Délégation vers les stratégies spécialisées
     */
    public Match generateMatch() {

        List<Candidate> pool;

        /*
         Les stratégies LOCAL et SKILL retirent
         progressivement les candidats déjà joués.
        */
        if (
                currentMode == StrategyFactory.Mode.LOCAL
                || currentMode == StrategyFactory.Mode.SKILL
        ) {

            pool = availableCandidates;

        } else {

            pool = candidates;
        }

        return session
                .getStrategy()
                .selectMatch(pool);
    }

    /**
     * Vérifie si un match peut être généré.
     *
     * Rôle :
     * - Empêcher la génération de matchs invalides
     *
     * Fonction :
     * - Vérifie que le pool contient assez de candidats
     *
     * Retour :
     * - true si un match est possible
     * - false sinon
     *
     * Contexte :
     * - Utilisé avant la génération d’un match
     */
    public boolean canGenerateMatch() {

        List<Candidate> pool;

        if (
                currentMode == StrategyFactory.Mode.LOCAL
                || currentMode == StrategyFactory.Mode.SKILL
        ) {

            pool = availableCandidates;

        } else {

            pool = candidates;
        }

        return pool.size()
                >= MINIMUM_MATCH_SIZE;
    }

    // =========================
    // Résolution match
    // =========================

    /**
     * Résout un match après sélection utilisateur.
     *
     * Rôle :
     * - Appliquer le résultat du duel candidat
     *
     * Fonction :
     * - Met à jour la session
     * - Retire les candidats déjà utilisés si nécessaire
     *
     * Paramètres :
     * - match : match traité
     * - selectedCandidate : candidat sélectionné
     *
     * Contexte :
     * - Utilisé après validation utilisateur
     *
     * Design :
     * - Encapsulation de la logique métier de résolution
     */
    public void resolveMatch(
            Match match,
            Candidate selectedCandidate
    ) {

        session.applyMatchResult(
                match,
                selectedCandidate
        );

        /*
         Les stratégies LOCAL et SKILL
         utilisent un système d’élimination
         progressive des candidats.
        */
        if (
                currentMode == StrategyFactory.Mode.LOCAL
                || currentMode == StrategyFactory.Mode.SKILL
        ) {

            availableCandidates.remove(
                    match.getCandidateA()
            );

            availableCandidates.remove(
                    match.getCandidateB()
            );
        }
    }
}