package simulator.Maruf_Asatullaev;

import simulator.do_not_change.*;

import java.util.Random;

public class SymbolSmallS extends Symbol implements Passive, SmallCase {
    public SymbolSmallS(int id, Position position) {
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
        System.out.println("I am 's' and I have dead");
    }

    @Override
    public void escape() {
        Position tmpPosition;
        for (int i = 1; i <= sightDistance; i++) {
            if(position.row + i < 10 && position.row != 0){
                tmpPosition = new Position(position.row + i, position.column);
                if(WorldController.world.containsKey(tmpPosition) &&
                        !(WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallS)){
                    position = new Position(position.row-1, position.column);
                    break;
                }
            }
            if(position.row - i > 0 && position.row != 9){
                tmpPosition = new Position(position.row - i, position.column);
                if(WorldController.world.containsKey(tmpPosition) &&
                        !(WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallS)) {
                    position = new Position(position.row + 1, position.column);
                    break;
                }
            }
        }
    }

    @Override
    public void moveBreed() {
        Position tmpPosition;
        for (int i = 1; i <= sightDistance; i++) {
            if(position.row + i < 10){
                tmpPosition = new Position(position.row, position.column+i);
                if(WorldController.world.containsKey(tmpPosition) &&
                        WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallS){
                    position = new Position(position.row, position.column+1);
                    break;
                }
            }
            if(position.row - i > 0){
                tmpPosition = new Position(position.row, position.column-i);
                if(WorldController.world.containsKey(tmpPosition) &&
                        WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallS){
                    position = new Position(position.row, position.column-1);
                    break;
                }
            }
        }
    }

    @Override
    public void upgrade() {
        Symbol tmp = new SymbolCapitalS(Simulator.id++, position);
        WorldController.world.get(position).add(tmp);
    }
}
