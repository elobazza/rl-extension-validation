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

    public AgentStateModel() {}
    
    @Override
    public List<StateTransitionProb> stateTransitions(State s, Action a) {
        List<StateTransitionProb> tps = new ArrayList<StateTransitionProb>();
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

        int height, width;
        if(QLearningAlgorithm.isQLearning) {
            height = QLearningAlgorithm.map.length;
            width  = QLearningAlgorithm.map[0].length;
        } else {
            height = SarsaAlgorithm.map.length;
            width  = SarsaAlgorithm.map[0].length;
            
        }

        if(nx < 0 || nx >= height || ny < 0 || ny >= width){ 
            nx = curX;
            ny = curY;
        }

        return new int[]{nx, ny};
    }
    
}
