package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Cœur du modèle de session recruteur.
 *
 * Rôle :
 * - Centraliser l’état global de la session utilisateur
 *
 * Fonction :
 * - Gérer score, bonus, historique et stratégie active
 * - Notifier les observers lors des changements d’état
 *
 * Contexte :
 * - Utilisé comme modèle principal de l’application JavaFX
 *
 * Design :
 * - Pattern Subject (Observer)
 * - Améliore la cohésion en regroupant l’état de session
 * - Découple la logique métier de la mise à jour UI
 */
public class RecruiterSession implements Subject {

    // =========================
    // OBSERVERS
    // =========================

    private final List<Observer> observers = new ArrayList<>();

    // =========================
    // STATE
    // =========================

    private final List<MatchHistoryEntry> history = new ArrayList<>();

    private int currentScore;
    private int bonusScore;

    private MatchSelectionStrategy currentStrategy;

    private long startTimeMillis;

    // =========================
    // CONSTANTES MÉTIER
    // =========================

    private static int ZERO_INT = 0;

    private static final double ONE_SECOND_MS = 1000.0;

    // =========================
    // CONSTRUCTEUR
    // =========================

    public RecruiterSession() {
        this.currentScore = ZERO_INT;
        this.bonusScore = ZERO_INT;
        this.startTimeMillis = System.currentTimeMillis();
        this.currentStrategy = StrategyFactory.getStrategy(StrategyFactory.Mode.RANDOM);
    }

    // =========================
    // OBSERVER PATTERN
    // =========================

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    // =========================
    // SESSION LOGIC
    // =========================

    /**
     * Applique le résultat d’un match à la session.
     *
     * Rôle :
     * - Mettre à jour le score global et l’historique
     *
     * Fonction :
     * - Ajoute le delta du match au score courant
     * - Ajoute une entrée dans l’historique
     * - Notifie les observers
     */
    public void applyMatchResult(Match match, Candidate selectedCandidate) {

        int delta = match.getDeltaFor(selectedCandidate);

        currentScore += delta;

        history.add(new MatchHistoryEntry(match, selectedCandidate));

        notifyObservers();
    }

    /**
     * Ajoute un bonus au score de session.
     *
     * Rôle :
     * - Mettre à jour le score bonus
     *
     * Fonction :
     * - Incrémenter bonusScore
     * - Notifier les observers
     */
    public void addBonus(int bonus) {
        bonusScore += bonus;
        notifyObservers();
    }

    /**
     * Réinitialise la session complète.
     *
     * Rôle :
     * - Remettre la session à zéro
     *
     * Fonction :
     * - Reset scores et historique
     * - Réinitialise le timer
     * - Notifie les observers
     */
    public void resetSession() {
        currentScore = ZERO_INT;
        bonusScore = ZERO_INT;
        history.clear();
        startTimeMillis = System.currentTimeMillis();

        notifyObservers();
    }

    // =========================
    // WORKING RATE
    // =========================

    /**
     * Calcule le taux de travail du recruteur.
     *
     * Rôle :
     * - Mesurer la productivité de sélection
     *
     * Fonction :
     * - Nombre de matchs traités / temps écoulé
     *
     * Contexte :
     * - Affiché dans l’interface utilisateur
     */
    public double getWorkingRate() {

        long elapsedMillis = System.currentTimeMillis() - startTimeMillis;

        if (elapsedMillis == ZERO_INT) {
            return ZERO_INT;
        }

        return (double) history.size() / (elapsedMillis / ONE_SECOND_MS);
    }

    // =========================
    // GETTERS
    // =========================

    public int getCurrentScore() {
        return currentScore;
    }

    public int getBonusScore() {
        return bonusScore;
    }

    public int getFinalScore() {
        return currentScore + bonusScore;
    }

    public List<MatchHistoryEntry> getHistory() {
        return new ArrayList<>(history);
    }

    // =========================
    // STRATEGY SWITCH
    // =========================

    /**
     * Change la stratégie de sélection active.
     *
     * Rôle :
     * - Modifier dynamiquement le comportement de sélection
     *
     * Fonction :
     * - Remplacer la stratégie courante
     * - Notifier les observers
     */
    public void setCurrentStrategy(MatchSelectionStrategy strategy) {
        this.currentStrategy = strategy;
        notifyObservers();
    }

    public MatchSelectionStrategy getStrategy() {
        return currentStrategy;
    }
}