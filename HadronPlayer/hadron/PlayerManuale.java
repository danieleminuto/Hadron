package hadron;

import hadron.board.Board;
import hadron.board.ByteBoard;
import hadron.heuristic.GenericHeuristic;
import hadron.heuristic.Heuristic;
import hadron.heuristic.HeuristicBase;
import hadron.research.GameController;
import hadron.research.GameControllerImpl;
import hadron.research.NegaSort;
import hadron.research.Research;

public class PlayerManuale{
    private Heuristic heuristic;
    private GameController game;

    public PlayerManuale( Heuristic h) {
        this.heuristic = h;
    }


    public void start(String ip, int port) {
        Board board = new ByteBoard();
        Research algorithm = new NegaSort();
        game = new GameControllerImpl(algorithm, heuristic, board, (byte)0, 930);
        new MyCommunication(ip,port,game);
    }

    public static void main(String[] args) {
        Heuristic h = new HeuristicBase();
        PlayerManuale p1 = new PlayerManuale(h);
        p1.start("localhost",8901);
    }

    public GameController getGame() {
        return this.game;
    }
}