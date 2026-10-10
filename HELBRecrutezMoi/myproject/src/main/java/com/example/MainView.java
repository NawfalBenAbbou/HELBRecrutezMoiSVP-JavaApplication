package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.util.Observer;

/**
 * Main JavaFX view of the recruiter application.
 *
 * Rôle :
 * - Interface principale de sélection des candidats
 * - Affichage des matchs et de l’historique de session
 *
 * Fonction :
 * - Construction complète de l’interface JavaFX
 * - Affichage dynamique des données de session
 * - Réaction aux changements via le pattern Observer
 *
 * Contexte :
 * - Couche View du pattern MVC
 * - Point d’entrée visuel de l’application
 */
public class MainView extends Application implements Observer {

    private RecruiterSession session;
    private MainController controller;

    // ================= CONSTANTES =================

    private static final int IMAGE_WIDTH = 220;
    private static final int IMAGE_HEIGHT = 250;

    private static final int CARD_WIDTH = 260;

    private static final int SPACING_SMALL = 10;
    private static final int SPACING_MEDIUM = 15;
    private static final int SPACING_LARGE = 40;

    private static final int PADDING_STANDARD = 20;
    private static final int HISTORY_HEIGHT = 180;

    private static final int SCENE_WIDTH = 1200;
    private static final int SCENE_HEIGHT = 850;

    private static final int DETAILS_WIDTH = 400;
    private static final int DETAILS_HEIGHT = 500;

    private static final String APP_TITLE = "Recruiter App";
    private static final String TITLE_TEXT = "Recruiter Session";
    private static final String MODE_TEXT = "Mode";
    private static final String HISTORY_TEXT = "History";

    private static final String CANDIDATE_A_TEXT = "Candidate A";
    private static final String CANDIDATE_B_TEXT = "Candidate B";

    private static final String RANDOM_TEXT = "Random";
    private static final String LOCAL_TEXT = "Local (+)";
    private static final String SKILL_TEXT = "Skill (-)";
    private static final String ANY_TEXT = "Any";

    private static final String RETAIN_TEXT = "Retain";
    private static final String SUBMIT_TEXT = "Submit Session";

    private static final String SCORE_TEXT = "Current Score : ";
    private static final String BONUS_TEXT = "Bonus : ";
    private static final String WORKING_RATE_TEXT = "Working Rate : ";

    private static final String SESSION_FINISHED_TITLE = "Session finished";
    private static final String SESSION_FINISHED_MSG =
            "No more candidates available. Generating report...";

    private static final String DETAILS_TITLE = "Candidate Details";

    private static final String STYLE_TITLE =
            "-fx-font-size: 28;-fx-font-weight: bold;";

    private static final String STYLE_LABEL_BOLD =
            "-fx-font-size: 18;-fx-font-weight: bold;";

    private static final String STYLE_SMALL_LABEL =
            "-fx-font-size: 16;";

    private static final String STYLE_BUTTON =
            "-fx-font-size: 16;-fx-background-radius: 20;-fx-padding: 10;";

    private static final String STYLE_SUBMIT_BUTTON =
            "-fx-font-size: 16;-fx-background-radius: 20;-fx-padding: 12 20 12 20;";

    private static final String STYLE_PANEL =
            "-fx-background-color: white;-fx-border-color: #d1d5db;-fx-border-radius: 20;-fx-background-radius: 20;";

    private static final String STYLE_BACKGROUND =
            "-fx-background-color: #f8fafc;";

    private static final String STYLE_SCORE_BOX =
            "-fx-border-color: #d1d5db;" +
            "-fx-border-radius: 15;" +
            "-fx-background-radius: 15;";    

    private static final String STYLE_LEFT_CARD =
            "-fx-background-color: #dbeafe;-fx-background-radius: 25;";

    private static final String STYLE_RIGHT_CARD =
            "-fx-background-color: #ffe4e6;-fx-background-radius: 25;";

