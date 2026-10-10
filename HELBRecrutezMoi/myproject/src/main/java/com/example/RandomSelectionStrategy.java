package com.example;

import java.util.List;
import java.util.Random;

/**
 * Stratégie de sélection aléatoire d’un match.
 *
 * Rôle :
 * - Sélectionner deux candidats aléatoirement
 *
 * Fonction :
 * - Tirer deux indices distincts dans la liste de candidats
 * - Calculer leurs scores et créer un Match
 *
 * Contexte :
 * - Utilisée lorsque le mode RANDOM est activé dans l’application
 *
 * Design :
 * - Pattern Strategy + Singleton
 * - Singleton : une seule instance car la stratégie est stateless
 * - Améliore la cohésion en isolant la logique de sélection aléatoire
 * - Réduit le couplage avec le moteur de génération de matchs
 */
public class RandomSelectionStrategy implements MatchSelectionStrategy {

    // =========================
    // INSTANCE UNIQUE (SINGLETON)
    // =========================

    private static final RandomSelectionStrategy INSTANCE =
            new RandomSelectionStrategy();

    private final Random random = new Random();

    // =========================
    // CONSTRUCTEUR PRIVÉ
    // =========================

    private RandomSelectionStrategy() {
    }

    // =========================
    // ACCÈS SINGLETON
    // =========================

    public static RandomSelectionStrategy getInstance() {
        return INSTANCE;
    }

    // =========================
    // STRATEGY METHOD
    // =========================

    /**
     * Sélectionne un match entre deux candidats aléatoires.
     *
     * Rôle :
     * - Générer une paire aléatoire de candidats
     *
     * Fonction :
     * - Choisir deux indices distincts
     * - Calculer les scores des candidats
     * - Construire un objet Match
     *
     * Contexte :
     * - Utilisé dans le mode RANDOM du système de recrutement
     */
    @Override
    public Match selectMatch(List<Candidate> candidates) {

        int size = candidates.size();

        int indexA = random.nextInt(size);//choisis 2 perso 
        int indexB = random.nextInt(size);

        while (indexB == indexA) {
            indexB = random.nextInt(size);// refais si meme
        }
// Candidate a = candidates.get(indexA); Candidate b = candidates.get(indexB); Avec : indexA = 2 indexB = 4 on obtient : a = Charlie b = Emma
        Candidate a = candidates.get(indexA);
        Candidate b = candidates.get(indexB);

        List<String> keywords = KeywordRepository.getInstance().getKeywords();//Récupération des mots-clés

        int scoreA = a.computeScore(keywords);//calcul le score des mots du perso a 
        int scoreB = b.computeScore(keywords);//calcul le score des mots du perso b

        return new Match(a, b, scoreA, scoreB);//les 2 perso et leurs score 
    }
}