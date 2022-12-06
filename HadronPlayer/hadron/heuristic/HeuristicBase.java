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

    //mosse possibili-> figli (mosse che posso scegliere io)
    //nipoti (mosse che può scegliere l'avversario) (deve tendere a 0)
    //pronipoti (mosse che posso scegliere io) (deve essere diverso da 0)
    //pronip2 (mosse che può scegliere l'avversario) (deve tendere a 0)
    public double evaluate(Board b, int col) {

        if(b.isFinal())
            return -1;

        ArrayList<Node> mossePossibili=b.getSons((byte) col);
        for(Node n:mossePossibili){
            if(n.getBoard().isFinal())
                return 1;
        }

        if(mossePossibili.size()>3){
           double min=Double.POSITIVE_INFINITY;
            for(Node n: mossePossibili){
                ArrayList<Node> nipoti=n.getBoard().getSons((byte) col);
                if(nipoti.size()<min)
                    min=nipoti.size();
            }
            return 1-min/100;
        }


        double min=Double.POSITIVE_INFINITY;
        for(Node n: mossePossibili){
            ArrayList<Node> nipoti=n.getBoard().getSons((byte) col);
            if(nipoti.size()==0)
                return 1;

            for(Node nip: nipoti){
                ArrayList<Node> pronip=nip.getBoard().getSons((byte) col);
                if(pronip.size()==0)
                    return -1;
                for(Node pron: pronip){
                    ArrayList<Node> pp=pron.getBoard().getSons((byte) col);
                    if(pp.size()==0)
                        return 1;

                    if(pp.size()<min)
                        min=pp.size();
                }
            }
        }
        return 1-min/100;


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
