package com.example;

import java.util.Random;

/**
 * Factory responsable de la création du bon type de candidat
 * (Alpha ou Beta).
 *
 * Rôle :
 * - Centraliser la logique de création des objets Candidate
 *
 * Fonction :
 * - Instancier AlphaCandidate ou BetaCandidate selon des règles métier
 *
 * Contexte :
 * - Utilisée lors du chargement des fichiers candidats
 *
 * Design :
 * - Pattern Factory
 * - Améliore la cohésion en isolant la logique de création
 * - Réduit le couplage entre parsing et instanciation des objets
 */
public final class ProfileTypeFactory {

    // =========================
    // CONSTANTES MÉTIER
    // =========================

    private static final char SPECIAL_RANDOM_LETTER = 'R';

    private static final String VOWELS = "AEIOUY";

    private static final Random RANDOM = new Random();

    private static int ZERO_INT = 0;


    // =========================
    // CONSTRUCTEUR PRIVÉ
    // =========================

    private ProfileTypeFactory() {
    }

    // =========================
    // FACTORY METHOD
    // =========================

    /**
     * Crée un candidat en fonction des règles métier.
     *
     * Rôle :
     * - Déterminer le type concret de Candidate
     *
     * Fonction :
     * - Appliquer les règles de classification (Alpha / Beta / Random)
     *
     * Contexte :
     * - Appelé lors du chargement des profils candidats
     *
     * renvoie instance de Candidate (AlphaCandidate ou BetaCandidate)
     */
    public static Candidate createCandidate(String firstName,
                                            int distance,
                                            String motivation,
                                            String imagePath) {

        if (firstName.isEmpty()) {
            return new BetaCandidate(firstName, distance, motivation, imagePath);//pas Alpha alors BETA
        }

        char firstLetter = Character.toUpperCase(firstName.charAt(ZERO_INT));

        if (firstLetter == SPECIAL_RANDOM_LETTER) {
            return createRandomCandidate(firstName, distance, motivation, imagePath);//appele en bas la fonction
        }

        if (isVowel(firstLetter)) {
            return new AlphaCandidate(firstName, distance, motivation, imagePath);
        }

        return new BetaCandidate(firstName, distance, motivation, imagePath);//pas Alpha alors BETA
    }

    // =========================
    // RANDOM STRATEGY
    // =========================

    /**
     * Création aléatoire d’un candidat Alpha ou Beta.
     *
     * Rôle :
     * - Introduire une variabilité contrôlée dans la création
     *
     * Fonction :
     * - Tirage aléatoire pour choisir le type concret
     *
     * Contexte :
     * - Cas spécifique lorsque le prénom commence par une lettre spéciale
     */
    private static Candidate createRandomCandidate(String firstName,
                                                   int distance,
                                                   String motivation,
                                                   String imagePath) {

        if (RANDOM.nextBoolean()) {
            return new AlphaCandidate(firstName, distance, motivation, imagePath);
        }

        return new BetaCandidate(firstName, distance, motivation, imagePath);
    }

    // =========================
    // HELPERS
    // =========================

    /**
     * Vérifie si une lettre est une voyelle.
     *
     * Rôle :
     * - Support de la règle de classification Alpha
     *
     * Fonction :
     * - Recherche dans la liste des voyelles définies
     *
     * Contexte :
     * - Utilisé dans la méthode de création des candidats
     */
    private static boolean isVowel(char letter) {
        return VOWELS.indexOf(letter) >= ZERO_INT; // VOWELS.indexOf('A')    renvoie : 0
    }
}