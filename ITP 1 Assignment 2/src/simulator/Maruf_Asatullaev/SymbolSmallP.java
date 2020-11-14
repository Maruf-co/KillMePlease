package simulator.Maruf_Asatullaev;

import simulator.do_not_change.Aggressive;
import simulator.do_not_change.Position;
import simulator.do_not_change.SmallCase;
import simulator.do_not_change.Symbol;

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
        if(randNumber == 0 && this.position.column != 0){ // Left
            this.position.column -= 1;
        }else if(randNumber == 1 && this.position.column != 9){ // Right
            this.position.column += 1;
        }else if(randNumber == 2 && this.position.row != 0){ // Up
            this.position.row -= 1;
        }else{ // Down
            this.position.row += 1;
        }
    }

    @Override
    public void die() {

    }

    @Override
    public void upgrade() {

    }
    @Override
    public void attackSmart() {

    }

}