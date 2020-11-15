package simulator.Maruf_Asatullaev;

import simulator.do_not_change.*;

import java.util.Random;


public class SymbolCapitalS extends Symbol implements Aggressive, CapitalCase {
    public SymbolCapitalS(int id, Position position) {
        this.position = position;
        this.idSymbol = id;
        this.numberIterationsAlive = 0;
        this.sightDistance = 5;
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
        System.out.println("I am 'S' and I have dead");
    }

    @Override
    public void jump() {
        Random rand = new Random();
        int randNumber = rand.nextInt(10);
        position.row = randNumber;
        randNumber = rand.nextInt(10);
        position.column = randNumber;
    }

    @Override
    public void attackSmart() {
        Position tmpPosition;
        for (int i = 1; i <= sightDistance; i++) {
            if(position.row + i < 10){
                tmpPosition = new Position(position.row, position.column+i);
                if(WorldController.world.containsKey(tmpPosition) &&
                        (WorldController.world.get(tmpPosition).get(0) instanceof SymbolCapitalP ||
                                WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallP)){
                    position = new Position(position.row, position.column+1);
                    break;
                }
            }
            if(position.row - i > 0){
                tmpPosition = new Position(position.row, position.column-i);
                if(WorldController.world.containsKey(tmpPosition) &&
                        (WorldController.world.get(tmpPosition).get(0) instanceof SymbolCapitalP ||
                                WorldController.world.get(tmpPosition).get(0) instanceof SymbolSmallP)){
                    position = new Position(position.row, position.column-1);
                    break;
                }
            }
        }
    }




}
