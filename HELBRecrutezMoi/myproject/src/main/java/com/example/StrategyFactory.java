package com.example;

/**
 * Factory responsible for providing the correct MatchSelectionStrategy.
 *
 * Design: Factory Pattern
 * Justification: centralizes strategy creation, improving cohesion and reducing coupling between controller and concrete strategy implementations.
 */
public final class StrategyFactory {

    // =========================
    // MODES (ENUM WRAPPER)
    // =========================

    public enum Mode {
        RANDOM,
        LOCAL,
        SKILL,
        ANY
    }

    // =========================
    // SINGLETON UTIL CLASS
    // =========================

    private StrategyFactory() {
        // Utility class
    }

    /**
     * Returns the correct strategy based on selected mode.
     *
     * Role:
     * - Map user selection to concrete strategy implementation
     *
     * Function:
     * - Acts as central dispatcher for strategy instantiation
     *
     * Context:
     * - Used by MainController and session mode switching
     */
    public static MatchSelectionStrategy getStrategy(Mode mode) {

        if (mode == Mode.RANDOM) {
            return RandomSelectionStrategy.getInstance();
        }

        if (mode == Mode.LOCAL) {
            return LocalScoreSelectionStrategy.getInstance();
        }

        if (mode == Mode.SKILL) {
            return SkillScoreSelectionStrategy.getInstance();
        }

        return AnySelectionStrategy.getInstance();
    }
}