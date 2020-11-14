package simulator.Maruf_Asatullaev;

import simulator.do_not_change.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class WorldBuilder extends WorldController{
    public WorldBuilder(){
        WorldController.world = new HashMap<Position, LinkedList<Symbol>>();
    }

    public void addSymbol(Symbol someSymbol){
        if(!WorldController.world.containsKey(someSymbol.getPosition())){
            LinkedList<Symbol> tmp = new LinkedList<>();
            tmp.add(someSymbol);
            WorldController.world.put(someSymbol.getPosition(), tmp);
        }else{
            WorldController.world.get(someSymbol.getPosition()).add(someSymbol);
        }

    }

    @Override
    public void symbolsMove(List<Symbol> symbols) {
        for (Symbol symbol : symbols) {
                symbol.move();
        }
    }

    @Override
    public void symbolsDie(List<Symbol> symbols) {
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
            if (existS && !existR && (symbol instanceof SymbolCapitalP || symbol instanceof SymbolSmallP) ||
                    existR && !existP && (symbol instanceof SymbolCapitalS || symbol instanceof SymbolSmallS) ||
                    existP && (!(symbol instanceof SymbolCapitalP) || !(symbol instanceof SymbolSmallP)) ||
                    symbol.getNumberIterationsAlive() > 30) {
                symbol.die();
                symbols.remove(symbol);
            }
        }


    }

    @Override
    public void smallCaseUpgrade(List<SmallCase> symbols) {
        Symbol tmpSymbol;
        for (SmallCase symbol : symbols) {
            tmpSymbol = (Symbol) symbol;
            if(tmpSymbol.getNumberIterationsAlive() == 20){
                symbol.upgrade();
            }

        }
    }

    @Override
    public void capitalCaseJump(List<CapitalCase> symbols) {
        for (CapitalCase symbol : symbols) {
            symbol.jump();
        }
    }

    @Override
    public void passiveEscape(List<Passive> symbols) {
        for (Passive symbol : symbols) {
            symbol.escape();
        }
    }

    @Override
    public void passiveBreed(List<Passive> symbols) {
        for (Passive symbol : symbols) {
            symbol.moveBreed();
        }
    }

    @Override
    public void aggressiveAttackSmart(List<Aggressive> symbols) {
        for (Aggressive symbol : symbols) {
            symbol.attackSmart();
        }
    }
    public void getOlder(List<Symbol> symbols){
        for (Symbol symbol : symbols) {
            symbol.becomeOlder();
        }
    }

    @Override
    public String plotWorld() {
        StringBuilder worldWord = new StringBuilder();
        Position tmpPosition = null;
        for (int i = 0; i < 100; i++) {
            tmpPosition = new Position(i / 10,i % 10);
            if (WorldController.world.containsKey(tmpPosition)) {
                if (WorldController.world.get(tmpPosition).get(0) instanceof SymbolCapitalP) {
                    worldWord.append(CAPITAL_P);
                } else if (WorldController.world.get(tmpPosition).get(0) instanceof SymbolCapitalR) {
                    worldWord.append(CAPITAL_R);
                } else if (WorldController.world.get(tmpPosition).get(0) instanceof SymbolCapitalS) {
                    worldWord.append(CAPITAL_S);
                } else if (WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallP) {
                    worldWord.append(SMALL_P);
                } else if (WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallR) {
                    worldWord.append(SMALL_R);
                } else if (WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallS) {
                    worldWord.append(SMALL_S);
                }
            } else {
                worldWord.append("ф");
            }
        }

        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 10; k++) {
                System.out.print(worldWord.charAt(j * 10 + k));
            }
            System.out.println();
        }
        return worldWord.toString();

    }
}
