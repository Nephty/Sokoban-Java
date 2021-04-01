package main.java.view;

import main.java.model.Board;
import main.java.model.Direction;

public class Game {

    //---------//
    // Objects //
    //---------//

    private Board board;

    //------//
    // Data //
    //------//

    private int totalMoves = 0;
    private byte totalMovesPow = 1;
    private int totalPushes = 0;
    private byte totalPushesPow = 1;
    Direction playerFacing = Direction.DOWN;
    private boolean isCurrentLevelWon = false;

    public Game(Board board) {
        this.board = board;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    public void setTotalMoves(int totalMoves) {
        this.totalMoves = totalMoves;
    }

    public byte getTotalMovesPow() {
        return totalMovesPow;
    }

    public int getTotalPushes() {
        return totalPushes;
    }

    public void setTotalPushes(int totalPushes) {
        this.totalPushes = totalPushes;
    }

    public byte getTotalPushesPow() {
        return totalPushesPow;
    }

    public void addTotalMoves(byte value) {
        this.totalMoves += value;
    }

    public void addTotalPushes(byte value) {
        this.totalPushesPow += value;
    }

    public void addTotalPushesPow(byte value) {
        this.totalPushesPow += value;
    }

    public void addTotalMovesPow(byte value) {
        this.totalMovesPow += value;
    }

    public Direction getPlayerFacing() {
        return playerFacing;
    }

    public void setPlayerFacing(Direction playerFacing) {
        this.playerFacing = playerFacing;
    }
}
