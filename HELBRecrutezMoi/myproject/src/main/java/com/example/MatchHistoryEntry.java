package com.example;

/**
 * Représente une entrée dans l’historique des matchs.
 *
 * Rôle :
 * - Stocker le résultat d’un match et le choix du recruteur
 *
 * Fonction :
 * - Encapsuler match + candidat sélectionné + delta calculé
 * - Fournir un format standard pour les rapports
 *
 * Contexte :
 * - Utilisé pour générer le fichier de session et l’affichage UI
 *
 * Design :
 * - DTO métier simple
 * - Cohésion élevée : uniquement la représentation d’un résultat
 */
public class MatchHistoryEntry {

    // =========================
    // CONSTANTES FORMAT
    // =========================

    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSE_PARENTHESIS = ")";
    private static final String VS_SEPARATOR = " vs ";
    private static final String DELTA_SEPARATOR = " : ";

    // =========================
    // ATTRIBUTS
    // =========================

    private final Match match;

    /**
     * Candidat sélectionné par le recruteur.
     * Utilisé pour le formatage et les calculs de delta contextuel.
     */
    private final Candidate selectedCandidate;

    /**
     * Différence de score du match (A - B).
     */
    private final int delta;

    /**
     * Construit une entrée d’historique de match.
     *
     * Rôle :
     * - Initialiser une représentation immuable d’un match joué
     *
     * Fonction :
     * - Stocker match + sélection + delta calculé
     *
     * Contexte :
     * - Créé lors de la résolution d’un match
     */
    public MatchHistoryEntry(Match match, Candidate selectedCandidate) {
        this.match = match;
        this.selectedCandidate = selectedCandidate;
        this.delta = match.getDeltaFor(selectedCandidate);
    }

    // =========================
    // GETTERS
    // =========================

    public Match getMatch() {
        return match;
    }

    public Candidate getSelectedCandidate() {
        return selectedCandidate;
    }

    public int getDelta() {
        return delta;
    }

    // =========================
    // REPORT FORMAT
    // =========================

    /**
     * Génère la représentation texte pour le rapport de session.
     *
     * Rôle :
     * - Formater une ligne exploitable dans le fichier de sortie
     *
     * Fonction :
     * - Affiche : (Selected) vs Other : delta
     *
     * Contexte :
     * - Utilisé par SessionReportWriter
     */
    public String formatForReport() {

        StringBuilder sb = new StringBuilder();//construire chaine progressivement

        sb.append(OPEN_PARENTHESIS)
          .append(selectedCandidate.getFirstName())
          .append(CLOSE_PARENTHESIS)
          .append(VS_SEPARATOR);

        Candidate other = getOtherCandidate();

        sb.append(other.getFirstName())
          .append(DELTA_SEPARATOR)
          .append(delta);

        return sb.toString();
    }

    // =========================
    // HELPERS
    // =========================

    /**
     * Retourne le candidat non sélectionné dans le match.
     *
     * Rôle :
     * - Identifier automatiquement l’adversaire
     *
     * Fonction :
     * - Compare avec selectedCandidate
     *
     * Contexte :
     * - Utilisé pour le format du rapport
     */
    private Candidate getOtherCandidate() {//celui qui n'a pas été sélectionné.   // pour formatreport

        if (match.getCandidateA().equals(selectedCandidate)) {
            return match.getCandidateB();
        }

        return match.getCandidateA();
    }
}