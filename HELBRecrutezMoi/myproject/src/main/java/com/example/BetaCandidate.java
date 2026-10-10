package com.example;

/**
 * Représente un candidat de type Beta dans le système.
 *
 * Rôle :
 * - Spécialisation de la classe Candidate
 *
 * Fonction :
 * - Ajoute un préfixe spécifique à la motivation affichée
 *
 * Contexte :
 * - Utilisé dans l’interface utilisateur pour distinguer les motivations
 *   des candidats Beta des autres types de candidats
 *
 * Design :
 * - Spécialisation via héritage (polymorphisme)
 * - Justification : améliore la cohésion en isolant la règle d’affichage
 */
public class BetaCandidate extends Candidate {

    // Préfixe appliqué à la motivation des candidats Beta
    private static final String BETA_PREFIX = "-Beta- ";

    /**
     * Constructeur d’un candidat Beta.
     *
     * Rôle :
     * - Initialiser un candidat de type Beta avec ses attributs métier
     *
     * Fonction :
     * - Délègue l’initialisation des attributs à la classe mère Candidate
     *
     * Contexte :
     * - Utilisé lors de la création d’un candidat Beta dans le système
     *
     * Design :
     * - Héritage (spécialisation de Candidate)
     * - Justification : améliore la cohésion en réutilisant la logique commune
     *   d’initialisation définie dans la classe parente
     */
    public BetaCandidate(String firstName,
                        int distanceKm,
                        String motivation,
                        String imagePath) {

        super(firstName, distanceKm, motivation, imagePath);
    }

    /**
     * Retourne la motivation formatée pour l'affichage UI.
     *
     * Fonction :
     * - Ajoute un préfixe Beta à la motivation du candidat
     *
     * Retour :
     * - Chaîne de caractères représentant la motivation affichée
     */
    @Override
    public String getMotivation() {
        return BETA_PREFIX + super.getMotivation();
    }
}