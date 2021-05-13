package view;

import model.Board;
import model.Direction;

public class Game {

    //---------//
    // Objects //
    //---------//

    private Board board;

    //------//
    // Data //
    //------//

    private int totalMoves = 0;
    private byte totalMovesMagnitude = 1;
    private int totalPushes = 0;
    private byte totalPushesMagnitude = 1;
    Direction playerFacing = Direction.DOWN;

    /**
     * Create a new <code>Game</code> object with a defined Board.
     *
     * @param board The Board on which the game will be playedÂµ
     */
    public Game(Board board) {
        this.board = board;
    }

    /**
     * Return the total amount of moves made during the current game.
     *
     * @return The attribute containing the total amount of moves
     */
    public int getTotalMoves() {
        return totalMoves;
    }

    /**
     * Set the total amount of moves made during the current game.
     *
     * @param totalMoves The new value
     */
    public void setTotalMoves(int totalMoves) {
        this.totalMoves = totalMoves;
    }

    /**
     * Return the current magnitude of the current total amount of moves.
     *
     * @return The attribute containing the magnitude of the total amount of moves
     */
    public byte getTotalMovesMagnitude() {
        return totalMovesMagnitude;
    }

    /**
     * Return the total amount of pushes made during the current game.
     *
     * @return The attribute containing the total amount of pushes
     */
    public int getTotalPushes() {
        return totalPushes;
    }

    /**
     * Set the total amount of pushes made during the current game.
     *
     * @param totalPushes The new value
     */
    public void setTotalPushes(int totalPushes) {
        this.totalPushes = totalPushes;
    }

    /**
     * Return the total amount of pushes made during the current game.
     *
     * @return The attribute containing the magnitude of the total amount of pushes
     */
    public byte getTotalPushesMagnitude() {
        return totalPushesMagnitude;
    }

    /**
     * Increment the total amount of moves made during the current game.
     */
    public void addTotalMoves(byte value) {
        this.totalMoves += value;
    }

    /**
     * Increment the total amount of pushes made during the current game.
     *
     * @param value The value to add
     */
    public void addTotalPushes(byte value) {
        this.totalPushes += value;
    }

    /**
     * Increment the current magnitude of the total amount of moves made during the current game.
     *
     * @param value The value to add
     */
    public void addTotalPushesMagnitude(byte value) {
        this.totalPushesMagnitude += value;
    }

    /**
     * Increment the current magnitude of the total amount of pushes made during the current game.
     *
     * @param value The value to add
     */
    public void addTotalMovesMagnitude(byte value) {
        this.totalMovesMagnitude += value;
    }

    /**
     * Return the current <code>Direction</code> towards which the player is facing.
     *
     * @return The attribute containing the facing direction
     */
    public Direction getPlayerFacing() {
        return playerFacing;
    }

    /**
     * Set the current <code>Direction</code> towards which the player is facing.
     *
     * @param playerFacing The new <code>Direction</code>
     */
    public void setPlayerFacing(Direction playerFacing) {
        this.playerFacing = playerFacing;
    }

    /**
     * Set the current <code>Board</code> on which the game is played.
     *
     * @param board The new <code>Board</code>
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Return the current <code>Board</code> on which the game is played.
     *
     * @return The attribute containing the current <code>Board</code>
     */
    public Board getBoard() {
        return this.board;
    }
}