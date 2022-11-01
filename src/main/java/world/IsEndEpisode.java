package world;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.state.State;

/**
 * @author Eloisa Bazzanella
 */
public class IsEndEpisode implements TerminalFunction {

    int goalX;
    int goalY;
    
    public IsEndEpisode(int goalX, int goalY){
        this.goalX = goalX;
        this.goalY = goalY;
    }
    
    @Override
    public boolean isTerminal(State s) {
        int ax, ay;
        
        if(QLearningAlgorithm.isQLearning) {
            ax = (Integer)s.get(QLearningAlgorithm.VAR_X);
            ay = (Integer)s.get(QLearningAlgorithm.VAR_Y);

            if(ax == this.goalX && ay == this.goalY){
                System.out.println(ax + "/" + ay + "= GOAL");
                return true;
            } 
            
            if(QLearningAlgorithm.map[ax][ay] == 1) {
                System.out.println(ax + "/" + ay + "= CLIFF");
                return true;
            }
        } else {
            ax = (Integer)s.get(SarsaAlgorithm.VAR_X);
            ay = (Integer)s.get(SarsaAlgorithm.VAR_Y);

            if(ax == this.goalX && ay == this.goalY){
                System.out.println(ax + "/" + ay + "= GOAL");
                return true;
            } 
            
            if(SarsaAlgorithm.map[ax][ay] == 1) {
                System.out.println(ax + "/" + ay + "= CLIFF");
                return true;
            }
        }
        
        return false;
    }
}