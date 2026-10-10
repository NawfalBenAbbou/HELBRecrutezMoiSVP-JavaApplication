package com.example;

/**
 * Contrôleur de la fenêtre de détail candidat.
 *
 * Rôle :
 * - Gérer les interactions liées à un candidat affiché
 *
 * Fonction :
 * - Valider les prédictions du recruteur
 * - Calculer le bonus selon les règles métier
 * - Appliquer le bonus à la session recruteur
 *
 * Contexte :
 * - Utilisé dans l’interface JavaFX de détail candidat
 *
 * Design :
 * - Contrôleur métier découplé de la vue
 * - Favorise la cohésion en centralisant la logique de calcul
 */
public class CandidateDetailsController {

    // =========================
    // Constantes métier
    // =========================

    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 100;

    private static final double TOLERANCE_RATE = 0.15;
    private static final double BONUS_RATE = 0.20;

    private static final double ZERO_DOUBLE = 0.0;
    private static final int ZERO_INT = 0;

    private final RecruiterSession session;
    private final Candidate candidate;

    /**
     * Constructeur du contrôleur de détail candidat.
     *
     * Rôle :
     * - Initialiser le contrôleur avec la session recruteur et le candidat courant
     *
     * Fonction :
     * - Stocke les références nécessaires au traitement des bonus et des interactions
     *
     * Contexte :
     * - Utilisé lors de l’ouverture de la vue de détail d’un candidat
     *
     * Design :
     * - Injection de dépendances pour garantir un faible couplage
     */
    public CandidateDetailsController(RecruiterSession session, Candidate candidate) {
        this.session = session;
        this.candidate = candidate;
    }

    /**
     * Retourne le candidat actuellement associé au contrôleur.
     *
     * Rôle :
     * - Exposer le candidat traité par ce contrôleur
     *
     * Fonction :
     * - Permet l’accès en lecture au modèle candidat
     *
     * Contexte :
     * - Utilisé par la vue pour afficher les informations du candidat
     */
    public Candidate getCandidate() {
        return candidate;
    }

    /**
     * Vérifie si une prédiction est dans les bornes autorisées.
     *
     * Rôle :
     * - Valider la cohérence d’une entrée utilisateur
     *
     * Fonction :
     * - Vérifie que la valeur est comprise entre MIN_SCORE et MAX_SCORE
     *
     * Contexte :
     * - Utilisé avant tout calcul de bonus
     *
     * Design :
     * - Encapsulation des règles de validation
     */
    public boolean isValidPrediction(int prediction) {

        return prediction >= MIN_SCORE
                && prediction <= MAX_SCORE;
    }

    /**
     * Calcule le bonus attribué selon la précision de la prédiction.
     *
     * Rôle :
     * - Évaluer la qualité de la prédiction du recruteur
     *
     * Fonction :
     * - Compare la prédiction avec le score réel
     * - Applique une tolérance et un coefficient de bonus
     *
     * Contexte :
     * - Utilisé dans le système de scoring du recruteur
     *
     * Design :
     * - Logique métier centralisée pour éviter duplication
     */
    public int computeBonus(
            int prediction,
            int realScore,
            int matchDelta
    ) {

        if (!isValidPrediction(prediction)) {
            return ZERO_INT;
        }

        int tolerance = //le (int) pour le convertir en int car le math round donne un long 
                (int) Math.round(realScore * TOLERANCE_RATE); //vrai score * la tolerance       80 * 0.15  = 12

        int difference =
                Math.abs(prediction - realScore);//disons prediction 70  alors 70-80 = -10 mais abs donc 10 

        if (difference <= tolerance) {    // 10 <= 12 donc oui alors 

            return (int) Math.round(
                    Math.abs(matchDelta) * BONUS_RATE // le score du match * 0.20 pour recevoir le bonus donc   le match 50 bah 50 * 0.2 = 10 e bonus est de 10
            );
        }

        return ZERO_INT;
    }

    /**
     * Applique le bonus calculé à la session recruteur.
     *
     * Rôle :
     * - Mettre à jour le score global du recruteur
     *
     * Fonction :
     * - Calcule puis ajoute le bonus à la session
     *
     * Contexte :
     * - Utilisé après validation d’une prédiction utilisateur
     *
     * Design :
     * - Séparation des responsabilités (SRP)
     */
    public void applyBonus(
            int prediction,
            int realScore,
            int matchDelta
    ) {

        int bonus = computeBonus(//appelle en haut 
                prediction,
                realScore,
                matchDelta
        );

        session.addBonus(bonus);//puis le mets 
    }
}