import model.Board;
import model.Direction;
import model.File;
import model.LevelSaver;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

class test{
    private Board board;

    @Test
    void loadXSB(){
        // If there is no error loading the file, the test passes
        ArrayList<String> map = File.loadFile("win_test.xsb", "test");
        board = new Board(map);
    }

    @Test
    void isWinnable(){
        //Test if the game is won when we push a box on a Goal (Test if we can push a box on a goal at the same time)
        loadXSB();
        board.move(Direction.DOWN);
        Assertions.assertTrue(board.isWin());
    }

    @Test
    void moveUP(){
        //Compare if the moveUP_test map is equal to the moveUP_final map after a move.
        ArrayList<String> mapUP = File.loadFile("moveUP_test.xsb", "test");
        ArrayList<String> mapUPFinal = File.loadFile("moveUP_final.xsb", "test");
        Board boardUP = new Board(mapUP);
        boardUP.move(Direction.UP);
        Board boardUPFinal = new Board(mapUPFinal);
        Assertions.assertTrue(boardUP.isEquals(boardUPFinal));
    }

    @Test
    void moveInAWall() {
        //Compare if the player doesn't move after a move in a wall
        ArrayList<String> mapUP = File.loadFile("moveUP_test.xsb", "test");
        ArrayList<String> mapInit = File.loadFile("moveUP_test.xsb", "test");
        Board boardUP = new Board(mapUP);
        boardUP.move(Direction.RIGHT);
        Board boardInit = new Board(mapInit);
        Assertions.assertTrue(boardUP.isEquals(boardInit));
    }

    @Test
    void moveBoxInWall(){
        //Test in the player can't push a box in a wall
        ArrayList<String> mapUP = File.loadFile("moveBoxWall_test.xsb", "test");
        ArrayList<String> mapInit = File.loadFile("moveBoxWall_test.xsb", "test");
        Board boardUP = new Board(mapUP);
        boardUP.move(Direction.UP);
        Board boardInit = new Board(mapInit);
        Assertions.assertTrue(boardUP.isEquals(boardInit));
    }

    @Test
    void moveBoxGoal(){
        //Test if a player can push a box on a Goal
        ArrayList<String> mapUP = File.loadFile("moveBoxGoal_test.xsb", "test");
        ArrayList<String> mapInit = File.loadFile("moveBoxGoal_final.xsb", "test");
        Board boardUP = new Board(mapUP);
        boardUP.move(Direction.DOWN);
        Board boardInit = new Board(mapInit);
        Assertions.assertTrue(boardUP.isEquals(boardInit));

    }

    @Test
    void loadMoves(){
        //Load a .mov file
        ArrayList<Direction> moves = LevelSaver.getHistory("testMovesSerial.mov", "test");
        Assertions.assertEquals(moves.get(2), Direction.RIGHT);
    }

    @Test
    void applyMoves(){
        //apply the .move file to a .xsb file
        ArrayList<String> map = File.loadFile("testMov.xsb", "test");
        ArrayList<String> mapFinal = File.loadFile("testMov_final.xsb", "test");
        Board board = new Board(map);
        ArrayList<Direction> res = LevelSaver.getHistory("testMovesSerial.mov", "test");
        board.applyMoves(res);
        Board boardFinal = new Board(mapFinal);
        Assertions.assertTrue(board.isEquals(boardFinal));
    }

    @Test
    void moveTeleport(){
        //The player moves to a Teleport from an "air" block
        ArrayList<String> map = File.loadFile("moveTeleport.xsb", "test");
        ArrayList<String> mapFinal = File.loadFile("moveTeleport_final.xsb","test");
        Board board = new Board(map);
        board.move(Direction.LEFT);
        board.move(Direction.LEFT);
        board.printList();
        Board boardFinal = new Board(mapFinal);
        boardFinal.printList();
        Assertions.assertTrue(board.isEquals(boardFinal));
    }

    @Test
    void moveTeleportFromGoal(){
        // The player moves to a Teleport from a Goal.
        ArrayList<String> map = File.loadFile("moveTeleportGoal.xsb", "test");
        ArrayList<String> mapFinal = File.loadFile("moveTeleportGoal_final.xsb","test");
        Board board = new Board(map);
        board.move(Direction.LEFT);
        board.move(Direction.LEFT);
        board.printList();
        Board boardFinal = new Board(mapFinal);
        boardFinal.printList();
        Assertions.assertTrue(board.isEquals(boardFinal));
    }

    @Test
    void moveTeleportToGoal(){
        // The player moves to a Teleport and to a Goal.
        ArrayList<String> map = File.loadFile("moveTeleportToGoal.xsb", "test");
        ArrayList<String> mapFinal = File.loadFile("moveTeleportToGoal_final.xsb","test");
        Board board = new Board(map);
        board.move(Direction.LEFT);
        board.move(Direction.LEFT);
        board.printList();
        Board boardFinal = new Board(mapFinal);
        boardFinal.printList();
        Assertions.assertTrue(board.isEquals(boardFinal));
    }
}