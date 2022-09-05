package world;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;

/**
 * @author Eloisa Bazzanella
 */
public class Reward implements RewardFunction {

    public double reward(State s, Action a, State sprime) {

        int ax = (Integer)sprime.get(QLearningAlgorithm.VAR_X);
        int ay = (Integer)sprime.get(QLearningAlgorithm.VAR_Y);

        if(QLearningAlgorithm.map[ax][ay] == 1) {
            return -100;
        }
        
        return -1;
    }
    
}
