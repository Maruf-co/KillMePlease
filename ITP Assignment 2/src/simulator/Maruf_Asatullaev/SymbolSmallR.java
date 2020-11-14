package simulator.Maruf_Asatullaev;

import simulator.do_not_change.*;

import java.util.Random;

public class SymbolSmallR extends Symbol implements Passive, SmallCase {
    public SymbolSmallR(int id, Position position) {
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
            position.column -= 1;
        }else if(randNumber == 1 && position.column != 9){ // Right
            position.column += 1;
        }else if(randNumber == 2 && position.row != 0){ // Up
            position.row -= 1;
        }else{ // Down
            position.row += 1;
        }
    }

    @Override
    public void die() {

    }

    @Override
    public void upgrade() {
        Symbol tmp = new SymbolCapitalR(Simulator.id++, position);
        WorldController.world.get(position).add(tmp);
    }

    @Override
    public void escape() {
        for (int i = 1; i <= sightDistance; i++) {
            if(position.row + i < 10 && position.row != 0 &&
                    !(WorldController.world.get(position.row + i).get(0) instanceof SymbolCapitalP)){
                position = new Position(position.column, position.row-1);
                break;
            }
            if(position.row - i > 0 && position.row != 9 &&
                    !(WorldController.world.get(position.row - i).get(0) instanceof SymbolCapitalP)){
                position = new Position(position.column, position.row+1);
                break;
            }
        }
    }

    @Override
    public void moveBreed() {
        for (int i = 1; i <= sightDistance; i++) {
            if(position.column + i < 10 &&
                    WorldController.world.get(position.column + i).get(0) instanceof SymbolCapitalP){
                position = new Position(position.column+1, position.row);
                break;
            }
            if(position.column - i > 0 &&
                    WorldController.world.get(position.column - i).get(0) instanceof SymbolCapitalP){
                position = new Position(position.column-1, position.row);
                break;
            }
        }
    }
}