    private static final String COLOR_LEFT = "LIGHTBLUE";
    private static final String COLOR_RIGHT = "LIGHTPINK";

    private static final String SCORE_FORMAT = "%.2f";

    // ================= UI =================

    private Label scoreLabel;
    private Label bonusLabel;
    private Label workingRateLabel;

    private VBox historyContainer;

    private RadioButton randomMode;
    private RadioButton localMode;
    private RadioButton skillMode;
    private RadioButton anyMode;

    private Button retainLeftBtn;
    private Button retainRightBtn;
    private Button submitButton;

    private Label leftCandidateLabel;
    private Label rightCandidateLabel;

    private ImageView leftImage;
    private ImageView rightImage;

    private Match currentMatch;

    public MainView() {
    }
    /**
     * Point d’entrée JavaFX.
     *
     * Rôle :
     * - Initialise la session de recrutement
     * - Connecte le controller à la view
     *
     * Fonction :
     * - Crée la session métier
     * - Attache l’Observer (View) à la session
     * - Construit et affiche la scène principale
     * - Lance le premier match
     *
     * Paramètre :
     * - stage : fenêtre principale JavaFX
     */
    @Override
    public void start(Stage stage) {

        this.session = new RecruiterSession();
        this.controller = new MainController(session);

        session.attach(this);

        Scene scene = buildScene();

        stage.setTitle(APP_TITLE);
        stage.setScene(scene);
        stage.show();

        generateNewMatch();
    }

    /**
     * Construit la scène principale de l’application.
     *
     * Rôle :
     * - Assemble toute l’interface utilisateur
     *
     * Fonction :
     * - Création des zones UI (top, center, right, bottom)
     * - Initialisation des composants graphiques
     *
     * Retour :
     * - Scene JavaFX complète
     */
    public Scene buildScene() {

        Label title = new Label(TITLE_TEXT);
        title.setStyle(STYLE_TITLE);

        HBox topBar = new HBox(title);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(PADDING_STANDARD));

        leftImage = new ImageView();
        leftImage.setFitWidth(IMAGE_WIDTH);
        leftImage.setFitHeight(IMAGE_HEIGHT);
        leftImage.setPreserveRatio(true);

        leftCandidateLabel = new Label(CANDIDATE_A_TEXT);
        leftCandidateLabel.setStyle(STYLE_LABEL_BOLD);

        VBox leftCard = new VBox(
                SPACING_MEDIUM,
                leftImage,
                leftCandidateLabel
        );

        leftCard.setAlignment(Pos.CENTER);
        leftCard.setPadding(new Insets(PADDING_STANDARD));
        leftCard.setPrefWidth(CARD_WIDTH);
        leftCard.setStyle(STYLE_LEFT_CARD);

        retainLeftBtn = new Button(RETAIN_TEXT);
        retainLeftBtn.setPrefWidth(CARD_WIDTH);
        retainLeftBtn.setStyle(STYLE_BUTTON);

        VBox leftColumn = new VBox(
                SPACING_MEDIUM,
                leftCard,
                retainLeftBtn
        );

        leftColumn.setAlignment(Pos.CENTER);

        rightImage = new ImageView();
        rightImage.setFitWidth(IMAGE_WIDTH);
        rightImage.setFitHeight(IMAGE_HEIGHT);
        rightImage.setPreserveRatio(true);

        rightCandidateLabel = new Label(CANDIDATE_B_TEXT);
        rightCandidateLabel.setStyle(STYLE_LABEL_BOLD);

        VBox rightCard = new VBox(
                SPACING_MEDIUM,
                rightImage,
                rightCandidateLabel
        );

        rightCard.setAlignment(Pos.CENTER);
        rightCard.setPadding(new Insets(PADDING_STANDARD));
        rightCard.setPrefWidth(CARD_WIDTH);
        rightCard.setStyle(STYLE_RIGHT_CARD);

