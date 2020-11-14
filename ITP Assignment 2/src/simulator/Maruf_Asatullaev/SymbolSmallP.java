package simulator.Maruf_Asatullaev;

import simulator.do_not_change.*;

import java.util.Random;

public class SymbolSmallP extends Symbol implements Aggressive, SmallCase {
    public SymbolSmallP(int id, Position position) {
        this.position = position;
        this.idSymbol = id;
        this.numberIterationsAlive = 0;
        this.sightDistance = 3;
    }

    @Override
    public void move() {
        Random rand = new Random();
        int randNumber = rand.nextInt(3);
        if(randNumber == 0 && position.column != 0){ // Left
            position = new Position(position.column-1, position.row);
        }else if(randNumber == 1 && position.column != 9){ // Right
            position = new Position(position.column+1, position.row);
        }else if(randNumber == 2 && position.row != 0){ // Up
            position = new Position(position.column, position.row-1);
        }else{ // Down
            position = new Position(position.column, position.row+1);
        }
    }

    @Override
    public void die() {

    }

    @Override
    public void upgrade() {
        Symbol tmp = new SymbolCapitalP(Simulator.id++, position);
        WorldController.world.get(position).add(tmp);
    }
    @Override
    public void attackSmart() {
        for (int i = 1; i <= sightDistance; i++) {
            if(position.row + i < 10 &&
                    (WorldController.world.get(position.row + i).get(0) instanceof SymbolCapitalR ||
                            WorldController.world.get(position.row + i).get(0) instanceof SymbolSmallR)){
                position = new Position(position.column, position.row+1);
                break;
            }
            if(position.row - i > 0 &&
                    (WorldController.world.get(position.row - i).get(0) instanceof SymbolCapitalR ||
                            WorldController.world.get(position.row - i).get(0) instanceof SymbolSmallR)){
                position = new Position(position.column, position.row-1);
                break;
            }
        }
    }

}
