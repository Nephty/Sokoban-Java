package model;

/**
 * The <code>Direction enum</code> is an enum used for the player movements.
 * 6 types of movements are available :
 * {@link #UP}
 * {@link #DOWN}
 * {@link #RIGHT}
 * {@link #LEFT}
 * {@link #RESTART}
 * {@link #NULL}
 */
public enum Direction {
    /**
     *UP Direction
     */
    UP,
    /**
     * DOWN Direction
     */
    DOWN,
    /**
     * Right Direction
     */
    RIGHT,
    /**
     * Left Direction
     */
    LEFT,
    /**
     * Restart Direction.
     * Used when the player wants to restart
     */
    RESTART,
    /**
     * Null Direction.
     * Used when the player do something else than a move or a restart
     */
    NULL,


}
