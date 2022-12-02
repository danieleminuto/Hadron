package hadron.heuristic;

import hadron.board.Board;
import hadron.research.Node;

import java.util.ArrayList;

public class HeuristicBase implements Heuristic {
    private static final long serialVersionUID = 5783084660998719046L;

    /**
     * Costruttore euristica
     */
    public HeuristicBase() {
    }


    @Override
    public double evaluate(Board b, int col) {

        if(b.isFinal())
            return -1;

        ArrayList<Node> mossePossibili=b.getSons((byte) col);
        for(Node n:mossePossibili){
            if(n.getBoard().isFinal())
                return 1;
        }

        //if(mossePossibili.size()>5){
           double min=Double.POSITIVE_INFINITY;
            for(Node n: mossePossibili){
                ArrayList<Node> nipoti=n.getBoard().getSons((byte) col);
                if(nipoti.size()<min)
                    min=nipoti.size();
            }
            return 1-min/100;
       // }

/*
        if(mossePossibili.size()>20){
            for(Node n:mossePossibili) {
                if (n.getBoard().isFinal())
                    return -1;
            }
            double coeff=0;
            if(mossePossibili.size()%2==1)
                coeff=0.01;
            else
                coeff=0.1;
            return 1-coeff;
        }
        else{
            for(Node n:mossePossibili){
                if(n.getBoard().isFinal())
                    return -1;
                else{
                    ArrayList<Node> proNipoti=n.getBoard().getSons((byte) col);
                    for(Node nip:proNipoti)
                        if(nip.getBoard().isFinal())
                            return 1;
                }
            }

        }*/
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
