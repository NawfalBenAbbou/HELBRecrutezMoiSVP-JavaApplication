package com.example;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Référentiel singleton des mots-clés du système.
 *
 * Rôle :
 * - Centraliser le chargement et l’accès aux mots-clés métier
 *
 * Fonction :
 * - Charge les mots-clés depuis le fichier DATA/keywords.txt
 * - Conserve les données en mémoire durant l’exécution
 *
 * Contexte :
 * - Utilisé par le moteur de scoring et de sélection des candidats
 *
 * Design :
 * - Pattern Singleton
 * - Justification : améliore la cohésion en centralisant
 *   une source unique de mots-clés partagés
 */
public final class KeywordRepository {

    // =========================
    // Constantes
    // =========================

    private static final String KEYWORDS_PATH =
            "DATA/keywords.txt";

    // =========================
    // Singleton
    // =========================

    private static final KeywordRepository INSTANCE =
            new KeywordRepository();

    // =========================
    // Attributs
    // =========================

    private final List<String> keywords;

    // =========================
    // Constructeur
    // =========================

    /**
     * Initialise le référentiel des mots-clés.
     *
     * Rôle :
     * - Charger les mots-clés une seule fois
     *
     * Fonction :
     * - Lit le fichier de mots-clés
     * - Stocke les données en mémoire
     *
     * Contexte :
     * - Appelé automatiquement lors de
     *   l’initialisation du Singleton
     *
     * Design :
     * - Chargement unique partagé par toute l’application
     * - Réduit les accès disque inutiles
     */
    private KeywordRepository() {

        KeywordsFileReader reader =
                new KeywordsFileReader(
                        new File(KEYWORDS_PATH)
                );

        this.keywords = reader.readKeywords();
    }

    /**
     * Retourne l’instance unique du repository.
     *
     * Rôle :
     * - Fournir un point d’accès global contrôlé
     *
     * Fonction :
     * - Retourne l’unique instance du repository
     *
     * Contexte :
     * - Utilisé dans les services métier du système
     *
     * Design :
     * - Pattern Singleton
     * - Garantit une unique source de données
     */
    public static KeywordRepository getInstance() {//une instance du fichier pour les mots et on partage le fichier 
        return INSTANCE;
    }

    /**
     * Retourne la liste des mots-clés en lecture seule.
     *
     * Rôle :
     * - Exposer les mots-clés du système
     *
     * Fonction :
     * - Retourne une vue non modifiable de la liste
     *
     * Contexte :
     * - Utilisé par les algorithmes de scoring
     *
     * Design :
     * - Protection de l’état interne du repository
     * - Réduit le couplage avec les consommateurs
     */
    public List<String> getKeywords() {

        return Collections.unmodifiableList(
                keywords
        );
    }
}