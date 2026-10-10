package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CandidateValidator.
 *
 * Role:
 * - Verify correctness of candidate validation rules
 *
 * Function:
 * - Ensures invalid candidates are rejected
 * - Ensures valid candidates are accepted
 *
 * Context:
 * - Used in automated test suite (JUnit 5)
 *
 * Design:
 * - Unit testing (white-box + black-box validation of business rules)
 */
class CandidateValidatorTest {

    private static final String VALID_NAME = "Alice";
    private static final String EMPTY_NAME = "   ";
    private static final String VALID_MOTIVATION = "I want this job";
    private static final String VALID_IMAGE = "img.png";

    private static final int VALID_DISTANCE = 10;

    /**
     * Vérifie que la validation échoue lorsqu’un candidat possède un nom vide.
     * Cas attendu : candidat invalide.
     */

    @Test
    void shouldReturnFalseWhenNameIsEmpty() {
        Candidate candidate = new Candidate(EMPTY_NAME, VALID_DISTANCE, VALID_MOTIVATION, VALID_IMAGE);
        assertFalse(CandidateValidator.isValid(candidate));//doit etre false car assert"false"
        
    }

    /**
     * Vérifie que la validation réussit lorsqu’un candidat respecte toutes les règles métier.
     * Cas attendu : candidat valide.
     */

    @Test
    void shouldReturnTrueWhenCandidateIsValid() {
        Candidate candidate = new Candidate(VALID_NAME, VALID_DISTANCE, VALID_MOTIVATION, VALID_IMAGE);
        assertTrue(CandidateValidator.isValid(candidate));
    }
}