/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Rémy
 */
class IA extends Player{
    protected Location result;
    protected int level;
    protected Game futurGame;
    
    public IA(String name, int color, int level) {
        super(name, color);
        this.level  = level;
    }
    /**
     * Get the number of modifications on a given axis
     * @param board game board
     * @param color currentPlayer color
     * @param i row location
     * @param j column location
     * @param iAxis row direction
     * @param jAxis column direction
     * @return number of rotate pieces
     */
    private int getAxisModifications(int[][] board, int color, int i, int j, int iAxis, int jAxis){
        int iNext = i+iAxis,jNext = j+jAxis;
        if(iNext>=0&&jNext>=0&&iNext<Game.gameSize&&jNext<Game.gameSize){
            if(board[iNext][jNext]==color)
                return 1;
            if(board[iNext][jNext]==-color)
                return 1+getAxisModifications(board,color, iNext, jNext, iAxis, jAxis);
        }
        return 0;
    }
    private int getNeighborsModifications(Location loc, int[][] board, int color){
        int res = 0;
        Set<Location> neighbors = loc.getNeighbors(board,-color);
        for(Location l:neighbors){
            res+=getAxisModifications(board, color, l.row, l.col, l.row-loc.row, l.col-loc.col);
        }
        return res;
    }
    private int getMaxModifications(Game game){
        int i,max = 0;
        for(Location l:game.placeable){
            if((i=getNeighborsModifications(l, game.board, game.currentPlayer.color))>max){
                max=i;
            }
        }
        return max;
    }
    
    private Location getMaxModificationsLocation(Game game, Set<Location> candidates){
        int i,max = 0;
        Location res = new Location();
        for(Location l:candidates){
            if((i=getNeighborsModifications(l, game.board, game.currentPlayer.color))>max){
                max=i;
                res = l;
            }
        }
        return res;
    }
    
    
    private int myIAMinMax(Game game, int depth, int depthmax){
//        for (int i = 0; i < depthmax-depth; i++) {
//                System.out.print(" ");
//            }
        if(depth < 1 || !game.isRunningGame()){
            return evaluation(game, color);
        }
        int max = Integer.MIN_VALUE;
//        System.out.println("Depth "+ depth + " -> " + max);
        for(Location l:game.placeable){
            futurGame = new Game(game);
            futurGame.updateBoard(l.row, l.col);
            int score = -myIAMinMax(futurGame, depth - 1, depthmax);
            futurGame = game;
            if(score > max){
                max = score;
                if(depth == depthmax)
                    result = l;
            }
        }
        return max;
    }
    
        private int alphaBetaRec(Game game, int eval, int depth, int depthmax, int alpha, int beta){
//        for (int i = 0; i < depthmax-depth; i++) {
//                System.out.print(" ");
//            }
        if(depth < 1 || !game.isRunningGame()){
            return evaluation(game, eval);
        }
//        System.out.println("Depth "+ depth + " -> " + alpha + ";"+ beta);
        for(Location l:game.placeable){
            futurGame = new Game(game);
            futurGame.updateBoard(l.row, l.col);
            int score = -alphaBetaRec(futurGame, eval ,depth - 1, depthmax, -beta, -alpha);
            futurGame = game;
            if(score > alpha){
                alpha = score;
                if(depth == depthmax)
                    result = l;
                if(alpha >= beta){
//                    System.out.println("break");
                    break;
                }
            }
       }
        return alpha;
    }
    private Location alphaBeta(Game game, int eval, int depth){
        alphaBetaRec(game, eval, depth, depth, Integer.MIN_VALUE+1, Integer.MAX_VALUE);
        return result;
    }
    
    private int evalstat(Game g){
        int [][][] tab;
        if(g.currentPlayer.equals(g.player1)){
            tab = g.p1Weights;
        }
        else{
            tab = g.p2Weights;
        }
        int c = g.currentPlayer.coups.size();
        int max = 0;
        for(Location l:g.placeable){
            max+= tab[c][l.row][l.col];
        }
        return max;
    }
    
    private Location statIA(Game g){
        int [][][] tab;
        Location res = new Location();
        if(g.currentPlayer.equals(g.player1)){
            tab = g.p1Weights;
        }
        else{
            tab = g.p2Weights;
        }
        
        int c = g.currentPlayer.coups.size();
        int max = Integer.MIN_VALUE;
        for(Location l:g.placeable){
            int tmp = tab[c][l.row][l.col];
            if(tmp > max){
                max = tmp;
                res=l;
            }
        }
//        List<Location> resultList = new ArrayList<>();
//        for(Location l:g.placeable){
//            int tmp = tab[c][l.row][l.col];
//            if((max - tmp) <= 10)
//                resultList.add(l);
//        }
//        Collections.shuffle(resultList);
        return res;
    }
    
    
        private int evaluation(Game game, int eval){
    //        System.out.println("eval : "+game.getScoreColor(color));
            if(!game.isRunningGame())
                return game.getScoreColor(game.getCurrentColor())>0?1000:-1000;
            switch(eval){
                case 0:
                    return game.getScoreColor(game.getCurrentColor());
                case 1:
                    return evalstat(game);
            }

            return 0;
        }
        
    @Override
    public Location getMove(Game game){
        switch (level) {
            case 0:
                List<Location> list =new ArrayList(game.placeable);
                Collections.shuffle(list);
                return list.get(0);
            case 1: return alphaBeta(game, 0, 2);
                
            case 2: return alphaBeta(game, 0, 4);
                
            case 3: return alphaBeta(game, 0, 6);
                
            case 4: return alphaBeta(game, 1, 2);
                
            case 5:return statIA(game);
            
                
            default:
                throw new AssertionError();
        }
    }
}
