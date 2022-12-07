package hadron.heuristic;

import hadron.board.Board;
import hadron.research.Node;

import java.util.ArrayList;
import java.util.Random;

public class HeuristicBase implements Heuristic {
    private static final long serialVersionUID = 5783084660998719046L;

    /**
     * Costruttore euristica
     */
    public HeuristicBase() {
    }


    @Override

    //mosse possibili-> figli (mosse che posso scegliere io)
    //nipoti (mosse che può scegliere l'avversario) (deve tendere a 0)
    //pronipoti (mosse che posso scegliere io) (deve essere diverso da 0)
    //pronip2 (mosse che può scegliere l'avversario) (deve tendere a 0)
    public double evaluate(Board b, int col) {

        if(b.isFinal())
            return -1;

        ArrayList<Node> mossePossibili=b.getSons((byte) col);

        if(mossePossibili.size()>3){
           double min=Double.POSITIVE_INFINITY;
            for(Node n: mossePossibili){
                if(n.getBoard().isFinal())
                    return 1;

                ArrayList<Node> nipoti=n.getBoard().getSons((byte) col);
                if(nipoti.size()<min)
                    min=nipoti.size();
            }
            return 1-1/min+(new Random().nextInt(10)/10000);
        }


        double min=Double.POSITIVE_INFINITY;
        for(Node n: mossePossibili){
            if(n.getBoard().isFinal())
                return 1;

            ArrayList<Node> nipoti=n.getBoard().getSons((byte) col);
            if(nipoti.size()<min)
                min=nipoti.size();
        }
        double ret= 1-1/min+(new Random().nextInt(10)/10000);
        if(ret<0.5){
            return ret-0.0001;
        }
        return ret+0.0001;




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
