import model.Board;
import model.Direction;
import model.Fichier;
import model.LevelSaver;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;

class test{
    private Board board;

    @Test
    void loadXSB(){
        try {
            // If there is no error loading the file, the test passes
            ArrayList<String> map = Fichier.loadFile("win_test.xsb", "test");
            board = new Board(map);
        } catch (IOException e){
            System.out.println(e.getMessage());
            Assertions.fail();
        }
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
        try {
            //Compare if the moveUP_test map is equal to the moveUP_final map after a move.
            ArrayList<String> mapUP = Fichier.loadFile("moveUP_test.xsb", "test");
            ArrayList<String> mapUPFinal = Fichier.loadFile("moveUP_final.xsb", "test");
            Board boardUP = new Board(mapUP);
            boardUP.move(Direction.UP);
            Board boardUPFinal = new Board(mapUPFinal);
            Assertions.assertTrue(boardUP.isEquals(boardUPFinal));
        } catch (IllegalArgumentException e1){
            // There's an error in the content of the file
            System.out.println("Error : " + e1.getMessage());
            Assertions.fail();
        } catch (IOException e2){
            // Couldn't find the file.
            System.out.println("Error : " + e2.getMessage());
            Assertions.fail();
        }
    }

    @Test
    void moveInAWall() {
        try{
            //Compare if the player doesn't move after a move in a wall
            ArrayList<String> mapUP = Fichier.loadFile("moveUP_test.xsb", "test");
            ArrayList<String> mapInit = Fichier.loadFile("moveUP_test.xsb", "test");
            Board boardUP = new Board(mapUP);
            boardUP.move(Direction.RIGHT);
            Board boardInit = new Board(mapInit);
            Assertions.assertTrue(boardUP.isEquals(boardInit));
        } catch (IllegalArgumentException e1){
            // There's an error in the content of the file
            System.out.println("Error : " + e1.getMessage());
            Assertions.fail();
        } catch (IOException e2){
            // Couldn't find the file.
            System.out.println("Error : " + e2.getMessage());
            Assertions.fail();
        }

    }

    @Test
    void moveBoxInWall(){
        try{
            //Test in the player can't push a box in a wall
            ArrayList<String> mapUP = Fichier.loadFile("moveBoxWall_test.xsb", "test");
            ArrayList<String> mapInit = Fichier.loadFile("moveBoxWall_test.xsb", "test");
            Board boardUP = new Board(mapUP);
            boardUP.move(Direction.UP);
            Board boardInit = new Board(mapInit);
            Assertions.assertTrue(boardUP.isEquals(boardInit));
        } catch (IllegalArgumentException e1){
            // There's an error in the content of the file
            System.out.println("Error : " + e1.getMessage());
            Assertions.fail();
        } catch (IOException e2){
            // Couldn't find the file.
            System.out.println("Error : " + e2.getMessage());
            Assertions.fail();
        }

    }

    @Test
    void moveBoxGoal(){
        try{
            //Test if a player can push a box on a Goal
            ArrayList<String> mapUP = Fichier.loadFile("moveBoxGoal_test.xsb", "test");
            ArrayList<String> mapInit = Fichier.loadFile("moveBoxGoal_final.xsb", "test");
            Board boardUP = new Board(mapUP);
            boardUP.move(Direction.DOWN);
            Board boardInit = new Board(mapInit);
            Assertions.assertTrue(boardUP.isEquals(boardInit));
        } catch (IllegalArgumentException e1){
            // There's an error in the content of the file
            System.out.println("Error : " + e1.getMessage());
            Assertions.fail();
        } catch (IOException e2){
            // Couldn't find the file.
            System.out.println("Error : " + e2.getMessage());
            Assertions.fail();
        }
    }

    @Test
    void loadMoves(){
        try {
            //Load a .mov file
            ArrayList<Direction> moves = LevelSaver.getHistory("testMovesSerial.mov", "test");
            Assertions.assertEquals(moves.get(2), Direction.RIGHT);
        } catch (Exception e){
            System.out.println("An Error occurred while loading the file");
            Assertions.fail();
        }
    }

    @Test
    void applyMoves(){
        try{
            //apply the .move file to a .xsb file
            ArrayList<String> map = Fichier.loadFile("testMov.xsb", "test");
            ArrayList<String> mapFinal = Fichier.loadFile("testMov_final.xsb", "test");
            Board board = new Board(map);
            ArrayList<Direction> res = LevelSaver.getHistory("testMovesSerial.mov", "test");
            board.applyMoves(res);
            Board boardFinal = new Board(mapFinal);
            Assertions.assertTrue(board.isEquals(boardFinal));
        } catch (IllegalArgumentException e1){
            // There's an error in the content of the file
            System.out.println("Error : " + e1.getMessage());
            Assertions.fail();
        } catch (IOException | ClassNotFoundException e2){
            // Couldn't find the file or .mov file not in a good format.
            System.out.println("Error : " + e2.getMessage());
            Assertions.fail();
        }
    }
}