
package com.example.singh.proj4;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    //Declare variables
    private static final int START_GAME = 1;
    private static final int GET_1st_MOVE = 2;
    private static final int Get_Next_Move = 3;
    private static final int UPDATE_MOVE_1_ON_UI = 4;
    private static final int UPDATE_MOVE_2_ON_UI = 5;
    private static final int RESTART_GAME = 6;


    /* Initially the holes are all empty */

    private int[] mHoleIds = new int[50];
    private int winningHole ;
    private int winningHoleGroup;
    private int winnerFound = 0;                            // player1 winner winnerFound =1  if player 2 wins its -1
    private String textForEachMove;
    private boolean nearMiss;
    private boolean nearGroup;
    private boolean bigMiss;



    //handlers and threads
    Handler uiHandler;
    Handler p1Handler;
    Handler p2Handler;
    Thread player1, player2;
    private ListView listHoles;
    private holeAdapter mHoleAdapter;



    //Data structure for players
    HashMap<Integer, Integer> holeGroup = new HashMap<Integer, Integer>();      //to keep track of group of holes
    Stack p1PrevGuesses = new Stack();
    Stack p2PrevGuesses = new Stack();
    int[] group = new int[5];
    ArrayList<Integer> p2ToVisitGroups = new ArrayList<Integer>(5);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int k= 0; k < 5; k++){                     // available groups
            group[k] = k;
        }
        Random rwinHole = new Random();                 // randomly deciding the winning/target hole
        winningHole = rwinHole.nextInt(50);
        Arrays.fill(mHoleIds, R.drawable.circleshape);   // filling mholeIds with drawable to mark holes
        mHoleIds[winningHole] = R.drawable.winningcircle; // marking the winning hole with different drawable
        fillHash();                                        // Hash table has keys as holes and values as groups
        winningHoleGroup = holeGroup.get(winningHole);
        nearMiss = false;
        nearGroup = false;
        bigMiss = false;

        listHoles = (ListView) findViewById(R.id.listitem);
        mHoleAdapter = new holeAdapter(this, mHoleIds);
        listHoles.setAdapter(mHoleAdapter);
        ((TextView) findViewById(R.id.winner)).setText("");
        ((TextView)findViewById(R.id.WinningHole)).setText("Winning Hole "+winningHole + " Group " + winningHoleGroup ); // set text to see the winning hole
        //View shape1 = (View)findViewById(R.id.shape1);
        //shape1.setBackgroundResource(R.drawable.solidcircle);


        /*  Create handler for UI Thread*/
        uiHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Message message;
                switch (msg.what) {

                    case START_GAME: {
                            /* Send message to player 1 to start game */
                        Log.i("Main", "Game started!!!!!!");
                        message = p1Handler.obtainMessage(Get_Next_Move);
                        p1Handler.sendMessage(message);
                        break;
                    }

                    case UPDATE_MOVE_1_ON_UI: {

                        /* Update Player 1 move on UI */
                        makePlayer1Move(msg.arg1);
                        Log.i("Main", "Move 1 updated!!!");
                        if (winnerFound == 1) {                         // Player 1 wins
                            player1.interrupt();                        // interrupting the execution of both the threads and exiting the game
                            player2.interrupt();
                            uiHandler.removeCallbacksAndMessages(null);
                            p1Handler.removeCallbacksAndMessages(null);
                            p2Handler.removeCallbacksAndMessages(null);
                            p1Handler.getLooper().quitSafely();
                            p2Handler.getLooper().quitSafely();
                            ((TextView) findViewById(R.id.winner)).setText(textForEachMove);        // set text = Player 1 won
                            break;
                        }

                        else if (winnerFound == -1){                    // Player 2 wins
                            player1.interrupt();                            // interrupting the execution of both the threads and exiting the game
                            player2.interrupt();
                            uiHandler.removeCallbacksAndMessages(null);
                            p1Handler.removeCallbacksAndMessages(null);
                            p2Handler.removeCallbacksAndMessages(null);
                            p1Handler.getLooper().quitSafely();
                            p2Handler.getLooper().quitSafely();
                            ((TextView) findViewById(R.id.winner)).setText(textForEachMove);
                            break;
                        }

                            ((TextView) findViewById(R.id.winner)).setText(textForEachMove);    // no winner setting text about the current player and its move

                        /* Post a runnable to Player 2 to wait for 2 seconds */
                        p2Handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.i("Thread 2", " Sleeping");
                                    Thread.sleep(4000);
                                } catch (InterruptedException e) {
                                    Log.i("Thread2","*****THREAD INTERRUPTED TO RESTART GAME*****");
                                    e.printStackTrace();
                                }
                            }
                        });

                            /* Notify Player 2 to get next move */
                        message = p2Handler.obtainMessage(Get_Next_Move);
                        p2Handler.sendMessage(message);
                        break;

                    }

                    case UPDATE_MOVE_2_ON_UI: {
                            /* Update Player 2 move on UI */
                        makePlayer2Move(msg.arg1);
                        Log.i("Main", "Move 2 updated!!!");

                             /* Check if player 2 has won */
                        if( winnerFound == -1){                             //Player 2 won
                            Log.i("Exit","Game");
                            player1.interrupt();
                            player2.interrupt();
                            uiHandler.removeCallbacksAndMessages(null);
                            p1Handler.removeCallbacksAndMessages(null);
                            p2Handler.removeCallbacksAndMessages(null);
                            p1Handler.getLooper().quitSafely();
                            p2Handler.getLooper().quitSafely();
                            ((TextView)findViewById(R.id.winner)).setText(textForEachMove);
                            break;
                        }

                        else if (winnerFound == 1){                 //Player 1 won
                            Log.i("Exit","Game");
                            player1.interrupt();
                            player2.interrupt();
                            uiHandler.removeCallbacksAndMessages(null);
                            p1Handler.removeCallbacksAndMessages(null);
                            p2Handler.removeCallbacksAndMessages(null);
                            p1Handler.getLooper().quitSafely();
                            p2Handler.getLooper().quitSafely();
                            ((TextView)findViewById(R.id.winner)).setText(textForEachMove);
                            break;
                        }

                            ((TextView)findViewById(R.id.winner)).setText(textForEachMove);

                            /* Post a runnable to Player 1 to wait for 2 seconds */
                        p1Handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.i("Thread 2", " Sleeping");
                                    Thread.sleep(4000);
                                } catch (InterruptedException e) {
                                    Log.i("Main","*****THREAD INTERRUPTED TO RESTART GAME*****");
                                    e.printStackTrace();
                                }
                            }
                        });

                            /* Notify Player 1 to get next move */
                        message = p1Handler.obtainMessage(Get_Next_Move);
                        p1Handler.sendMessage(message);
                        break;

                    }

                    case RESTART_GAME:{
                        if(p1Handler != null && p2Handler != null){
                            p1Handler.removeCallbacksAndMessages(null);
                            p2Handler.removeCallbacksAndMessages(null);

                            try {
                                player1.join();
                                player2.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        break;

                    }


                    default:
                        break;
                }
            }
        };
    /* Set click listener for start button*/
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (player1 != null && player2 != null) {
                    player1.interrupt();
                    player2.interrupt();
                    uiHandler.removeCallbacksAndMessages(null);
                    p1Handler.removeCallbacksAndMessages(null);
                    p2Handler.removeCallbacksAndMessages(null);
                    p1Handler.getLooper().quitSafely();
                    p1Handler.getLooper().quitSafely();
                    resetGameForRestart();

                }



         /* Create player 1 thread */
                player1 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        /* Prepare Looper */
                        Looper.prepare();

                        /* Set handler for player 1 thread */
                        p1Handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                Message message;
                                switch (msg.what) {
//                                    case GET_1st_MOVE: {
//
//                                        /* Get move for player 1 and send move as argument in the message
//                                        * Send message to UI thread to update move on UI */
//                                        message = uiHandler.obtainMessage(UPDATE_MOVE_1_ON_UI);
//                                        message.arg1 = getMove1ForPlayer1();
//                                        uiHandler.sendMessage(message);
//                                        break;
//                                    }


                                    case Get_Next_Move:{
                                        if (p1PrevGuesses.empty()){             //if its player 1's first move
                                            /* Get move for player 1 and send move as argument in the message
                                        * Send message to UI thread to update move on UI */
                                            message = uiHandler.obtainMessage(UPDATE_MOVE_1_ON_UI);
                                            message.arg1 = getMove1ForPlayer1();
                                            uiHandler.sendMessage(message);
                                        }

                                        else {                  // all moves except 1st move
                                            message = uiHandler.obtainMessage(UPDATE_MOVE_1_ON_UI);
                                            message.arg1 = getNextMoveForPlayer1();
                                            uiHandler.sendMessage(message);
                                        }
                                        break;

                                    }

                                    default:
                                        break;
                                }
                            }

                        };
                        Log.i("P1", "Handler created");
                        /*  Send message to UI thread to start the game */
                        Message message = uiHandler.obtainMessage(START_GAME);
                        uiHandler.sendMessage(message);
                        Looper.loop();
                    }
                });
                player1.start();

                player2 = new Thread(new Runnable() {
                    @Override
                    public  void run() {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                         /* Prepare Looper */
                        Looper.prepare();
                         /* Set handler for player 1 thread */
                        p2Handler = new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                Message message;
                                switch (msg.what){
                                    case Get_Next_Move:{
                                        if(p2PrevGuesses.empty()){          //if its player 2's first move then its random
                                            message = uiHandler.obtainMessage(UPDATE_MOVE_2_ON_UI);
                                            message.arg1 = getMove1ForPlayer2();
                                            uiHandler.sendMessage(message);
                                        }
                                        else {                  // for all other moves of player 2
                                            message = uiHandler.obtainMessage(UPDATE_MOVE_2_ON_UI);
                                            message.arg1 = getNextMoveForPlayer2();
                                            uiHandler.sendMessage(message);
                                        }
                                        break;
                                    }
                                    default:{

                                        break;
                                    }
                                }
                            }
                        };

                        Log.i("P2", "Handler created");
                        Looper.loop();

                    }
                });
                player2.start();
            }
        });
    }

    /*  Method to get random move for player 1 */
    public  int getMove1ForPlayer1(){
        Log.i("Thread  1 ", "move1 player 2");
        int move;
        int moveGroup;
        Random r = new Random();
        move = r.nextInt(50);
        moveGroup = holeGroup.get(move);
        setStatusText(move,moveGroup,"P1");
        Log.i("M1P1",String.valueOf(move));
        return move;
    }



    /*  Method to get random move for player 2 */
    public  int getMove1ForPlayer2(){
        Log.i("Thread  2 ", "move1 player 2");
        int move;
        int moveGroup;
        Random r = new Random();
        move = r.nextInt(50);
        moveGroup =holeGroup.get(move);
        for (int i = 0; i < 5; i++) {               //for next move of p2 ///IMPORTANT
            p2ToVisitGroups.add((i + moveGroup) % 5);
        }
        setStatusText(move,moveGroup,"P2");
        Log.i("M1P2",String.valueOf(move));
        return move;
    }

    /* Method for all other moves of player1*/
    public int getNextMoveForPlayer1(){
        int nextMove;
        int nextMoveGroup;
        ArrayList<Integer> availableMoveFor1 = new ArrayList<Integer>(50);

        for (int i=0;i<50;i++){
            availableMoveFor1.add(i);
        }

        availableMoveFor1.removeAll(p1PrevGuesses);
        Random rmove2Player1 = new Random();
        nextMove = availableMoveFor1.get(rmove2Player1.nextInt(availableMoveFor1.size()));
        nextMoveGroup = holeGroup.get(nextMove);
        setStatusText(nextMove,nextMoveGroup,"P1");
        Log.i("MP1",String.valueOf(nextMove));
        return nextMove;

    }

    public int getNextMoveForPlayer2(){
        int nextMoveGroup = p2ToVisitGroups.get(0);
        int nextMove;
        ArrayList<Integer> availableMoveFor2;
        availableMoveFor2 = getKeysByValue(holeGroup,nextMoveGroup);
        availableMoveFor2.removeAll(p2PrevGuesses);
        if (availableMoveFor2.isEmpty()){
            p2ToVisitGroups.remove(Integer.valueOf(nextMoveGroup));
            nextMoveGroup=p2ToVisitGroups.get(0);
            availableMoveFor2 = getKeysByValue(holeGroup,nextMoveGroup);
            availableMoveFor2.removeAll(p2PrevGuesses);
        }
        nextMove = availableMoveFor2.get(0);
        setStatusText(nextMove,nextMoveGroup,"P2");
        Log.i("MP2",String.valueOf(nextMove));
        return nextMove;

    }

    /*method to get holes for given hole group*/

    public static <T, E> ArrayList<Integer> getKeysByValue(Map<T, E> map, E value) {
        //Set keys = new HashSet();
        ArrayList<Integer> keys = new ArrayList<Integer>();
        for (Map.Entry entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                keys.add((Integer) entry.getKey()); //no break, looping entire hashtable
            }
        }
        return keys;
    }

    /*  Method to update Player 1 move on UI */
    public void makePlayer1Move(int moveToIndex){
        Log.i("p1 make",String.valueOf(moveToIndex));
        p1PrevGuesses.push(moveToIndex);
        updateUIState(moveToIndex,"P1");

    }
    public void makePlayer2Move(int moveToIndex){
        Log.i("p2 make",String.valueOf(moveToIndex));
        p2PrevGuesses.push(moveToIndex);
        updateUIState(moveToIndex,"P2");

    }

    /* Update holes' position on UI*/
    public void updateUIState(int position, String currentPlayer){
        Log.i("updating",String.valueOf(position) + "player"+String.valueOf(currentPlayer));
        ImageView view;
        if (currentPlayer == "P1")
        mHoleAdapter.mHoleIds[position]=R.drawable.solidcircle;
        else mHoleAdapter.mHoleIds[position]=R.drawable.circleplayer2;
        mHoleAdapter.notifyDataSetChanged();
        listHoles.invalidateViews();
        listHoles.smoothScrollToPosition(position);
        checkWinner(position,currentPlayer);
    }

    public void checkWinner(int position, String currentPlayer){
        Log.i("p1", String.valueOf(p1PrevGuesses.search(position)));
        Log.i("p2",String.valueOf(p2PrevGuesses.search(position)));
        int temp;
        temp = p1PrevGuesses.search(position);
        if (winningHole == position){
            Log.i("Exit","winning hole"+ String.valueOf(position));
            if (currentPlayer=="P1") winnerFound = 1;
            else winnerFound = -1;
            textForEachMove="JACKPOT!!!! " + currentPlayer +" wins ";
        }

        else if((currentPlayer =="P1" )&&(p2PrevGuesses.search(position)> 0)) //-1 means not found that position anyhtng greater than 0 means found
        {
            Log.i("Exit","p2 wins"+ String.valueOf(position));
            winnerFound = -1; // p2 wins
            textForEachMove = "CATASTROPHE!!!! " + currentPlayer + " wins ";
        }

        else if ((currentPlayer =="P2" )&&(p1PrevGuesses.search(position)> 0) ){
            Log.i("Exit","p1 wins"+ String.valueOf(position));
            winnerFound = 1;    //p1 wins
            textForEachMove = "CATASTROPHE!!!! " + currentPlayer + " wins ";

        }
        else {Log.i("Exit","continue game"+ String.valueOf(position));
            winnerFound = 0;} //no winner continue game
    }

    /*set text method*/
    public void setStatusText(int move, int moveGroup, String player){
        if (moveGroup == winningHoleGroup) {
            Log.i("set","same group");
            textForEachMove = "NEAR MISS!!!! "+ player +" Move: " + move + " Move group: " + moveGroup;
            nearMiss = true;
            bigMiss = false;
            nearGroup = false;
        }
        else if(( winningHoleGroup== moveGroup+1)||(winningHoleGroup== moveGroup - 1)){
            Log.i("set","near group");
            textForEachMove = "NEAR GROUP!!!! "+ player +" Move: " + move + " Move group: " + moveGroup;
            nearMiss = false;
            bigMiss = false;
            nearGroup = true;
        }
        else {
            Log.i("set","Big miss");
            textForEachMove = "BIG MISS!!!! "+ player +" Move: " + move + " Move group: " + moveGroup;
            nearMiss = false;
            bigMiss = true;
            nearGroup = false;
        }
    }

    public void resetGameForRestart(){
        p1PrevGuesses.clear();
        p2PrevGuesses.clear();
        p2ToVisitGroups.clear();
        Random rwinHole = new Random();                 // randomly deciding the winning/target hole
        winningHole = rwinHole.nextInt(50);
        Arrays.fill(mHoleIds, R.drawable.circleshape);   // filling mholeIds with drawable to mark holes
        mHoleIds[winningHole] = R.drawable.winningcircle; // marking the winning hole with different drawable
        fillHash();                                        // Hash table has keys as holes and values as groups
        winningHoleGroup = holeGroup.get(winningHole);
        ((TextView)findViewById(R.id.winner)).setText("");
        ((TextView)findViewById(R.id.WinningHole)).setText("Winning Hole "+winningHole + " Group " + winningHoleGroup );
        mHoleAdapter.notifyDataSetChanged();
        listHoles.invalidateViews();
    }



    public void fillHash(){
        holeGroup.put(0,0);holeGroup.put(1,0);holeGroup.put(2,0);holeGroup.put(3,0);holeGroup.put(4,0);
        holeGroup.put(5,0);holeGroup.put(6,0);holeGroup.put(7,0);holeGroup.put(8,0);holeGroup.put(9,0);
        holeGroup.put(10,1);holeGroup.put(11,1);holeGroup.put(12,1);holeGroup.put(13,1);holeGroup.put(14,1);
        holeGroup.put(15,1);holeGroup.put(16,1);holeGroup.put(17,1);holeGroup.put(18,1);holeGroup.put(19,1);
        holeGroup.put(20,2);holeGroup.put(21,2);holeGroup.put(22,2);holeGroup.put(23,2);holeGroup.put(24,2);
        holeGroup.put(25,2);holeGroup.put(26,2);holeGroup.put(27,2);holeGroup.put(28,2);holeGroup.put(29,2);
        holeGroup.put(30,3);holeGroup.put(31,3);holeGroup.put(32,3);holeGroup.put(33,3);holeGroup.put(34,3);
        holeGroup.put(35,3);holeGroup.put(36,3);holeGroup.put(37,3);holeGroup.put(38,3);holeGroup.put(39,3);
        holeGroup.put(40,4);holeGroup.put(41,4);holeGroup.put(42,4);holeGroup.put(43,4);holeGroup.put(44,4);
        holeGroup.put(45,4);holeGroup.put(46,4);holeGroup.put(47,4);holeGroup.put(48,4);holeGroup.put(49,4);


    }
}
