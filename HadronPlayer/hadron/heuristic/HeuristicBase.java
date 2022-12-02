package hadron.heuristic;

import hadron.board.Board;

public class HeuristicBase implements Heuristic {
    private static final long serialVersionUID = 5783084660998719046L;

    /**
     * Costruttore euristica
     */
    public HeuristicBase() {
    }


    private double mosseValide(Board b) {
        int ret=0;
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                if(b.validMove(i, j))
                    ret--;

            }
        }
        return ret;
    }

    @Override
    public double evaluate(Board b, int col) {


        // Se la hadron.board da una vittoria vuol dire che il giocatore attuale è rimasto senza mosse
        // col è il vincitore

        if(b.isFinal())
            return -1000000D;
        else
            return mosseValide(b);
    }


    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof GenericHeuristic) ) return false;
        GenericHeuristic other = (GenericHeuristic) obj;
        return other.toString().equals(this.toString());
    }
}
