package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Responsible for generating the session report file.
 *
 * Role:
 * - Persist match history and final session scores to a text file
 *
 * Function:
 * - Formats each match entry into a human-readable line
 * - Writes current score, bonus score, and final score
 *
 * Context:
 * - Used at the end of a recruiter session when exporting results
 *
 * Design:
 * - Utility class (static only)
 * - Improves cohesion by isolating report generation from business logic and UI
 */
public final class SessionReportWriter {

    // =========================
    // FILE CONFIGURATION
    // =========================

    private static final String FILE_NAME = "session_report.txt";

    // =========================
    // FORMATTING CONSTANTS
    // =========================

    private static final String VS_SEPARATOR = " vs ";
    private static final String SCORE_SEPARATOR = " : ";
    private static final String LINE_BREAK = "\n";
    private static final String DASH_LINE = "----------------------";
    private static final String SELECTED_WRAPPER_OPEN = "(";
    private static final String SELECTED_WRAPPER_CLOSE = ")";

    private static final String CURRENT_SCORE_LABEL = "Current Score : ";
    private static final String BONUS_SCORE_LABEL = "Bonus Score : ";
    private static final String FINAL_SCORE_LABEL = "Final Score : ";

    private SessionReportWriter() {}

    /**
     * Writes the full session report into a text file.
     *
     * Role:
     * - Export session results for persistence and analysis
     *
     * Function:
     * - Iterates over match history
     * - Formats each entry consistently
     * - Appends global session statistics
     *
     * Context:
     * - Triggered at session end or export action in UI
     */
    public static void write(List<MatchHistoryEntry> history,
                             int currentScore,
                             int bonusScore) {

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            for (MatchHistoryEntry entry : history) {

                Match match = entry.getMatch();
                Candidate selected = entry.getSelectedCandidate();

                Candidate a = match.getCandidateA();
                Candidate b = match.getCandidateB();

                String left = formatCandidate(a, selected);
                String right = formatCandidate(b, selected);

                int delta = entry.getDelta();

                writer.write(left + VS_SEPARATOR + right + SCORE_SEPARATOR + delta + LINE_BREAK);
            }

            writer.write(DASH_LINE + LINE_BREAK);

            writer.write(CURRENT_SCORE_LABEL + currentScore + LINE_BREAK);
            writer.write(BONUS_SCORE_LABEL + bonusScore + LINE_BREAK);
            writer.write(FINAL_SCORE_LABEL + (currentScore + bonusScore) + LINE_BREAK);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats a candidate name for report output.
     *
     * Role:
     * - Highlight selected candidate in match result
     *
     * Function:
     * - Wraps selected candidate name in parentheses
     *
     * Context:
     * - Used during report generation only
     */
    private static String formatCandidate(Candidate c, Candidate selected) {//retourne le candidat selectionner avec les parantheses

        if (c.equals(selected)) {
            return SELECTED_WRAPPER_OPEN + c.getFirstName() + SELECTED_WRAPPER_CLOSE;
        }

        return c.getFirstName();
    }
}