package simulator.Maruf_Asatullaev;


import simulator.do_not_change.*;

import java.util.LinkedList;

public class Simulator {
    public static void main(String[] args){
        actionPerformed();
    }

    private static String output;
    public static int id = 0;

    static LinkedList<Symbol> tmpSymbols = new LinkedList<>();
    static LinkedList<Passive> tmpPassiveSymbols = new LinkedList<>();
    static LinkedList<Aggressive> tmpAggressiveSymbols = new LinkedList<>();
    static LinkedList<SmallCase> tmpSmallCaseSymbols = new LinkedList<>();
    static LinkedList<CapitalCase> tmpCapitalCaseSymbols = new LinkedList<>();

    public static void actionPerformed() {
        Position tmpPosition = null;
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

        WorldBuilder worldBuilder = new WorldBuilder();
        worldBuilder.addSymbol(capitalR);
        worldBuilder.addSymbol(smallR);
        worldBuilder.addSymbol(capitalP);
        worldBuilder.addSymbol(smallP);
        worldBuilder.addSymbol(capitalS);
        worldBuilder.addSymbol(smallS);


        for (int i = 1; i <= 2; i++) {
            tmpSymbols.clear();
            tmpCapitalCaseSymbols.clear();
            tmpSmallCaseSymbols.clear();
            tmpAggressiveSymbols.clear();
            tmpPassiveSymbols.clear();

            for (int j = 0; j < 100; j++) {
                tmpPosition = new Position(j / 10, j % 10);
                if(worldBuilder.world.containsKey(tmpPosition)) {
                    worldBuilder.symbolsMove(WorldController.world.get(tmpPosition));
                }if(worldBuilder.world.containsKey(tmpPosition)) {
                    worldBuilder.symbolsDie(WorldController.world.get(tmpPosition));
                }if(worldBuilder.world.containsKey(tmpPosition)) {
                    for(Symbol symbol : WorldController.world.get(tmpPosition)){
                        tmpSymbols.add(symbol);
                    }
                }

            }


            for(Symbol symbol : tmpSymbols){
                if(symbol instanceof SmallCase){
                    tmpSmallCaseSymbols.add((SmallCase) symbol);
                }else{
                    tmpCapitalCaseSymbols.add((CapitalCase) symbol);
                }
            }
            worldBuilder.smallCaseUpgrade(tmpSmallCaseSymbols);
            worldBuilder.capitalCaseJump(tmpCapitalCaseSymbols);

            tmpSymbols.clear();
            for(SmallCase symbol : tmpSmallCaseSymbols){
                tmpSymbols.add((Symbol)symbol);
            }
            for(CapitalCase symbol : tmpCapitalCaseSymbols){
                tmpSymbols.add((Symbol)symbol);
            }

            for(Symbol symbol : tmpSymbols){
                if(symbol instanceof Passive){
                    tmpPassiveSymbols.add((Passive) symbol);
                }else{
                    tmpAggressiveSymbols.add((Aggressive) symbol);
                }
            }
//            worldBuilder.passiveEscape(tmpPassiveSymbols);
//            worldBuilder.passiveBreed(tmpPassiveSymbols);
//            worldBuilder.aggressiveAttackSmart(tmpAggressiveSymbols);

            tmpSymbols.clear();
            for(Passive symbol : tmpPassiveSymbols){
                tmpSymbols.add((Symbol)symbol);
            }
            for(Aggressive symbol : tmpAggressiveSymbols){
                tmpSymbols.add((Symbol)symbol);
            }
            worldBuilder.getOlder(tmpSymbols);

            worldBuilder.world.clear();
            for(Symbol symbol : tmpSymbols){
                worldBuilder.addSymbol(symbol);
            }

            output = worldBuilder.plotWorld();
            for (int j = 0; j < 10; j++) {
                if(j==0){
                    for (int k = 0; k < 12; k++) {
                        System.out.print("# ");
                    }
                    System.out.println();
                }
                for (int k = 0; k < 12; k++) {
                    if(k == 0 || k == 11){
                        System.out.print("# ");
                    }else{
                        System.out.print(output.charAt(j * 10 + k-1));
                        System.out.print(" ");
                    }
                }
                System.out.println();
                if(j==9){
                    for (int k = 0; k < 12; k++) {
                        System.out.print("# ");
                    }
                    System.out.println();
                }
            }

        }
    }

}
