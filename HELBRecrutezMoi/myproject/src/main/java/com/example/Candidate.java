package com.example;

import java.util.List;

/**
 * Représente un candidat du système de sélection.
 *
 * Rôle :
 * - Modélise un candidat avec ses caractéristiques métier
 *
 * Fonction :
 * - Stocke les informations principales (nom, distance, motivation, image)
 * - Fournit des méthodes de comparaison et de calcul de score
 *
 * Contexte :
 * - Utilisé dans le moteur de sélection et de matchmaking
 *
 * Design :
 * - Objet métier central du système
 * - Favorise la cohésion en regroupant les données et comportements liés au candidat
 */
public class Candidate {

    // =========================
    // Constantes métier
    // =========================

    private static final int DISTANCE_MIN_KM = 1;
    private static final int DISTANCE_MAX_KM = 200;

    private static final double MAX_SCORE = 50.0;

    private static final double ZERO_DOUBLE = 0.0;
    private static final double ONE_DOUBLE = 1.0;

    private static final int ZERO_INT = 0;
    private static final int ONE_HUNDRED = 100;

    // =========================
    // Constantes texte
    // =========================

    private static final String CANDIDATE_TEXT = "Candidate{";
    private static final String FIRST_NAME_TEXT = "firstName='";
    private static final String DISTANCE_TEXT = ", distanceKm=";
    private static final String IMAGE_PATH_TEXT = ", imagePath='";
    private static final String SIMPLE_QUOTE_TEXT = "'";
    private static final String CLOSING_BRACKET_TEXT = "}";

    // =========================
    // Attributs
    // =========================

    private final String firstName;
    private final int distanceKm;
    private final String motivation;
    private final String imagePath;

    /**
     * Constructeur d’un candidat.
     *
     * Rôle :
     * - Initialiser un candidat avec ses données métier
     *
     * Fonction :
     * - Stocke les informations nécessaires au système de sélection
     *
     * Contexte :
     * - Utilisé lors du chargement ou de la création des candidats
     *
     * Design :
     * - Centralise l’initialisation des données métier du candidat
     */
    public Candidate(
            String firstName,
            int distanceKm,
            String motivation,
            String imagePath
    ) {

        this.firstName = firstName;
        this.distanceKm = distanceKm;
        this.motivation = motivation;
        this.imagePath = imagePath;
    }

    // =========================
    // Getters
    // =========================

    /**
     * Retourne le prénom du candidat.
     *
     * Rôle :
     * - Exposer le prénom du candidat
     *
     * Fonction :
     * - Fournit un accès en lecture au prénom
     *
     * Contexte :
     * - Utilisé dans l’interface utilisateur et le moteur métier
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retourne la distance du candidat.
     *
     * Rôle :
     * - Exposer la distance géographique du candidat
     *
     * Fonction :
     * - Fournit un accès en lecture à la distance
     *
     * Contexte :
     * - Utilisé dans le calcul des scores et l’affichage UI
     */
    public int getDistanceKm() {
        return distanceKm;
    }

    /**
     * Retourne la motivation du candidat.
     *
     * Rôle :
     * - Exposer le texte de motivation du candidat
     *
     * Fonction :
     * - Fournit un accès en lecture à la motivation
     *
     * Contexte :
     * - Utilisé dans l’analyse des mots-clés et l’interface utilisateur
     */
    public String getMotivation() {
        return motivation;
    }

    /**
     * Retourne le chemin de l’image du candidat.
     *
     * Rôle :
     * - Exposer la ressource visuelle du candidat
     *
     * Fonction :
     * - Fournit un accès au chemin d’image
     *
     * Contexte :
     * - Utilisé dans l’interface JavaFX pour afficher la photo du candidat
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Retourne le nom affiché du candidat.
     *
     * Rôle :
     * - Fournir un nom compatible avec l’affichage UI
     *
     * Fonction :
     * - Retourne le prénom du candidat
     *
     * Contexte :
     * - Peut être redéfini par les spécialisations Alpha/Beta
     *
     * Design :
     * - Favorise le polymorphisme d’affichage
     */
    public String getDisplayName() {
        return firstName;
    }

