package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Vue de détail d’un candidat dans l’interface JavaFX.
 *
 * Rôle :
 * - Afficher les informations d’un candidat
 *
 * Fonction :
 * - Présenter motivation, distance et formulaire de prédiction
 * - Permettre la validation d’un score estimé
 *
 * Contexte :
 * - Utilisée dans l’écran de détail d’un match candidat
 *
 * Design :
 * - Vue JavaFX découplée du contrôleur
 * - Favorise la cohésion via séparation UI / logique métier
 */
public class CandidateDetailsView {

    // =========================
    // Constantes numériques
    // =========================

    private static final int SPACING_MAIN = 20;

    private static final int PADDING_TOP = 10;
    private static final int PADDING_HORIZONTAL = 20;
    private static final int PADDING_BOTTOM = 10;

    private static final int FONT_TITLE_SIZE = 26;
    private static final int FONT_HINT_SIZE = 12;

    private static final int INPUT_SPACING = 8;
    private static final int INPUT_MAX_WIDTH = 120;

    private static final int BORDER_RADIUS = 25;

    private static final int MIN_SCORE_VALUE = 0;
    private static final int MAX_SCORE_VALUE = 100;

    // =========================
    // Constantes texte
    // =========================

    private static final String COLOR_GREY = "#666";

    private static final String BACKGROUND_STYLE =
            "-fx-background-color: white;";

    private static final String BORDER_RADIUS_STYLE =
            "-fx-background-radius: ";

    private static final String PIXEL_SUFFIX = ";";

    private static final String TITLE_STYLE =
            "-fx-font-size: ";

    private static final String TITLE_BOLD_STYLE =
            "; -fx-font-weight: bold;";

    private static final String HINT_STYLE_PREFIX =
            "-fx-font-size: ";

    private static final String HINT_STYLE_COLOR =
            "; -fx-text-fill: ";

    private static final String CANDIDATE_TEXT = "Candidate";

    private static final String SCORE_RANGE_TEXT = "0 - 100";

    private static final String SCORE_LABEL_TEXT =
            "Guess the score [0 : 100]";

    private static final String BONUS_RULE_TEXT =
            "Bonus : ±15% error margin → 20% bonus";

    private static final String BUTTON_TEXT =
            "Confirm the guess";

    private static final String MOTIVATION_PREFIX =
            "Motivation : ";

    private static final String DISTANCE_PREFIX =
            "Distance : ";

    private static final String KM_SUFFIX =
            " km";

    // =========================
    // Attributs
    // =========================

    private final VBox root;

    private final Label nameLabel;
    private final Label motivationLabel;
    private final Label distanceLabel;
    private final Label bonusRuleLabel;

    private final TextField guessField;

    private final Button confirmButton;

    private final CandidateDetailsController controller;
    private final Match match;

