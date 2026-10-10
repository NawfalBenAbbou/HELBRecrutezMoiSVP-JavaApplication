package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Lecteur de dossier candidat.
 *
 * Rôle :
 * - Lire les données brutes d’un candidat depuis le dossier DATA/
 *
 * Fonction :
 * - Charge les fichiers de motivation, distance et image
 * - Délègue la création du candidat à ProfileTypeFactory
 *
 * Contexte :
 * - Utilisé lors du chargement initial des candidats du système
 *
 * Design :
 * - Respecte le principe SRP (Single Responsibility Principle)
 * - Favorise la cohésion en isolant la lecture de fichiers
 * - Utilise GRASP Creator via ProfileTypeFactory
 */
public final class CandidateFolderReader {

    // =========================
    // Constantes fichiers
    // =========================

    private static final String MOTIVATION_FILE =
            "motivation.txt";

    private static final String DISTANCE_FILE =
            "distance.dist";

    private static final String IMAGE_FILE =
            "image.png";

    // =========================
    // Constantes métier
    // =========================

    private static final int DEFAULT_DISTANCE = 200;

    // =========================
    // Constantes texte
    // =========================

    private static final String EMPTY_TEXT = "";
    private static final String SPACE_TEXT = " ";

    // =========================
    // Attributs
    // =========================

    private final File candidateFolder;

    /**
     * Constructeur du lecteur de dossier candidat.
     *
     * Rôle :
     * - Initialiser le dossier contenant les données du candidat
     *
     * Fonction :
     * - Stocke la référence vers le dossier à analyser
     *
     * Contexte :
     * - Utilisé lors du chargement des candidats depuis DATA/
     *
     * Design :
     * - Injection de dépendance simple pour limiter le couplage
     */
    public CandidateFolderReader(File candidateFolder) {
        this.candidateFolder = candidateFolder;
    }

    /**
     * Lit un dossier candidat et construit un objet Candidate.
     *
     * Rôle :
     * - Centraliser la lecture complète des données candidat
     *
     * Fonction :
     * - Charge distance, motivation et image
     * - Délègue ensuite la création à ProfileTypeFactory
     *
     * Contexte :
     * - Utilisé par le système de chargement des candidats
     *
     * Design :
     * - Respecte GRASP Creator
     * - Améliore la cohésion en séparant lecture et création
     */
    public Candidate readCandidate(String firstName) {

        int distance = readDistance();

        String motivation = readMotivation();

        String imagePath = readImagePath();

        return ProfileTypeFactory.createCandidate(
                firstName,
                distance,
                motivation,
                imagePath
        );
    }

    // =========================
    // Lecture fichiers
    // =========================

    /**
     * Lit la distance du candidat depuis le fichier dédié.
     *
     * Rôle :
     * - Charger la distance géographique du candidat
     *
     * Fonction :
     * - Lit le fichier distance.dist
     * - Retourne une valeur par défaut en cas d’erreur
     *
     * Contexte :
     * - Utilisé lors du chargement métier du candidat
     *
     * Design :
     * - Gestion défensive des erreurs de lecture
     */
    private int readDistance() {

        File file = new File(
                candidateFolder,
                DISTANCE_FILE
        );

        if (!file.exists()) {
            return DEFAULT_DISTANCE;
        }

        try (
                BufferedReader reader =//lire ligne par ligne 
                        new BufferedReader(
                                new FileReader(file)
                        )
        ) {

            String line = reader.readLine();//donne  : ligne ="25" puis conversion en int

            return Integer.parseInt(
                    line.trim()
            );

        } catch (Exception e) {

            /*
             Valeur par défaut utilisée lorsque
             le fichier est invalide ou illisible.
            */
            return DEFAULT_DISTANCE;
        }
    }

    /**
     * Lit la motivation du candidat.
     *
     * Rôle :
     * - Charger le texte de motivation
     *
     * Fonction :
     * - Concatène les lignes non vides du fichier motivation.txt
     *
     * Contexte :
     * - Utilisé pour le scoring par mots-clés
     *
     * Design :
     * - Nettoyage léger des données lors du chargement
     */
    private String readMotivation() {

        File file = new File(
                candidateFolder,
                MOTIVATION_FILE
        );

        if (!file.exists()) {
            return EMPTY_TEXT;
        }

        StringBuilder sb = new StringBuilder();//construire chaine progressivement

        try {

            List<String> lines =//lire lignes
                    Files.readAllLines(
                            file.toPath()
                    );

            /*
             Les lignes vides sont ignorées
             afin d’obtenir une motivation propre
             pour le moteur de scoring.
            */
            for (String line : lines) {

                if (!line.isBlank()) {

                    sb.append(line)
                            .append(SPACE_TEXT);
                }
            }

        } catch (IOException e) {

            return EMPTY_TEXT;
        }

        return sb.toString().trim();
    }

    /**
     * Retourne le chemin absolu de l’image candidat.
     *
     * Rôle :
     * - Fournir l’accès à l’image du candidat
     *
     * Fonction :
     * - Retourne le chemin absolu du fichier image
     *
     * Contexte :
     * - Utilisé dans l’interface JavaFX
     *
     * Design :
     * - Retourne une chaîne vide si l’image est absente
     */
    private String readImagePath() {

        File file = new File(
                candidateFolder,
                IMAGE_FILE
        );

        if (!file.exists()) {
            return EMPTY_TEXT;
        }

        return file.getAbsolutePath();
    }
}