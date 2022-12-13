package hadron.heuristic;

import hadron.board.Board;

public class GenericHeuristic implements Heuristic {
	private static final long serialVersionUID = 5783084660998719046L;

	/**
	 * Costruttore euristica
	 */
	public GenericHeuristic() {
	}


	@Override
	public double evaluate(Board b, int col) {
		// Se la hadron.board da una vittoria vuol dire che il giocatore attuale è rimasto senza mosse
		// col è il vincitore
		if(b.isFinal())
			return -1000000D;
        /*if(valore>0){
            valore--;
            return new Random().nextInt(1000);
        }*/

		int size=b.getSons((byte)col).size();
		if(size%2==0){
			//return -1000+size;
			return size-1000;
		}
		return 1000-size;
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
