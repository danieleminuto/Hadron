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

    public int[] chiuse_pari_dispari(ArrayList<Node> mossePossibili, int col){
        int mosseChiuse=0;
        int aprePari=0;
        int apreDispari=0;
        for(Node n: mossePossibili){
            Board bo=n.getBoard();
            String move=n.getPreviousMove();
            char le=move.charAt(0);
            char[] alph="ABCDEFGHI".toCharArray();

            int riga=0;
            int colonna=Integer.parseInt(String.valueOf(move.charAt(1)));;
            for(int i=0;i<alph.length;i++){
                if(le==alph[i]){
                    riga=i;
                }
            }
            colonna--;

            boolean flag=true;
            if(riga==0){
                if(colonna==0){
                    if(bo.getCol(0,1)==-1 || bo.getCol(1,0)==-1)
                        flag=false;
                }
                else if(colonna==8){
                    if(bo.getCol(0,7)==-1 || bo.getCol(1,8)==-1)
                        flag=false;
                }
                else {
                    if (bo.getCol(0, colonna - 1) == -1 || bo.getCol(0, colonna+1) == -1 || bo.getCol(1,colonna)==-1 )
                        flag = false;
                }
            }
            else if(riga==8){
                if(colonna==0){
                    if(bo.getCol(8,1)==-1 || bo.getCol(7,0)==-1)
                        flag=false;
                }
                else if(colonna==8){
                    if(bo.getCol(8,7)==-1 || bo.getCol(7,8)==-1)
                        flag=false;
                }
                else {
                    if (bo.getCol(8, colonna - 1) == -1 || bo.getCol(8, colonna+1) == -1 || bo.getCol(7,colonna)==-1)
                        flag = false;
                }
            }
            else if(colonna==0){
                if (bo.getCol(riga-1, colonna) == -1 || bo.getCol(riga+1, colonna) == -1 || bo.getCol(riga,colonna+1)==-1 )
                    flag = false;
            }
            else if(colonna==8){
                if (bo.getCol(riga-1, colonna) == -1 || bo.getCol(riga+1, colonna) == -1 || bo.getCol(riga,colonna-1)==-1 )
                    flag = false;
            }
            else{
                if (bo.getCol(riga-1, colonna) == -1 || bo.getCol(riga+1, colonna) == -1 || bo.getCol(riga,colonna-1)==-1 || bo.getCol(riga,colonna+1)==-1 )
                    flag=false;
            }

            //verifica mossa chiusa come ieri
            if(flag)
                mosseChiuse++;
            else{
                if(n.getBoard().getSons((byte) col).size()%2==0)
                    aprePari++;
                else
                    apreDispari++;
            }
        }
        return new int[] {mosseChiuse,aprePari,apreDispari};
    }

    public int[] neutre_pos_neg(ArrayList<Node> mossePossibili,int col){
        int neutre=0;
        int pos=0;
        int neg=0;
        for(Node n:mossePossibili){
            Board bo=n.getBoard();
            String move=n.getPreviousMove();
            char le=move.charAt(0);
            char[] alph="ABCDEFGHI".toCharArray();

            int riga=0;
            int colonna=Integer.parseInt(String.valueOf(move.charAt(1)));;
            for(int i=0;i<alph.length;i++){
                if(le==alph[i]){
                    riga=i;
                }
            }
            colonna--;

            if(riga==0 && colonna==0 || riga==8 && colonna==8 || colonna==0 && riga==8|| colonna==8 && riga==0){
                neutre++;
            }
            else {
                try {
                    if(bo.getCol(riga-2,colonna)!=-1 && bo.getCol(riga-1,colonna-1)!=-1 && bo.getCol(riga-1,colonna+1)!=-1){
                        int c=0;
                        if( bo.getCol(riga-2,colonna)== col)
                            c++;
                        if(bo.getCol(riga-1,colonna-1)==col)
                            c++;
                        if(bo.getCol(riga-1,colonna+1)==col)
                            c++;
                        if(c==1)
                            pos++;
                        else
                            neg++;
                    }
                    else if(bo.getCol(riga+2,colonna)!=-1 && bo.getCol(riga+1,colonna-1)!=-1 && bo.getCol(riga+1,colonna+1)!=-1){
                        int c=0;
                        if( bo.getCol(riga-2,colonna)== col)
                            c++;
                        if(bo.getCol(riga-1,colonna-1)==col)
                            c++;
                        if(bo.getCol(riga-1,colonna+1)==col)
                            c++;
                        if(c==1)
                            pos++;
                        else
                            neg++;
                    }
                    else if(bo.getCol(riga,colonna-2)!=-1 && bo.getCol(riga-1,colonna-1)!=-1 && bo.getCol(riga+1,colonna-1)!=-1){
                        int c=0;
                        if( bo.getCol(riga,colonna-2)== col)
                            c++;
                        if(bo.getCol(riga-1,colonna-1)==col)
                            c++;
                        if(bo.getCol(riga+1,colonna-1)==col)
                            c++;
                        if(c==1)
                            pos++;
                        else
                            neg++;
                    }
                    else if(bo.getCol(riga,colonna+2)!=-1 && bo.getCol(riga-1,colonna+1)!=-1 && bo.getCol(riga+1,colonna+1)!=-1){
                        int c=0;
                        if( bo.getCol(riga,colonna+2)== col)
                            c++;
                        if(bo.getCol(riga-1,colonna+1)==col)
                            c++;
                        if(bo.getCol(riga+1,colonna+1)==col)
                            c++;
                        if(c==1)
                            pos++;
                        else
                            neg++;
                    }
                    else{
                        neg++;
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    neutre++;
                }
            }
        }
        return new int[] {neutre, pos, neg};
    }

    @Override

    //mosse possibili-> figli (mosse che posso scegliere io)
    //nipoti (mosse che può scegliere l'avversario) (deve tendere a 0)
    //pronipoti (mosse che posso scegliere io) (deve essere diverso da 0)
    //pronip2 (mosse che può scegliere l'avversario) (deve tendere a 0)
    public double evaluate(Board b, int col) {

        if(b.isFinal())
            return -1000000D;

        ArrayList<Node> mossePossibili=b.getSons((byte) col);


        if(mossePossibili.size()>40) {
            if (mossePossibili.size() % 2 == 0) {
                return mossePossibili.size() - 1000000D + new Random().nextInt(1000)/10.0;
            }
            return 1000000D - mossePossibili.size() - new Random().nextInt(1000)/10.0;
        }


        if(mossePossibili.size()>10){
            int[] ar=neutre_pos_neg(mossePossibili,col);
            int neutre=ar[0];
            int pos=ar[1];
            int neg=ar[2];
            return 1000000D - (2 * 1000000D * (((neutre+neg)/2) / mossePossibili.size())) - (mossePossibili.size() - pos) * 100000;
        }

        int[] ar=chiuse_pari_dispari(mossePossibili,col);
        int mosseChiuse=ar[0];
        int aprePari=ar[1];
        int apreDispari=ar[2];

        //deve preferire path pari se le mosse chiuse sono pari e viceversa
        if(mosseChiuse%2==0){
            return 1000000D - (2 * 1000000D * (apreDispari / mossePossibili.size())) - (mossePossibili.size() - aprePari) * 100000;
        }
        return 1000000D - (2 * 1000000D * (aprePari / mossePossibili.size())) - (mossePossibili.size() - apreDispari) * 100000;



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
