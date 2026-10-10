package com.example;

/**
 * Représente un candidat de type Alpha dans le système.
 *
 * Rôle :
 * - Spécialisation de la classe Candidate
 *
 * Fonction :
 * - Ajoute un préfixe spécifique à l'affichage du nom du candidat
 *
 * Contexte :
 * - Utilisé dans l’interface utilisateur pour différencier visuellement
 *   les candidats Alpha des autres types de candidats
 *
 * Design : spécialisation via héritage (polymorphisme)
 * Justification : améliore la cohésion en isolant le comportement d’affichage
 */
public class AlphaCandidate extends Candidate {

    // Préfixe d’affichage spécifique aux candidats Alpha
    private static final String ALPHA_PREFIX = "+Alpha+ ";

    /**
     * Constructeur d’un candidat Alpha.
     *
     * Rôle :
     * - Initialiser un candidat de type Alpha avec ses attributs métier
     *
     * Fonction :
     * - Délègue l’initialisation des attributs à la classe mère Candidate
     *
     * Contexte :
     * - Utilisé lors de la création d’un candidat Alpha dans le système
     *
     * Design :
     * - Héritage (spécialisation de Candidate)
     * - Justification : améliore la cohésion en réutilisant la logique commune
     *   d’initialisation définie dans la classe parente
     */
    public AlphaCandidate(String firstName,
                        int distanceKm,
                        String motivation,
                        String imagePath) {

        super(firstName, distanceKm, motivation, imagePath);
    }

    /**
     * Retourne le nom formaté pour l’affichage UI.
     *
     * Fonction :
     * - Ajoute un préfixe Alpha au prénom du candidat
     *
     * Retour :
     * - Chaîne de caractères représentant le nom affiché
     */
    @Override
    public String getDisplayName() {
        return getFirstName() + " " + ALPHA_PREFIX;
    }
}