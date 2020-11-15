/**
 * The Simulator program implements the world of Scissors, Rock and Paper
 * that  displays 10x10 board with symbols on it
 * to the standard output.
 *
 * @author  Maruf Asatullaev
 * @version 1.0
 * @since   2020-10-31
 */
package simulator.Maruf_Asatullaev;

import simulator.do_not_change.*;
import java.util.LinkedList;

public class Simulator {
    /**
     * This is the main method which only calls
     * allActions method.
     * @param args Unused.
     * @return Nothing.
     */
    public static void main(String[] args){
        //Calls method where everything is implemented
        allActions();
    }
// Static variables for whole life of program
    private static String output; // Main string, where first symbols from 10x10 board are written
    public static int id = 0; // Counter to give each symbol unique id
// Temporary lists with different types to perform on each iteration
    static LinkedList<Symbol> tmpSymbols = new LinkedList<>();
    static LinkedList<Passive> tmpPassiveSymbols = new LinkedList<>();
    static LinkedList<Aggressive> tmpAggressiveSymbols = new LinkedList<>();
    static LinkedList<SmallCase> tmpSmallCaseSymbols = new LinkedList<>();
    static LinkedList<CapitalCase> tmpCapitalCaseSymbols = new LinkedList<>();

    /**
     * This method is used to do all overall work. Firstly, to
     * put beginning symbols of all types. Then make 1000 iterations,
     * each of which has 10 operations on symbols and their interpretation.
     */
    public static void allActions() {
        Position tmpPosition; // Temporary position
// Creating symbols of each type to put for the beginning of world
        tmpPosition = new Position(3, 1);
        Symbol capitalR = new SymbolCapitalR(id++, tmpPosition);
        tmpPosition = new Position(4, 3);
        Symbol smallR = new SymbolSmallR(id++, tmpPosition);
        tmpPosition = new Position(4, 4);
        Symbol capitalP = new SymbolCapitalP(id++, tmpPosition);
        tmpPosition = new Position(5,5);
        Symbol smallP = new SymbolSmallP(id++, tmpPosition);
        tmpPosition = new Position(9, 8);
        Symbol capitalS = new SymbolCapitalS(id++, tmpPosition);
        tmpPosition = new Position(9, 2);
        Symbol smallS = new SymbolSmallS(id++, tmpPosition);
// Creating main hashmap and putting declared symbols on it
        WorldBuilder worldBuilder = new WorldBuilder();
        worldBuilder.addSymbol(capitalR);
        worldBuilder.addSymbol(smallR);
        worldBuilder.addSymbol(capitalP);
        worldBuilder.addSymbol(smallP);
        worldBuilder.addSymbol(capitalS);
        worldBuilder.addSymbol(smallS);
// Main loop for 1000 iterations to make 10 operations
        for (int i = 1; i <= 100; i++) {
            // Clear all lists at each iteration
            tmpSymbols.clear();
            tmpCapitalCaseSymbols.clear();
            tmpSmallCaseSymbols.clear();
            tmpAggressiveSymbols.clear();
            tmpPassiveSymbols.clear();
            // Zero operation. Born new symbols from same type pairs
            for (int j = 0; j < 100; j++) {
                if (worldBuilder.world.containsKey(tmpPosition)) { // Checking whether key exist
                    for(Symbol symbol : WorldController.world.get(tmpPosition)){
                        worldBuilder.symbolPairs(WorldController.world.get(tmpPosition));
                    }
                }
            }
            // First operation. Delete dead symbols from worldBuilder
            for (int j = 0; j < 100; j++) {
                tmpPosition = new Position(j / 10, j % 10); // define tmpPosition by coordinates
                if (worldBuilder.world.containsKey(tmpPosition)) { // Checking whether key exist
                    for(Symbol symbol : WorldController.world.get(tmpPosition)){
                        if (symbol.getIdSymbol() == -1){ // Checking whether symbol is dead
                            WorldController.world.get(tmpPosition).remove(symbol); // removing dead symbol
                            break; // finishing loop to avoid an error
                        }
                    }
                }
            }
            // Second operation. Moving symbols randomly
            for (int j = 0; j < 100; j++) {
                tmpPosition = new Position(j / 10, j % 10); // define tmpPosition by coordinates
                if (worldBuilder.world.containsKey(tmpPosition)) { // Checking whether key exist
                    worldBuilder.symbolsMove(WorldController.world.get(tmpPosition));
                }
            }
            // Third operation. Death of symbols
            for (int j = 0; j < 100; j++) {
                tmpPosition = new Position(j / 10, j % 10); // define tmpPosition by coordinates
                if (worldBuilder.world.containsKey(tmpPosition)) { // Checking whether key exist
                    worldBuilder.symbolsDie(WorldController.world.get(tmpPosition));
                }
                // Additional If statement to write symbols from hashmap to temporary list,
                if (worldBuilder.world.containsKey(tmpPosition)) { // Checking whether key exist
                    for(Symbol symbol : WorldController.world.get(tmpPosition)){
                        tmpSymbols.add(symbol);
                    }
                }
            }
            // Rewriting symbols from Symbol temporary list to more specific lists
            for(Symbol symbol : tmpSymbols){
                if (symbol instanceof SmallCase){ // Small-case symbols temporary list
                    tmpSmallCaseSymbols.add((SmallCase) symbol);
                } else { // Small-case symbols temporary list
                    tmpCapitalCaseSymbols.add((CapitalCase) symbol);
                }
            }
            // Fourth and Fifth operations. Small-case symbols upgrade and Capital-case symbols jump
            worldBuilder.smallCaseUpgrade(tmpSmallCaseSymbols);
            worldBuilder.capitalCaseJump(tmpCapitalCaseSymbols);
            // Clear temporary symbols list to rewrite upgraded symbols from other lists
            tmpSymbols.clear();
            for (SmallCase symbol : tmpSmallCaseSymbols){ // Rewriting upgraded Small-case symbols
                tmpSymbols.add((Symbol)symbol);
            }
            for (CapitalCase symbol : tmpCapitalCaseSymbols){ // Rewriting upgraded Capital-case symbols
                tmpSymbols.add((Symbol)symbol);
            }
            // Rewriting symbols from Symbol temporary list to more specific lists
            for (Symbol symbol : tmpSymbols){
                if (symbol instanceof Passive){ // Passive symbols temporary list
                    tmpPassiveSymbols.add((Passive) symbol);
                } else { // Agressive symbols temporary list
                    tmpAggressiveSymbols.add((Aggressive) symbol);
                }
            }
            // Sixth, seventh and eighth operations.
            // Small-case symbols Escape and Breed. Capital-case symbols Attack smartly.
            worldBuilder.passiveEscape(tmpPassiveSymbols);
            worldBuilder.passiveBreed(tmpPassiveSymbols);
            worldBuilder.aggressiveAttackSmart(tmpAggressiveSymbols);
            // Clear temporary symbols list to rewrite upgraded symbols from other lists
            tmpSymbols.clear();
            for (Passive symbol : tmpPassiveSymbols){ // Rewriting upgraded Passive symbols
                tmpSymbols.add((Symbol)symbol);
            }
            for (Aggressive symbol : tmpAggressiveSymbols){ // Rewriting upgraded Aggressive symbols
                tmpSymbols.add((Symbol)symbol);
            }
            // Ninth operation. Symbols get older.
            worldBuilder.getOlder(tmpSymbols);
            // Clear hashmap to rewrite upgraded lists with another Positions
            worldBuilder.world.clear();
            for (Symbol symbol : tmpSymbols){
                worldBuilder.addSymbol(symbol);
            }
            // Tenth operation. Interpret the world
            output = worldBuilder.plotWorld();
            interpretWorld(output, i);
        }
    }
    /**
     * This method is used to plot the world by board 10x10. Also method
     * print number of day and frames to the board
     * show the usage of various javadoc Tags.
     * @param output This is the string with length 100 and all symbols to print
     * @param dayNumber  This parameter for printing number of day given by int type
     * @return Nothing.
     */
    public static void interpretWorld(String output, int dayNumber){
        System.out.println("Day №"+dayNumber); // Print day number
        for (int j = 0; j < 10; j++) {
            if(j == 0){ // Top frames
                for (int k = 0; k < 12; k++) {
                    System.out.print("# ");
                }
                System.out.println();
            }
            for (int k = 0; k < 12; k++) {
                if (k == 0 || k == 11){ // Side frames
                    System.out.print("# ");
                } else { // Symbols
                    System.out.print(output.charAt(j * 10 + k-1) + " ");
                }
            }
            System.out.println(); // New line
            if (j == 9){ // Bottom frames
                for (int k = 0; k < 12; k++) {
                    System.out.print("# ");
                }
                System.out.println(); // New line
            }
        }
    }
}
