package world;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Eloisa Bazzanella
 */
public class AgentState implements MutableState {

    public int x;
    public int y;

    private final static List<Object> keys = Arrays.<Object>asList(
            QLearningAlgorithm.VAR_X, QLearningAlgorithm.VAR_Y);
    
    private final static List<Object> keysSarsa = Arrays.<Object>asList(
            SarsaAlgorithm.VAR_X, SarsaAlgorithm.VAR_Y);

    public AgentState() {
    }

    public AgentState(int x, int y) {
            this.x = x;
            this.y = y;
    }
    
    @Override
    public MutableState set(Object variableKey, Object value) {
        if(QLearningAlgorithm.isQLearning) {
            if(variableKey.equals(QLearningAlgorithm.VAR_X)){
                this.x = StateUtilities.stringOrNumber(value).intValue();
            }
            else if(variableKey.equals(QLearningAlgorithm.VAR_Y)){
                this.y = StateUtilities.stringOrNumber(value).intValue();
            }
            else{
                throw new UnknownKeyException(variableKey);
            }
        } else {
            if(variableKey.equals(SarsaAlgorithm.VAR_X)){
                this.x = StateUtilities.stringOrNumber(value).intValue();
            }
            else if(variableKey.equals(SarsaAlgorithm.VAR_Y)){
                this.y = StateUtilities.stringOrNumber(value).intValue();
            }
            else{
                throw new UnknownKeyException(variableKey);
            }
            
        }
        return this;
    }

    @Override
    public List<Object> variableKeys() {
       return keys;
    }

    @Override
    public Object get(Object variableKey) {
        if(QLearningAlgorithm.isQLearning) {
            if(variableKey.equals(QLearningAlgorithm.VAR_X)){
                return x;
            }
            else if(variableKey.equals(QLearningAlgorithm.VAR_Y)){
                return y;
            }
        } else {
            if(variableKey.equals(SarsaAlgorithm.VAR_X)){
                return x;
            }
            else if(variableKey.equals(SarsaAlgorithm.VAR_Y)){
                return y;
            }    
        }
        throw new UnknownKeyException(variableKey);
    }

    public AgentState copy() {
            return new AgentState(x, y);
    }
    
    public String toString() {
            return StateUtilities.stateToString(this);
    }
    
}
