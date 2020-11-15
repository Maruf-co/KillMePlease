/**
 * <h1>Build the world!</h1>
 * The WorldBuilder class extends abstract class WorldController
 * and implements all given methods. This class also has its own methods.
 *
 * @author Maruf Asatullaev
 *  @version 1.0
 *  @since   2020-10-31
 */
package simulator.Maruf_Asatullaev;

import simulator.do_not_change.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class WorldBuilder extends WorldController{
    /**
     * This is the constructor which only creates instance if
     * hashmap world.
     * @return Nothing.
     */
    public WorldBuilder(){
        WorldController.world = new HashMap<Position, LinkedList<Symbol>>();
    }
    /**
     * This is the method to add Symbol for hashmap.
     * @param someSymbol This paramter is symbol to add to world hashmap
     * @return Nothing.
     */
    public static void addSymbol(Symbol someSymbol){
        if (!WorldController.world.containsKey(someSymbol.getPosition())){ // If hashmap have no such key
            LinkedList<Symbol> tmp = new LinkedList<>(); // Create Linked list
            tmp.add(someSymbol); // Add someSymbol to list
            WorldController.world.put(someSymbol.getPosition(), tmp); // Add list and position as a key to hashmap
        } else { // If hashmap have  such key
            // Add symbol to list which is under key that is given by position of someSymbol
            WorldController.world.get(someSymbol.getPosition()).add(someSymbol);
        }
    }
    /**
     * This is the method to move Symbols.
     * @param symbols This parameter to go through it and move its instances
     * @return Nothing.
     */
    @Override
    public void symbolsMove(List<Symbol> symbols) {
        for (Symbol symbol : symbols) {
                symbol.move();
        }
    }
    /**
     * This is the method to kill symbols
     * that should die. It could be death by getting older or
     * death by another type which kills the given symbol.
     * @param symbols This parameter to go through it and kill its instances
     * @return Nothing.
     */
    @Override
    public void symbolsDie(List<Symbol> symbols) {
        // Flags to check whether specific type of simple exist or not
        boolean existR = false;
        boolean existP = false;
        boolean existS = false;
        for (Symbol symbol : symbols) {
            if (symbol instanceof SymbolCapitalR || symbol instanceof SymbolSmallR) {
                existR = true;
            } else if (symbol instanceof SymbolCapitalP || symbol instanceof SymbolSmallP) {
                existP = true;
            } else {
                existS = true;
            }
        }
        for (Symbol symbol : symbols) {
            if (existS && !existR && (symbol instanceof SymbolCapitalP || symbol instanceof SymbolSmallP) || // S kills P's
                    existR && !existP && (symbol instanceof SymbolCapitalS || symbol instanceof SymbolSmallS) || // S kills P's
                        existP && !(symbol instanceof SymbolCapitalP) && !(symbol instanceof SymbolSmallP) || // P kills everyone not P
                    symbol.getNumberIterationsAlive() > 30) { // Old capital symbols die
                symbol.die();
            }
        }
    }
    /**
     * This method create new symbols if
     * there is pair os similar symbols in the list
     * @param symbols This parameter to go through it and match the pairs
     * @return Nothing.
     */
    public void symbolPairs(List<Symbol> symbols){
        // Flags to check whether specific type of simple exist or not
        boolean capitalSymbol = false;
        boolean smallSymbol = false;
        boolean symbolP = false;
        boolean symbolR = false;
        for(Symbol symbol : symbols){
            if (symbol instanceof CapitalCase){
                capitalSymbol = true;
            }else{
                smallSymbol = true;
            }
            if (symbol instanceof SymbolCapitalP || symbol instanceof SymbolSmallP){
                symbolP = true;
            } else if (symbol instanceof SymbolCapitalR || symbol instanceof SymbolSmallR){
                symbolR = true;
            }
        }
        if (capitalSymbol != smallSymbol && symbols.size() > 1){ // If there is pair of same type symbols, then new symbol is born
            Position tmpPosition;
            if (Simulator.id%2==0) { // If current id is even, then new symbol born in adjacent column
                if (symbols.get(0).getPosition().column != 0) { // Left
                    tmpPosition = new Position(symbols.get(0).getPosition().row, symbols.get(0).getPosition().column - 1);
                } else { // Right
                    tmpPosition = new Position(symbols.get(0).getPosition().row, 1);
                }
            }else{ // If current id is odd, then new symbol born in adjacent row
                if (symbols.get(0).getPosition().row != 9) { // Down
                    tmpPosition = new Position(symbols.get(0).getPosition().row+1, symbols.get(0).getPosition().column);
                } else { // Up
                    tmpPosition = new Position(8, symbols.get(0).getPosition().column);
                }
            }
            Symbol tmpSymbol; // Creating new symbol and giving it proper type
            if (symbolP){
                tmpSymbol = new SymbolSmallP(Simulator.id++, tmpPosition);
            } else if (symbolR){
                tmpSymbol = new SymbolSmallR(Simulator.id++, tmpPosition);
            } else {
                tmpSymbol = new SymbolSmallS(Simulator.id++, tmpPosition);
            }
            addSymbol(tmpSymbol); // Adding symbol to hashmap
        }
    }
    /**
     * This method change Small-case symbols
     * to Capital-case ones if they are grown enough
     * @param symbols This parameter to go through it and upgrade instances
     * @return Nothing.
     */
    @Override
    public void smallCaseUpgrade(List<SmallCase> symbols) {
        Symbol tmpSymbol;
        for (SmallCase symbol : symbols) {
            tmpSymbol = (Symbol) symbol; // Converting SmallCase to Symbol to use method getNumberIterationsAlive()
            if (tmpSymbol.getNumberIterationsAlive() == 20){ // If symbol 20 days old, then it should grow
                symbol.upgrade();
            }
        }
    }
    /**
     * This method change Capital-case symbols' position
     * @param symbols This parameter to go through it and upgrade position of instances
     * @return Nothing.
     */
    @Override
    public void capitalCaseJump(List<CapitalCase> symbols) {
        for (CapitalCase symbol : symbols) {
            symbol.jump();
        }
    }
    /**
     * This method change position of Passive symbols
     * to make them far away of symbols of different type
     * @param symbols This parameter to go through it and upgrade position of instances
     * @return Nothing.
     */
    @Override
    public void passiveEscape(List<Passive> symbols) {
        for (Passive symbol : symbols) {
            symbol.escape();
        }
    }
    /**
     * This method change position of Passive symbols
     * to make them closer to symbols of the same type
     * @param symbols This parameter to go through it and upgrade position of instances
     * @return Nothing.
     */
    @Override
    public void passiveBreed(List<Passive> symbols) {
        for (Passive symbol : symbols) {
            symbol.moveBreed();
        }
    }
    /**
     * This method change position of Aggressive symbols
     * to make them closer to weaker symbols
     * @param symbols This parameter to go through it and upgrade position of instances
     * @return Nothing.
     */
    @Override
    public void aggressiveAttackSmart(List<Aggressive> symbols) {
        for (Aggressive symbol : symbols) {
            symbol.attackSmart();
        }
    }
    /**
     * This method grow age of symbols by 1
     * @param symbols This parameter to go through it and upgrade age of instances
     * @return Nothing.
     */
    public void getOlder(List<Symbol> symbols){
        for (Symbol symbol : symbols) {
            symbol.becomeOlder();
        }
    }
    /**
     * This method fill string with 7 kind of symbols which could
     * be obtained from first element of lists at hashmap
     * @return String This returns string of size 100 with symbol at each position
     */
    @Override
    public String plotWorld() {
        StringBuilder worldWord = new StringBuilder(); // String to return
        Position tmpPosition; // Temporary position
        for (int i = 0; i < 100; i++) {
            tmpPosition = new Position(i / 10,i % 10); // define tmpPosition by coordinates
            if (WorldController.world.containsKey(tmpPosition) && // If there is such a key in hashmap and
                    WorldController.world.get(tmpPosition).size() > 0) { // if list not empty, then add some symbol to worldWord
                Symbol tmp = WorldController.world.get(tmpPosition).get(0);

                if (tmp instanceof SymbolCapitalP) { // add 'P'
                    worldWord.append(WorldController.CAPITAL_P);
                } else if (tmp instanceof SymbolCapitalR) { // add 'R'
                    worldWord.append(WorldController.CAPITAL_R);
                } else if (tmp instanceof SymbolCapitalS) { // add 'S'
                    worldWord.append(WorldController.CAPITAL_S);
                } else if (tmp instanceof SymbolSmallP) { // add 'p'
                    worldWord.append(WorldController.SMALL_P);
                } else if (tmp instanceof SymbolSmallR) { // add 'r'
                    worldWord.append(WorldController.SMALL_R);
                } else if (tmp instanceof SymbolSmallS) { // add 's'
                    worldWord.append(WorldController.SMALL_S);
                }
            } else { // add ' '
                worldWord.append(" ");
            }
        }
        return worldWord.toString();
    }
}
