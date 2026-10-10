package com.example;

/**
 * Validateur des candidats du système.
 *
 * Rôle :
 * - Vérifier la cohérence et la validité des données candidat
 *
 * Fonction :
 * - Contrôle les champs obligatoires
 * - Vérifie les contraintes métier liées à la motivation
 *
 * Contexte :
 * - Utilisé lors du chargement des candidats depuis DATA
 *
 * Design :
 * - Classe utilitaire statique
 * - Justification : améliore la cohésion en centralisant
 *   toutes les règles de validation métier
 */
public final class CandidateValidator {

    // =========================
    // Constantes texte
    // =========================

    private static final String HTTPS_PREFIX =
            "https://";
    private static int ZERO_INT = 0;

    // =========================
    // Constructeur privé
    // =========================

    /**
     * Constructeur privé utilitaire.
     *
     * Rôle :
     * - Empêcher l’instanciation de la classe
     *
     * Fonction :
     * - Cette classe contient uniquement des méthodes statiques
     *
     * Contexte :
     * - Pattern classique des classes utilitaires Java
     *
     * Design :
     * - Classe utilitaire statique
     * - Améliore la cohésion en regroupant les validations métier
     */
    private CandidateValidator() {
    }

    // =========================
    // Validation principale
    // =========================

    /**
     * Vérifie si un candidat est valide.
     *
     * Rôle :
     * - Centraliser les validations métier du candidat
     *
     * Fonction :
     * - Vérifie les champs obligatoires
     * - Vérifie la validité de la motivation
     *
     * Contexte :
     * - Utilisé lors du chargement des candidats
     *
     * Design :
     * - Point d’entrée unique pour la validation candidat
     */
    public static boolean isValid(Candidate candidate) {

        if (!isNonEmpty(candidate.getFirstName())) {
            return false;
        }

        if (!isNonEmpty(candidate.getMotivation())) {
            return false;
        }

        return isValidMotivation(
                candidate.getMotivation()
        );
    }

    // =========================
    // Validation motivation
    // =========================

    /**
     * Vérifie si une motivation respecte les contraintes métier.
     *
     * Rôle :
     * - Empêcher les motivations invalides ou incohérentes
     *
     * Fonction :
     * - Refuse les chaînes vides
     * - Refuse les URLs
     * - Refuse les chaînes composées uniquement de chiffres
     *
     * Contexte :
     * - Utilisé lors du chargement des profils candidats
     *
     * Design :
     * - Encapsulation des règles métier liées aux motivations
     */
    private static boolean isValidMotivation(
            String motivation
    ) {

        if (!isNonEmpty(motivation)) {
            return false;
        }

        /*
         Les URLs sont interdites dans les motivations
         afin d’éviter le contenu parasite ou promotionnel.
        */
        if (motivation.contains(HTTPS_PREFIX)) {
            return false;
        }

        /*
         Une motivation composée uniquement de chiffres
         est considérée comme invalide métier.
        */
        if (containsOnlyDigits(motivation)) {
            return false;
        }

        return containsAtLeastOneLetter(
                motivation
        );
    }

    // =========================
    // Helpers
    // =========================

    /**
     * Vérifie qu’une chaîne contient du contenu.
     *
     * Rôle :
     * - Éviter les chaînes vides ou composées d’espaces
     *
     * Fonction :
     * - Supprime les espaces puis vérifie la longueur
     *
     * Contexte :
     * - Utilisé dans toutes les validations texte
     */
    private static boolean isNonEmpty(
            String value
    ) {

        return value.trim().length() > ZERO_INT;
    }

    /**
     * Vérifie si une chaîne contient uniquement des chiffres.
     *
     * Rôle :
     * - Identifier les contenus numériques purs
     *
     * Fonction :
     * - Analyse caractère par caractère
     *
     * Contexte :
     * - Utilisé dans la validation des motivations
     */
    private static boolean containsOnlyDigits(
            String value
    ) {

        for (int i = ZERO_INT; i < value.length(); i++) {

            if (!Character.isDigit(
                    value.charAt(i)
            )) {

                return false;
            }
        }

        return true;
    }

    /**
     * Vérifie si une chaîne contient au moins une lettre.
     *
     * Rôle :
     * - Garantir une motivation textuelle minimale
     *
     * Fonction :
     * - Analyse caractère par caractère
     *
     * Contexte :
     * - Utilisé pour empêcher les motivations invalides
     */
    private static boolean containsAtLeastOneLetter(
            String value
    ) {

        for (int i = ZERO_INT; i < value.length(); i++) {

            if (Character.isLetter(
                    value.charAt(i)
            )) {

                return true;
            }
        }

        return false;
    }
}