        retainRightBtn = new Button(RETAIN_TEXT);
        retainRightBtn.setPrefWidth(CARD_WIDTH);
        retainRightBtn.setStyle(STYLE_BUTTON);

        VBox rightColumn = new VBox(
                SPACING_MEDIUM,
                rightCard,
                retainRightBtn
        );

        rightColumn.setAlignment(Pos.CENTER);

        leftImage.setOnMouseClicked(e ->
                openDetails(currentMatch.getCandidateA())
        );

        rightImage.setOnMouseClicked(e ->
                openDetails(currentMatch.getCandidateB())
        );

        HBox candidatesBox = new HBox(
                SPACING_LARGE,
                leftColumn,
                rightColumn
        );

        candidatesBox.setAlignment(Pos.CENTER);

        ToggleGroup group = new ToggleGroup();

        randomMode = new RadioButton(RANDOM_TEXT);
        localMode = new RadioButton(LOCAL_TEXT);
        skillMode = new RadioButton(SKILL_TEXT);
        anyMode = new RadioButton(ANY_TEXT);

        randomMode.setToggleGroup(group);
        localMode.setToggleGroup(group);
        skillMode.setToggleGroup(group);
        anyMode.setToggleGroup(group);

        randomMode.setSelected(true);

        randomMode.setOnAction(e ->
                controller.setMode(StrategyFactory.Mode.RANDOM)
        );

        localMode.setOnAction(e ->
                controller.setMode(StrategyFactory.Mode.LOCAL)
        );

        skillMode.setOnAction(e ->
                controller.setMode(StrategyFactory.Mode.SKILL)
        );

        anyMode.setOnAction(e ->
                controller.setMode(StrategyFactory.Mode.ANY)
        );

        workingRateLabel = new Label(WORKING_RATE_TEXT + "0");
        workingRateLabel.setStyle(STYLE_SMALL_LABEL);

        VBox modeBox = new VBox(
                SPACING_MEDIUM,
                new Label(MODE_TEXT),
                randomMode,
                localMode,
                skillMode,
                anyMode,
                new Separator(),
                workingRateLabel
        );

        modeBox.setAlignment(Pos.TOP_CENTER);
        modeBox.setPadding(new Insets(PADDING_STANDARD));
        modeBox.setStyle(STYLE_PANEL);

        historyContainer = new VBox(SPACING_SMALL);
        historyContainer.setPadding(new Insets(SPACING_SMALL));

        ScrollPane historyScroll = new ScrollPane(historyContainer);
        historyScroll.setFitToWidth(true);
        historyScroll.setPrefHeight(HISTORY_HEIGHT);

        VBox historyBox = new VBox(
                SPACING_SMALL,
                new Label(HISTORY_TEXT),
                historyScroll
        );

        historyBox.setPadding(new Insets(PADDING_STANDARD));
        historyBox.setStyle(STYLE_PANEL);

        scoreLabel = new Label(SCORE_TEXT + "0");
        scoreLabel.setStyle(STYLE_SMALL_LABEL);

        bonusLabel = new Label(BONUS_TEXT + "0");
        bonusLabel.setStyle(STYLE_SMALL_LABEL);

        VBox scoreBox = new VBox(scoreLabel);
        scoreBox.setPadding(new Insets(SPACING_MEDIUM));
        scoreBox.setStyle(STYLE_SCORE_BOX);

        VBox bonusBox = new VBox(bonusLabel);
        bonusBox.setPadding(new Insets(SPACING_MEDIUM));
        bonusBox.setStyle(STYLE_SCORE_BOX);

        submitButton = new Button(SUBMIT_TEXT);
        submitButton.setStyle(STYLE_SUBMIT_BUTTON);

        submitButton.setOnAction(e -> {

            SessionReportWriter.write(
                    session.getHistory(),
                    session.getCurrentScore(),
                    session.getBonusScore()
            );

            Stage currentStage =
                    (Stage) submitButton.getScene().getWindow();

            currentStage.close();
        });

