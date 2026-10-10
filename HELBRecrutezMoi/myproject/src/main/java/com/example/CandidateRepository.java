package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Repository central des candidats du système.
 *
 * Rôle :
 * - Centraliser le chargement et le stockage des candidats
 *
 * Fonction :
 * - Charge les candidats depuis le dossier DATA
 * - Fournit un accès sécurisé à la liste des candidats
 *
 * Contexte :
 * - Utilisé par les services métier et les stratégies de sélection
 *
 */
public final class CandidateRepository {

    // =========================
    // Constantes
    // =========================

    private static final String DATA_PATH = "DATA";

    private static final CandidateRepository INSTANCE =
            new CandidateRepository();

    // =========================
    // Attributs
    // =========================

    private final List<Candidate> candidates;

    /**
     * Constructeur privé du repository.
     *
     * Rôle :
     * - Empêcher l’instanciation externe
     *
     * Fonction :
     * - Charge automatiquement les candidats au démarrage
     *
     * Contexte :
     * - Utilisé uniquement par le Singleton interne
     *
     * Design :
     * - Pattern Singleton
     * - Garantit une source unique de données candidats
     */
    private CandidateRepository() {

        this.candidates = loadCandidates();
    }

    /**
     * Retourne l’instance unique du repository.
     *
     * Rôle :
     * - Fournir un point d’accès global au repository
     *
     * Fonction :
     * - Retourne l’instance Singleton existante
     *
     * Contexte :
     * - Utilisé par les services métier et contrôleurs
     */
    public static CandidateRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Retourne la liste des candidats.
     *
     * Rôle :
     * - Exposer les candidats chargés dans le système
     *
     * Fonction :
     * - Retourne une vue non modifiable de la liste
     *
     * Contexte :
     * - Utilisé par les stratégies de sélection et le matchmaking
     *
     * Design :
     * - Protection défensive contre les modifications externes
     */
    public List<Candidate> getCandidates() {

        return Collections.unmodifiableList(
                candidates
        );
    }

    // =========================
    // Chargement
    // =========================

    /**
     * Charge tous les candidats présents dans le dossier DATA.
     *
     * Rôle :
     * - Construire la liste complète des candidats valides
     *
     * Fonction :
     * - Parcourt les dossiers candidats
     * - Délègue la lecture à CandidateFolderReader
     * - Filtre les candidats invalides
     *
     * Contexte :
     * - Utilisé au démarrage du repository
     *
     * Design :
     * - Respecte la séparation des responsabilités
     * - CandidateFolderReader gère uniquement la lecture fichier
     * - CandidateValidator gère uniquement la validation métier
     */
    private List<Candidate> loadCandidates() {

        List<Candidate> result =
                new ArrayList<>();

        File dataFolder =
                new File(DATA_PATH);

        /*
         Vérification défensive :
         le système retourne une liste vide
         si le dossier DATA est absent ou invalide.
        */
        if (!dataFolder.exists()
                || !dataFolder.isDirectory()) {

            return result;
        }

        File[] folders =
                dataFolder.listFiles();

        List<File> safeFolders =
                new ArrayList<>();

        /*
         Seuls les dossiers sont conservés
         afin d’éviter le traitement de fichiers parasites.
        */
        for (File folder : folders) {

            if (folder.isDirectory()) {

                safeFolders.add(folder);
            }
        }

        /*
         Chaque dossier valide correspond
         à un candidat du système.
        */
        for (File folder : safeFolders) {

            CandidateFolderReader reader =
                    new CandidateFolderReader(folder);

            /*
             Source unique de vérité :
             toutes les créations de candidats
             passent exclusivement par CandidateFolderReader.
            */
            Candidate candidate =
                    reader.readCandidate(
                            folder.getName()
                    );

            if (CandidateValidator.isValid(candidate)) {

                result.add(candidate);
            }
        }

        return result;
    }
}