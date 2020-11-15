/**
 * The SymbolCapitalS class extends abstract class Symbol, implements
 * interfaces Aggressive and CapitalCase and
 * implements all given methods.
 *
 * @author Maruf Asatullaev
 *  @version 1.0
 *  @since   2020-10-31
 */
package simulator.Maruf_Asatullaev;

import simulator.do_not_change.*;

import java.util.Random;

public class SymbolCapitalS extends Symbol implements Aggressive, CapitalCase {
    /**
     * This is the constructor which creates instance of
     * SymbolCapitalS by given paramters
     * @param id This paramter is unique for each symbol
     * @param position This paramter symbol's Position at world
     * @return Nothing.
     */
    public SymbolCapitalS(int id, Position position) {
        this.position = position;
        this.idSymbol = id;
        this.numberIterationsAlive = 0;
        this.sightDistance = 5;
    }
    /**
     * This is the method to move symbol randomly by 1 step.
     * @return Nothing.
     */
    @Override
    public void move() {
        Random rand = new Random(); // creating random variable
        int randNumber = rand.nextInt(4); // giving boundaries to have 4 cases
        if (randNumber == 0 && position.column != 0){ // Left
            position = new Position(position.column-1, position.row);
        } else if (randNumber == 1 && position.column != 9){ // Right
            position = new Position(position.column+1, position.row);
        } else if (randNumber == 2 && position.row != 0){ // Up
            position = new Position(position.column, position.row-1);
        } else { // Down
            position = new Position(position.column, position.row+1);
        }
    }
    /**
     * This is the method to print symbol's last words.
     * @return Nothing.
     */
    @Override
    public void die() {
        System.out.println("I am 'S' and I have dead"); // Message
        idSymbol = -1; // Intialize id as -1 to show for special method that symbol should be removed
    }
    /**
     * This is the method to move symbol randomly.
     * @return Nothing.
     */
    @Override
    public void jump() {
        Random rand = new Random(); // creating random variable
        int randRow = rand.nextInt(10); // giving random value for row from 0 to 9
        int randColumn = rand.nextInt(10); // giving random value fro column from 0 to 9
        position = new Position(randRow, randColumn); // New position
    }
    /**
     * This is the method to move closer to symbol of weaker type.
     * @return Nothing.
     */
    @Override
    public void attackSmart() {
        Position tmpPosition;
        for (int i = 1; i <= sightDistance; i++) {
            if (position.column + i < 10) { // Checking for moving symbol Right
                tmpPosition = new Position(position.row, position.column + i);
                if (WorldController.world.containsKey(tmpPosition) && // Checking whether key exist
                        WorldController.world.get(tmpPosition).size() != 0 && // Checking whether list is not empty
                        (WorldController.world.get(tmpPosition).get(0) instanceof SymbolCapitalP || // Checking whether symbol of weaker type
                                WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallP)) { // Checking whether symbol of weaker type
                    position = new Position(position.row, position.column + 1); // New position
                    break; // Finalizing as movement is complete
                }
            }
            if (position.column - i > 0) { // Checking for moving symbol Left
                tmpPosition = new Position(position.row, position.column - i);
                if (WorldController.world.containsKey(tmpPosition) && // Checking whether list is not empty
                        WorldController.world.get(tmpPosition).size() != 0 && // Checking whether list is not empty
                        (WorldController.world.get(tmpPosition).get(0) instanceof SymbolCapitalP || // Checking whether symbol of weaker type
                                WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallP)) { // Checking whether symbol of weaker type
                    position = new Position(position.row, position.column - 1); // New position
                    break; // Finalizing as movement is complete
                }
            }
        }
    }
}
