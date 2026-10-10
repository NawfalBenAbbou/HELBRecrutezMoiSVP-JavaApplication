package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

/**
 * Lecteur des mots-clés du système.
 *
 * Rôle :
 * - Lire les mots-clés depuis le fichier keywords.txt
 *
 * Fonction :
 * - Charge les mots-clés ligne par ligne
 * - Nettoie les espaces inutiles
 * - Ignore les lignes vides
 *
 * Contexte :
 * - Utilisé par KeywordRepository lors du chargement initial
 *
 * Design :
 * - Respecte le principe SRP (Single Responsibility Principle)
 * - Justification : améliore la cohésion en isolant
 *   la logique de lecture des fichiers
 */
public final class KeywordsFileReader {

    // =========================
    // Attributs
    // =========================

    private final File keywordsFile;

    // =========================
    // Constructeur
    // =========================

    /**
     * Initialise le lecteur de mots-clés.
     *
     * Rôle :
     * - Associer un fichier source au lecteur
     *
     * Fonction :
     * - Stocke le fichier contenant les mots-clés
     *
     * Contexte :
     * - Utilisé par KeywordRepository
     *
     * Paramètres :
     * - keywordsFile : fichier contenant les mots-clés
     */
    public KeywordsFileReader(
            File keywordsFile
    ) {

        this.keywordsFile = keywordsFile;
    }

    // =========================
    // Lecture fichier
    // =========================

    /**
     * Lit tous les mots-clés du fichier.
     *
     * Rôle :
     * - Charger les mots-clés métier du système
     *
     * Fonction :
     * - Lit le fichier ligne par ligne
     * - Supprime les espaces inutiles
     * - Ignore les lignes vides
     *
     * Retour :
     * - Liste des mots-clés valides
     *
     * Contexte :
     * - Utilisé au démarrage du système
     *
     * Design :
     * - Encapsulation de la logique de lecture fichier
     */
    public List<String> readKeywords() {

        List<String> keywords =
                new ArrayList<>();

        /*
         Retour immédiat si le fichier
         n’existe pas afin d’éviter
         une exception de lecture.
        */
        if (!keywordsFile.exists()) {
            return keywords;
        }

        try {

            List<String> lines =
                    Files.readAllLines(
                            keywordsFile.toPath()
                    );

            for (String line : lines) {

                String cleaned =
                        line.trim();

                /*
                 Les lignes vides sont ignorées
                 pour éviter des mots-clés invalides.
                */
                if (!cleaned.isEmpty()) {
                    keywords.add(cleaned);
                }
            }

        } catch (IOException e) {

            /*
             Retourne une liste vide en cas
             d’erreur de lecture afin de garder
             un comportement stable du système.
            */
            return new ArrayList<>();
        }

        return keywords;
    }
}