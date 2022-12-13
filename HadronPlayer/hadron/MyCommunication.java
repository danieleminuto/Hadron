package hadron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Locale;

import hadron.board.Board;
import hadron.research.GameController;

/**
 * Classe che si occupa della comunicazione con il server
 */
public class MyCommunication {

    private String serverIP;		// IP del server
    private int serverPort;			// porta del server

    private Socket server;			// socket di comunicazione
    private BufferedReader in;		// buffer in ingresso
    private PrintWriter out;		// buffer in uscita

    private Listener listener; 		// Thread che ascolta i nuovi messaggi del server
    private String message; 		// Messaggio appena ricevuto dal server

    private GameController game;	// GameController, responsabile della gestione del gioco
    private int col;				// colore giocatore

    //Solo per debugging in caso di INVALID_MOVE
    private LinkedList<Board> boards = new LinkedList<>();
    private LinkedList<String> moves = new LinkedList<>();

    /**
     * Costruttore, apre il socket verso il server e crea gli stream in input ed output
     * @param ip ip server
     * @param port porta server
     * @param game GameController
     */
    public MyCommunication(String ip, int port, GameController game) {
        this.serverIP = ip;
        this.serverPort = port;

        // Collegamento al server
        try{
            server = new Socket( serverIP, serverPort );
            in = new BufferedReader( new InputStreamReader( server.getInputStream() ) );
            out = new PrintWriter( server.getOutputStream(), true );
            System.out.println("Connected to Server");

            listener = new Listener();
            listener.start();

            this.game = game;
            boards.add( game.getBoard() );
        }catch( IOException ioe ){
            System.out.println(ioe);
        }
    }

    public String converti(String move){
        char l=move.charAt(0);
        int n=Integer.parseInt(move.charAt(1)+"");

        int le=0;
        char nu=' ';

        if(l=='a')
            le=1;
        else if(l=='b')
            le=2;
        else if(l=='c')
            le=3;
        else if(l=='d')
            le=4;
        else if(l=='e')
            le=5;
        else if(l=='f')
            le=6;
        else if(l=='g')
            le=7;
        else if(l=='h')
            le=8;
        else if(l=='i')
            le=9;

        if(n==1)
            nu='i';
        else if(n==2)
            nu='h';
        else if(n==3)
            nu='g';
        else if(n==4)
            nu='f';
        else if(n==5)
            nu='e';
        else if(n==6)
            nu='d';
        else if(n==7)
            nu='c';
        else if(n==8)
            nu='b';
        else if(n==9)
            nu='a';

        return String.valueOf(nu)+le+"";
    }

    public static String convertiPerLudii(String move){


        char l=move.toLowerCase(Locale.ROOT).charAt(0);
        int n=Integer.parseInt(String.valueOf(move.charAt(1)));

        int nu=0;
        char le=' ';

        switch (n){
            case 1:
                le= 'a';
                break;
            case 2:
                le= 'b';
                break;
            case 3:
                le= 'c';
                break;
            case 4:
                le= 'd';
                break;
            case 5:
                le= 'e';
                break;
            case 6:
                le= 'f';
                break;
            case 7:
                le= 'g';
                break;
            case 8:
                le= 'h';
                break;
            case 9:
                le= 'i';
                break;
        }

        switch (l){
            case 'a':
                nu= 9;
                break;
            case 'b':
                nu= 8;
                break;
            case 'c':
                nu= 7;
                break;
            case 'd':
                nu= 6;
                break;
            case 'e':
                nu= 5;
                break;
            case 'f':
                nu= 4;
                break;
            case 'g':
                nu= 3;
                break;
            case 'h':
                nu= 2;
                break;
            case 'i':
                nu= 1;
                break;
        }

        return String.valueOf(le)+nu+"";
    }

    public Listener getListener() {
        return this.listener;
    }

    /**
     * Oggetto che si occupa di leggere e gestire i messaggi del server
     */

    class Listener extends Thread{



        public void run() {
            String myMove = null;

            try {
                while(true) {
                    message = in.readLine();
                    System.out.println("\tCOL "+col+" - SERVER: "+message);

                    if(message.equals("YOUR_TURN")) {
                        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                        myMove= br.readLine();

                        //per giocare con la scacchiera di ludii
                        myMove=converti(myMove);
                        out.println("MOVE "+myMove);
                        out.flush();

                        System.out.println(game.getBoard());
                        moves.add(myMove);
                        boards.add( game.getBoard() );
                    }else if(message.contains("OPPONENT_MOVE")) {
                        String oppMove = message.substring(14);

                        //per ludii
                        System.out.println("mossa per ludii "+convertiPerLudii(oppMove));
                        game.updateGame(oppMove);

                        moves.add(oppMove);
                        boards.add( game.getBoard() );

                    }else if(message.contains("WELCOME"))
                        col = game.setCol(message.substring(8));

                    else if(message.equals("VALID_MOVE"))
                        continue;

                    else if(message.contains("MESSAGE"))
                        continue;

                    else if(message.equals("ILLEGAL_MOVE")) {
                        System.err.println("ILLEGAL_MOVE "+myMove);
                        printGameTrace();
                        break;
                    }else if(message.equals("TIMEOUT"))
                        break;

                    else if(message.equals("VICTORY")) {
                        break;
                    }
                    else if(message.equals("TIE") || message.equals("DEFEAT")) {
                        break;
                    }

                }
            }catch(Exception e) {
                System.out.println("-----------ERRORE-----------");
                e.printStackTrace();
                //stayOn();
            }
        }

    }

    private void printGameTrace() {
        System.out.println("Configurazione iniziale:");
        System.out.println( boards.removeFirst() );
        if( game.getCol() == 1 ) { //inizio io
            System.out.println("Sono il Bianco, inizio io");
            while( !boards.isEmpty() ) {
                System.out.println("La mia mossa: "+ (moves.isEmpty()?null:moves.removeFirst()) );
                System.out.println("La mia scacchiera aggiornata:\n"+ (boards.isEmpty()?null:boards.removeFirst() ) );
                System.out.println("La sua mossa: "+ (moves.isEmpty()?null:moves.removeFirst()) );
                System.out.println("La mia scacchiera aggiornata:\n"+ (boards.isEmpty()?null:boards.removeFirst() ) );
            }
        }else {
            System.out.println("Sono il Nero");
            while( !boards.isEmpty() ) {
                System.out.println("La sua mossa: "+ (moves.isEmpty()?null:moves.removeFirst()) );
                System.out.println("La mia scacchiera aggiornata:\n"+ (boards.isEmpty()?null:boards.removeFirst() ) );
                System.out.println("La mia mossa: "+ (moves.isEmpty()?null:moves.removeFirst()) );
                System.out.println("La mia scacchiera aggiornata:\n"+ (boards.isEmpty()?null:boards.removeFirst() ) );
            }
        }
    }
}