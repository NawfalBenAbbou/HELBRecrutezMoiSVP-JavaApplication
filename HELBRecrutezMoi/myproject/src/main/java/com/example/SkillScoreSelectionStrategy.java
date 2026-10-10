package com.example;

import java.util.List;

/**
 * Skill-based selection strategy.
 *
 * Role:
 * - Selects a pair of candidates based on similarity of keyword relevance
 *
 * Function:
 * - Computes keyword-based score for each candidate
 * - Chooses the pair with the smallest gap in skill relevance
 *
 * Context:
 * - Used when recruiter activates SKILL mode in the application
 *
 * Design:
 * - Strategy pattern implementation
 * - Singleton to avoid unnecessary instantiation (stateless logic)
 * - Improves cohesion by isolating keyword comparison logic
 */
public class SkillScoreSelectionStrategy implements MatchSelectionStrategy {

    // =========================
    // CONSTANTS
    // =========================

    private static SkillScoreSelectionStrategy INSTANCE;

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int MAX_PERCENT = 100;

    private SkillScoreSelectionStrategy() {
    }

    /**
     * Returns singleton instance of the strategy.
     *
     * Role:
     * - Ensure single shared instance
     *
     * Function:
     * - Lazily creates instance if needed
     *
     * Context:
     * - Used by StrategyFactory
     */
    public static SkillScoreSelectionStrategy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SkillScoreSelectionStrategy();
        }
        return INSTANCE;
    }

    /**
     * Selects the best match based on keyword similarity.
     *
     * Role:
     * - Find two candidates with closest keyword relevance
     *
     * Function:
     * - Iterates over all pairs of candidates
     * - Computes keyword score difference
     * - Keeps pair with minimal gap
     *
     * Context:
     * - Used in SKILL selection mode
     */
    @Override
    public Match selectMatch(List<Candidate> candidates) {

        List<String> keywords = KeywordRepository.getInstance().getKeywords();

        Candidate bestA = candidates.get(ZERO);
        Candidate bestB = candidates.get(ONE);

        int bestGap = Math.abs(
                keywordScore(bestA, keywords) - keywordScore(bestB, keywords)
        );

        for (int i = ZERO; i < candidates.size(); i++) {
            for (int j = i + ONE; j < candidates.size(); j++) {

                Candidate a = candidates.get(i);
                Candidate b = candidates.get(j);

                int gap = Math.abs(
                        keywordScore(a, keywords) - keywordScore(b, keywords)
                );

                if (gap < bestGap) {
                    bestGap = gap;
                    bestA = a;
                    bestB = b;
                }
            }
        }

        return new Match(
                bestA,
                bestB,
                bestA.computeScore(keywords),
                bestB.computeScore(keywords)
        );
    }

    /**
     * Computes keyword relevance score for a candidate.
     *
     * Role:
     * - Measure how strongly a candidate matches keywords
     *
     * Function:
     * - Counts occurrences of keywords in motivation text
     * - Converts ratio into percentage score
     *
     * Context:
     * - Internal helper used only by selection algorithm
     */
    private int keywordScore(Candidate c, List<String> keywords) {

        String mot = c.getMotivation().toLowerCase();
        int hits = ZERO;

        for (String k : keywords) {
            if (mot.contains(k.toLowerCase())) {
                hits++;
            }
        }

        if (keywords.isEmpty()) {
            return ZERO;
        }

        return (int) Math.round(((double) hits / keywords.size()) * MAX_PERCENT);
    }
}