    /**
     * Compare deux candidats sur leurs attributs métier.
     *
     * Rôle :
     * - Vérifier l’équivalence entre deux objets Candidate
     *
     * Fonction :
     * - Compare tous les champs métier du candidat
     *
     * Contexte :
     * - Utilisé pour éviter les doublons dans les listes de candidats
     *
     * Design :
     * - Encapsulation de la logique de comparaison métier
     */
    public boolean isSameCandidate(Candidate other) {//other = autre candidat le B 
        return firstName.equals(other.firstName)
                && distanceKm == other.distanceKm
                && motivation.equals(other.motivation)
                && imagePath.equals(other.imagePath);
    }

    /**
     * Retourne une représentation textuelle du candidat.
     *
     * Rôle :
     * - Fournir une représentation lisible de l’objet
     *
     * Fonction :
     * - Construit une chaîne contenant les principales informations métier
     *
     * Contexte :
     * - Utilisé pour le debug et les logs
     */
    @Override
    public String toString() {
        return CANDIDATE_TEXT +
                FIRST_NAME_TEXT + firstName + SIMPLE_QUOTE_TEXT +
                DISTANCE_TEXT + distanceKm +
                IMAGE_PATH_TEXT + imagePath + SIMPLE_QUOTE_TEXT +
                CLOSING_BRACKET_TEXT;
    }

    /**
     * Calcule un score global basé sur la distance et les mots-clés.
     *
     * Rôle :
     * - Évaluer la pertinence d’un candidat
     *
     * Fonction :
     * - Combine une pondération de distance et de mots-clés
     *
     * Contexte :
     * - Utilisé dans le moteur de sélection pour classer les candidats
     *
     * Design :
     * - Algorithme de scoring métier encapsulé dans l’entité Candidate
     */
    public int computeScore(List<String> keywords) {

        double distancePart;

        if (distanceKm <= DISTANCE_MIN_KM) {
            distancePart = MAX_SCORE;
        } else if (distanceKm >= DISTANCE_MAX_KM) {
            distancePart = ZERO_DOUBLE;
        } else {
            distancePart =
                    MAX_SCORE * (ONE_DOUBLE - ((double)(distanceKm - DISTANCE_MIN_KM)//plus c'est grand moins c'est bien exemple 200km =>   200-1 /200-1 = 199/199 = 1  puis 1(one_double)- 1(resultat) =0
                    / (DISTANCE_MAX_KM - DISTANCE_MIN_KM)));                    //puis ici 0* maxore bah 0
        }

        /*
         Limitation défensive :
         le score distance est forcé dans les bornes autorisées
         afin d’éviter tout dépassement lié aux calculs intermédiaires.
        */
        if (distancePart < ZERO_DOUBLE) {
            distancePart = ZERO_DOUBLE;
        }

        if (distancePart > MAX_SCORE) {
            distancePart = MAX_SCORE;
        }

        int keywordHits = ZERO_INT;

        String mot = motivation.toLowerCase();

        /*
         Analyse simple basée sur la présence des mots-clés
         dans le texte de motivation du candidat.
        */
        for (String k : keywords) {  //chope tout les mots pour score motivation 
            if (mot.contains(k.toLowerCase())) {//true ou false
                keywordHits++;
            }
        }

        double keywordPart;

        if (keywords.isEmpty()) {
            keywordPart = ZERO_DOUBLE;
        } else {
            keywordPart =
                    ((double) keywordHits / keywords.size()) * MAX_SCORE;   //exemple 2 trouves et y a 3 dans keywords du prof alors 2/3 = 0.3333
        }

        int total = (int) Math.round(distancePart + keywordPart);

        /*
         Le score final est borné entre 0 et 100
         afin de garantir une valeur exploitable dans le système.
        */
        if (total < ZERO_INT) {
            return ZERO_INT;
        }

        if (total > ONE_HUNDRED) {
            return ONE_HUNDRED;
        }

        return total;
    }
}