package com.example;

/**
 * Subject interface for Observer pattern.
 *
 * Role:
 * - Defines the observable contract for registering and notifying observers
 *
 * Function:
 * - Manages observer lifecycle (attach, detach, notification)
 *
 * Context:
 * - Used by RecruiterSession to propagate state changes to the UI
 *
 * Design:
 * - Part of GoF Observer pattern
 * - Improves cohesion by decoupling state management from UI update logic
 */
public interface Subject {

    /**
     * Attach an observer to be notified on state change.
     *
     * Role:
     * - Register an observer into the notification list
     *
     * Function:
     * - Adds observer to internal subscription mechanism
     */
    void attach(Observer observer);

    /**
     * Detach an observer.
     *
     * Role:
     * - Remove an observer from notifications
     *
     * Function:
     * - Prevents further updates to this observer
     */
    void detach(Observer observer);

    /**
     * Notify all observers of state change.
     *
     * Role:
     * - Broadcast current subject state
     *
     * Function:
     * - Triggers update() on all registered observers
     */
    void notifyObservers();
}