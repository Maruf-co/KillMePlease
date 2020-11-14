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
            if(symbol instanceof SymbolCapitalP)
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
                    existP && !(symbol instanceof SymbolCapitalP) && !(symbol instanceof SymbolSmallP) ||
                    symbol.getNumberIterationsAlive() > 30) {
                symbol.die();
                symbols.remove(symbol);
            }
        }
        symbolPairs(symbols, existP, existR, existS);

    }
    public void symbolPairs(List<Symbol> symbols, boolean existP, boolean existR, boolean existS){
        boolean capitalSymbol = false;
        boolean smallSymbol = false;
        for(Symbol symbol : symbols){
            if(symbol instanceof CapitalCase){
                capitalSymbol = true;
            }else{
                smallSymbol = true;
            }
        }
        if(!(capitalSymbol && smallSymbol)){
            Position tmpPosition = null;
            if(symbols.get(0).getPosition().row == 0){
                tmpPosition = new Position(1, symbols.get(0).getPosition().column);
            }else{
                tmpPosition = new Position(symbols.get(0).getPosition().row-1, symbols.get(0).getPosition().column);
            }
            Symbol tmpSymbol = null;
            if(existP){
                tmpSymbol = new SymbolSmallP(Simulator.id++, tmpPosition);
            }else if(existR){
                tmpSymbol = new SymbolSmallR(Simulator.id++, tmpPosition);
            }else if(existS){
                tmpSymbol = new SymbolSmallS(Simulator.id++, tmpPosition);
            }
            this.addSymbol(tmpSymbol);
        }
    }

    @Override
    public void smallCaseUpgrade(List<SmallCase> symbols) {
        Symbol tmpSymbol;
        for (SmallCase symbol : symbols) {
            tmpSymbol = (Symbol) symbol;
            if(tmpSymbol.getNumberIterationsAlive() == 20){
                symbol.upgrade();
                symbols.remove(symbol);
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
                Symbol tmp = WorldController.world.get(tmpPosition).get(0);
                if (tmp instanceof SymbolCapitalP) {
                    worldWord.append(WorldController.CAPITAL_P);
                } else if (tmp instanceof SymbolCapitalR) {
                    worldWord.append(WorldController.CAPITAL_R);
                } else if (tmp instanceof SymbolCapitalS) {
                    worldWord.append(WorldController.CAPITAL_S);
                } else if (tmp instanceof SymbolSmallP) {
                    worldWord.append(WorldController.SMALL_P);
                } else if (tmp instanceof SymbolSmallR) {
                    worldWord.append(WorldController.SMALL_R);
                } else if (tmp instanceof SymbolSmallS) {
                    worldWord.append(WorldController.SMALL_S);
                }
            } else {
                worldWord.append(" ");
            }
        }
        return worldWord.toString();
    }
}