    /**
     * Constructeur de la vue détail candidat.
     *
     * Rôle :
     * - Initialiser tous les composants graphiques de la vue
     *
     * Fonction :
     * - Configure l’interface JavaFX et les interactions utilisateur
     *
     * Contexte :
     * - Utilisé lors de l’ouverture de la fenêtre détail candidat
     *
     * Design :
     * - Séparation UI / logique métier via contrôleur dédié
     * - Améliore la cohésion des responsabilités
     */
    public CandidateDetailsView(
            CandidateDetailsController controller,
            Match match
    ) {

        this.controller = controller;
        this.match = match;

        root = new VBox(SPACING_MAIN);

        root.setPadding(
                new Insets(//marge
                        PADDING_TOP,
                        PADDING_HORIZONTAL,
                        PADDING_BOTTOM,
                        PADDING_HORIZONTAL
                )
        );

        root.setAlignment(Pos.CENTER);//centre element

        root.setStyle(
                BACKGROUND_STYLE
                        + BORDER_RADIUS_STYLE
                        + BORDER_RADIUS
                        + PIXEL_SUFFIX
        );

        // ================= NAME =================

        nameLabel = new Label(CANDIDATE_TEXT);

        nameLabel.setStyle(
                TITLE_STYLE
                        + FONT_TITLE_SIZE
                        + TITLE_BOLD_STYLE
        );

        // ================= TEXTS =================

        motivationLabel = new Label();
        motivationLabel.setWrapText(true);

        distanceLabel = new Label();

        bonusRuleLabel = new Label(BONUS_RULE_TEXT);

        bonusRuleLabel.setStyle(
                HINT_STYLE_PREFIX
                        + FONT_HINT_SIZE
                        + HINT_STYLE_COLOR
                        + COLOR_GREY
                        + PIXEL_SUFFIX
        );

        // ================= INPUT =================

        Label inputLabel = new Label(SCORE_LABEL_TEXT);

        guessField = new TextField();
        guessField.setPromptText(SCORE_RANGE_TEXT);
        guessField.setMaxWidth(INPUT_MAX_WIDTH);

        confirmButton = new Button(BUTTON_TEXT);
        confirmButton.setDisable(true);

        /*
         Validation temps réel :
         le bouton reste désactivé tant que la saisie
         ne respecte pas les contraintes métier.
        */
        guessField.textProperty().addListener((obs, oldV, newV) -> {//chaque fois que luser mets un truc le listener se declenche 
            confirmButton.setDisable(!isValid(newV));
            //obs = propriété observée
                //oldV = ancien texte
                //newV = nouveau texte
        });

        /*
         Lors de la validation :
         - récupération de la prédiction
         - calcul éventuel du bonus
         - fermeture de la fenêtre
        */
        confirmButton.setOnAction(e -> {

            int prediction = getGuessValue();

            int realScore = match.getScoreA();
            int delta = match.getDelta();

            controller.applyBonus(
                    prediction,
                    realScore,
                    delta
            );

            confirmButton.setDisable(true);//une tentative

            root.getScene().getWindow().hide();
        });

        VBox inputBox = new VBox(
                INPUT_SPACING,
                inputLabel,
                guessField,
                confirmButton
        );

        inputBox.setAlignment(Pos.CENTER);

        // ================= LAYOUT =================

        root.getChildren().addAll(
                nameLabel,
                motivationLabel,
                distanceLabel,
                bonusRuleLabel,
                inputBox
        );
    }

    // =========================
    // Validation
    // =========================

    /**
     * Vérifie si une valeur saisie est valide.
     *
     * Rôle :
     * - Valider l’entrée utilisateur avant activation du bouton
     *
     * Fonction :
     * - Vérifie que la chaîne est numérique et dans les bornes autorisées
     *
     * Contexte :
     * - Utilisée en temps réel lors de la saisie
     *
     * Design :
     * - Encapsulation de la logique de validation UI
     */
    private boolean isValid(String value) {

        value = value.trim();

        if (value.isEmpty()) {
            return false;
        }

        try {

            int v = Integer.parseInt(value);//transforme en int 

            return v >= MIN_SCORE_VALUE
                    && v <= MAX_SCORE_VALUE;

        } catch (Exception e) {

            return false;
        }
    }


    /**
     * Retourne la racine JavaFX de la vue.
     *
     * Rôle :
     * - Exposer le composant principal de la vue
     *
     * Fonction :
     * - Permet l’intégration dans la scène JavaFX
     *
     * Contexte :
     * - Utilisé par le système de navigation JavaFX
     */
    public VBox getRoot() {//appeller dans le mainview pour afficher detail 
        return root;
    }

    /**
     * Retourne le bouton de confirmation.
     *
     * Rôle :
     * - Fournir un accès externe au bouton principal
     *
     * Fonction :
     * - Permet certains tests ou contrôles UI externes
     *
     * Contexte :
     * - Utilisé pour automatisation ou intégration UI
     */
    public Button getConfirmButton() {
        return confirmButton;
    }

    /**
     * Retourne la valeur saisie par l’utilisateur.
     *
     * Rôle :
     * - Récupérer la prédiction utilisateur
     *
     * Fonction :
     * - Convertit le texte saisi en entier exploitable
     *
     * Contexte :
     * - Utilisé lors de la validation du formulaire
     */
    public int getGuessValue() { //36 aussi isvalid
        return Integer.parseInt(
                guessField.getText().trim()
        );
    }

    /**
     * Met à jour les informations affichées du candidat.
     *
     * Rôle :
     * - Charger les données du candidat dans l’interface
     *
     * Fonction :
     * - Met à jour les labels d’affichage
     *
     * Contexte :
     * - Utilisé lors du changement de candidat sélectionné
     */
    public void setCandidate(Candidate c) {//affiche juste candidat

        nameLabel.setText(
                c.getDisplayName()
        );

        motivationLabel.setText(
                MOTIVATION_PREFIX + c.getMotivation()
        );

        distanceLabel.setText(
                DISTANCE_PREFIX
                        + c.getDistanceKm()
                        + KM_SUFFIX
        );
    }
}