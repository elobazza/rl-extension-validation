package world;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eloisa Bazzanella
 */
public class AgentStateModel implements FullStateModel {

//    protected double [][] transitionProbs;
    
    public AgentStateModel() {
//        this.transitionProbs = new double[4][4];
//        for(int i = 0; i < 4; i++){
//            for(int j = 0; j < 4; j++){
//                //FORMULA
//                double p = 0.25;
//                transitionProbs[i][j] = p;
//            }
//        }
    }
    
    @Override
    public List<StateTransitionProb> stateTransitions(State s, Action a) {
//        AgentState gs = (AgentState) s;
//
//        int curX = gs.x;
//        int curY = gs.y;
//        
//        int adir = actionDir(a);
//        
        List<StateTransitionProb> tps = new ArrayList<StateTransitionProb>();
//        StateTransitionProb noChange = null;
//        
//        for(int i = 0; i < 4; i++){
//
//            int [] newPos = this.moveResult(curX, curY, i);
//            if(newPos[0] != curX || newPos[1] != curY){
//                //new possible outcome
//                AgentState ns = gs.copy();
//                ns.x = newPos[0];
//                ns.y = newPos[1];
//
//                tps.add(new StateTransitionProb(ns, this.transitionProbs[adir][i]));
//            }
//            else{
//                if(noChange != null){
//                    noChange.p += this.transitionProbs[adir][i];
//                }
//                else{
//                    noChange = new StateTransitionProb(s.copy(), this.transitionProbs[adir][i]);
//                    tps.add(noChange);
//                }
//            }
//        }
//
        return tps;
    }

    protected int actionDir(Action a){
        int adir = -1;
        
        if(a.actionName().equals(QLearningAlgorithm.ACTION_NORTH)){
            adir = 0;
        }
        else if(a.actionName().equals(QLearningAlgorithm.ACTION_SOUTH)){
            adir = 1;
        }
        else if(a.actionName().equals(QLearningAlgorithm.ACTION_EAST)){
            adir = 2;
        }
        else if(a.actionName().equals(QLearningAlgorithm.ACTION_WEST)){
            adir = 3;
        }
        
        return adir;
    }
    
    @Override
    public State sample(State s, Action a) {
        s = s.copy();
        AgentState state = (AgentState)s;
        int curX = state.x;
        int curY = state.y;

        int dir = actionDir(a);

//        double r = Math.random();
//        double sumProb = 0;
//        int dir = 0;
//        for(int i = 0; i < 4; i++){
//            sumProb += this.transitionProbs[adir][i];
//            if(r < sumProb){
//                    dir = i;
//                    break; 
//            }
//        }

        int [] newPos = this.moveResult(curX, curY, dir);

        state.x = newPos[0];
        state.y = newPos[1];

        return state;
    }
    
    protected int [] moveResult(int curX, int curY, int direction){
        // 0: north; 1: south; 2:east; 3: west
        int xdelta = 0;
        int ydelta = 0;
        
        switch (direction) {
            case 0:
                xdelta = -1;
                break;
            case 1:
                xdelta = 1;
                break;
            case 2:
                ydelta = 1;
                break;
            default:
                ydelta = -1;
                break;
        }

        int nx = curX + xdelta;
        int ny = curY + ydelta;

        int height = QLearningAlgorithm.map.length;
        int width  = QLearningAlgorithm.map[0].length;

        if(nx < 0 || nx >= height || ny < 0 || ny >= width){ 
            nx = curX;
            ny = curY;
        }

        return new int[]{nx, ny};
    }
    
}