        HBox bottomBar = new HBox(
                SPACING_MEDIUM,
                scoreBox,
                bonusBox,
                submitButton
        );

        bottomBar.setAlignment(Pos.CENTER_LEFT);

        retainLeftBtn.setOnAction(e -> resolve(true));
        retainRightBtn.setOnAction(e -> resolve(false));

        BorderPane root = new BorderPane();

        root.setTop(topBar);
        root.setCenter(candidatesBox);
        root.setRight(modeBox);

        root.setBottom(new VBox(
                SPACING_MEDIUM,
                historyBox,
                bottomBar
        ));

        BorderPane.setMargin(candidatesBox, new Insets(PADDING_STANDARD));
        BorderPane.setMargin(modeBox, new Insets(PADDING_STANDARD));
        BorderPane.setMargin(bottomBar, new Insets(PADDING_STANDARD));
        BorderPane.setMargin(historyBox, new Insets(PADDING_STANDARD));

        root.setStyle(STYLE_BACKGROUND);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        update(session);

        return scene;
    }

    /**
     * Ouvre une fenêtre de détails pour un candidat sélectionné.
     *
     * Rôle :
     * - Affiche une vue secondaire dédiée au candidat
     *
     * Fonction :
     * - Crée un controller spécifique
     * - Construit une vue indépendante
     * - Ouvre une nouvelle fenêtre JavaFX
     *
     * Paramètre :
     * - candidate : candidat sélectionné
     */
    private void openDetails(Candidate candidate) {

        CandidateDetailsController controller =
                new CandidateDetailsController(session, candidate);

        CandidateDetailsView view =
                new CandidateDetailsView(controller, currentMatch);

        view.setCandidate(controller.getCandidate());

        Stage stage = new Stage();

        Scene scene = new Scene(
                view.getRoot(),//tout le truc du details
                DETAILS_WIDTH,
                DETAILS_HEIGHT
        );

        stage.setScene(scene);
        stage.setTitle(DETAILS_TITLE);

        stage.show();
        // Correction: stage.initOwner(...); stage.initModality(Modality.WINDOW_MODAL); stage.showAndWait()
    }
        // ================= MATCH =================

        /**
        * Génère un nouveau match de candidats et met à jour l'interface.
        *
        * Rôle :
        * - Orchestrer la création d’un nouveau match
        * - Mettre à jour la vue avec les candidats sélectionnés
        *
        * Fonction :
        * - Vérifie si un match peut être généré (fin de session)
        * - Récupère un match depuis le controller
        * - Met à jour les labels UI (noms + boutons)
        * - Charge les images associées aux candidats
        *
        * Logique :
        * - Si aucun match disponible, affiche une popup de fin et arrête l’exécution
        */
    private void generateNewMatch() {

        if (!controller.canGenerateMatch()) {
            showEndPopup();
            return;
        }

        currentMatch = controller.generateMatch();

        Candidate a = currentMatch.getCandidateA();
        Candidate b = currentMatch.getCandidateB();

        leftCandidateLabel.setText(a.getFirstName());
        rightCandidateLabel.setText(b.getFirstName());

        retainLeftBtn.setText(RETAIN_TEXT + " " + a.getFirstName());
        retainRightBtn.setText(RETAIN_TEXT + " " + b.getFirstName());

        loadImage(leftImage, a.getImagePath());
        loadImage(rightImage, b.getImagePath());
    }
        /**
         * Charge et affiche une image dans un composant ImageView.
         *
         * Rôle :
         * - Transformer un chemin fichier en image JavaFX affichable
         *
         * Fonction :
         * - Convertit un chemin local en URL exploitable par JavaFX
         * - Crée une instance Image
         * - Injecte l’image dans l’ImageView cible
         *
         * Paramètres :
         * - imageView : composant graphique cible
         * - path : chemin du fichier image sur le système
         *
         * Remarque :
         * - Aucun contrôle d'existence de fichier (supposé valide dans le flux métier)
         */
    private void loadImage(ImageView imageView, String path) {

        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }
        /**
         * Résout un match en enregistrant le candidat sélectionné.
         *
         * Rôle :
         * - Déterminer le gagnant du match courant
         * - Transmettre la sélection au controller
         *
         * Fonction :
         * - Choisit le candidat gauche ou droite selon le paramètre
         * - Envoie la décision au controller métier
         * - Déclenche immédiatement la génération d’un nouveau match
         *
         * Paramètre :
         * - pickLeft : true = candidat gauche sélectionné, false = candidat droit
         */
    private void resolve(boolean pickLeft) {

        Candidate selected;

        if (pickLeft) {
            selected = currentMatch.getCandidateA();
        } else {
            selected = currentMatch.getCandidateB();
        }

        controller.resolveMatch(currentMatch, selected);
        generateNewMatch();
    }
        // ================= OBSERVER =================

       /**
         * Met à jour l'interface en fonction de l'état de la session (Observer).
         *
         * Rôle :
         * - Synchroniser la vue avec les données du modèle (session)
         *
         * Fonction :
         * - Met à jour les scores (score, bonus, working rate)
         * - Vide et reconstruit l'historique affiché
         * - Colorie les entrées selon le candidat sélectionné
         *
         * Paramètre :
         * - session : état actuel de la session de recrutement
         *
         * Logique :
         * - Chaque entrée d’historique est transformée en ligne UI
         * - Couleur dépend du candidat sélectionné dans le match
         */
    @Override
    public void update(RecruiterSession session) {

        scoreLabel.setText(SCORE_TEXT + session.getCurrentScore());
        bonusLabel.setText(BONUS_TEXT + session.getBonusScore());

        workingRateLabel.setText(
                WORKING_RATE_TEXT +
                        String.format(SCORE_FORMAT, session.getWorkingRate())
        );
        // probleme qst 22 Correction: String.format("%.1f", session.getWorkingRate())

        historyContainer.getChildren().clear();

        for (MatchHistoryEntry entry : session.getHistory()) {

            Rectangle colorBox = new Rectangle(SPACING_MEDIUM, SPACING_MEDIUM);
            Candidate selected = entry.getSelectedCandidate();

            if (selected.equals(entry.getMatch().getCandidateA())) {
                colorBox.setFill(Color.valueOf(COLOR_LEFT));
            } else {
                colorBox.setFill(Color.valueOf(COLOR_RIGHT));
            }

            Label text = new Label(entry.formatForReport());

            HBox line = new HBox(SPACING_MEDIUM, colorBox, text);
            line.setAlignment(Pos.CENTER_LEFT);

            historyContainer.getChildren().add(line);           //add(0, line);
        }
    }
        /**
         * Affiche une popup indiquant la fin de session.
         *
         * Rôle :
         * - Informer l'utilisateur qu'il n'y a plus de candidats disponibles
         *
         * Fonction :
         * - Crée une alerte JavaFX de type INFORMATION
         * - Définit titre et message de fin de session
         * - Déclenche la finalisation de session à la fermeture de la popup
         */
    private void showEndPopup() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SESSION_FINISHED_TITLE);
        alert.setHeaderText("");
        alert.setContentText(SESSION_FINISHED_MSG);

        alert.setOnHidden(e -> finishSession());
        alert.show();
    }
        /**
         * Finalise la session de recrutement.
         *
         * Rôle :
         * - Sauvegarder les résultats de session
         * - Fermer l’application proprement
         *
         * Fonction :
         * - Écrit le rapport de session dans un fichier
         * - Récupère la fenêtre courante et la ferme
         *
         * Dépendances :
         * - SessionReportWriter : génération du rapport final
         */
    private void finishSession() {

        SessionReportWriter.write(
                session.getHistory(),
                session.getCurrentScore(),
                session.getBonusScore()
        );

        Stage stage = (Stage) scoreLabel.getScene().getWindow();
        stage.close();
    }
}