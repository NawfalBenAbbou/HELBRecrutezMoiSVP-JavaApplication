package com.example;

/**
 * Représente un match entre deux candidats.
 *
 * Rôle :
 * - Encapsuler une confrontation entre deux candidats
 *
 * Fonction :
 * - Stocker les deux candidats et leurs scores associés
 * - Fournir des calculs dérivés (delta, gagnant, perdant)
 *
 * Contexte :
 * - Utilisé dans le moteur de sélection et l’historique de session
 *
 * Design :
 * - Modèle métier pur (aucune dépendance UI / IO)
 * - Forte cohésion : uniquement logique liée au match
 */
public class Match {

    private static final boolean A_WINS_ON_TIE = true;

    private final Candidate candidateA;
    private final Candidate candidateB;

    private final int scoreA;
    private final int scoreB;

    /**
     * Construit un match entre deux candidats.
     *
     * Rôle :
     * - Initialiser un objet Match immuable
     *
     * Fonction :
     * - Associer deux candidats et leurs scores respectifs
     *
     * Contexte :
     * - Créé par les stratégies de sélection
     */
    public Match(Candidate candidateA,
                 Candidate candidateB,
                 int scoreA,
                 int scoreB) {

        this.candidateA = candidateA;
        this.candidateB = candidateB;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    // =========================
    // GETTERS
    // =========================

    /**
     * Retourne le candidat A.
     */
    public Candidate getCandidateA() {
        return candidateA;
    }

    /**
     * Retourne le candidat B.
     */
    public Candidate getCandidateB() {
        return candidateB;
    }

    /**
     * Retourne le score du candidat A.
     */
    public int getScoreA() {
        return scoreA;
    }

    /**
     * Retourne le score du candidat B.
     */
    public int getScoreB() {
        return scoreB;
    }

    // =========================
    // BUSINESS LOGIC
    // =========================

    /**
     * Calcule l’écart de score global (A - B).
     *
     * Rôle :
     * - Mesurer la différence de performance entre les deux candidats
     *
     * Fonction :
     * - scoreA - scoreB
     *
     * Contexte :
     * - Utilisé pour les bonus et l’évaluation du match
     */
    public int getDelta() {
        return scoreA - scoreB;
    }

    /**
     * Calcule le delta du point de vue d’un candidat sélectionné.
     *
     * Rôle :
     * - Adapter l’écart de score selon le candidat choisi
     *
     * Fonction :
     * - Retourne score sélectionné - score adverse
     *
     * Contexte :
     * - Utilisé pour le calcul du bonus utilisateur
     */
    public int getDeltaFor(Candidate selected) {

        if (selected.isSameCandidate(candidateA)) {
            return scoreA - scoreB;
        }

        return scoreB - scoreA;
    }

    /**
     * Retourne le candidat gagnant du match.
     *
     * Rôle :
     * - Identifier le meilleur candidat
     *
     * Fonction :
     * - Compare scoreA et scoreB
     *
     * Contexte :
     * - Utilisé pour affichage et historique
     *
     * Règle :
     * - En cas d’égalité, candidateA est considéré gagnant (règle métier)
     */
    public Candidate getWinner() {

        if (scoreA > scoreB || (scoreA == scoreB && A_WINS_ON_TIE)) {
            return candidateA;
        }

        return candidateB;
    }

    /**
     * Retourne le candidat perdant du match.
     *
     * Rôle :
     * - Identifier le candidat non sélectionné comme gagnant
     *
     * Fonction :
     * - Inverse de getWinner()
     *
     * Contexte :
     * - Utilisé dans l’historique et l’affichage UI
     */
    public Candidate getLoser() {

        if (scoreA > scoreB || (scoreA == scoreB && A_WINS_ON_TIE)) {
            return candidateB;
        }

        return candidateA;
    }
}