package world;

import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.Domain;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.statehashing.simple.SimpleHashableStateFactory;

/**
 * @author Eloisa Bazzanella
 */
public class QLearningAlgorithm implements DomainGenerator {

    public static final String VAR_X = "x";
    public static final String VAR_Y = "y";

    public static final String ACTION_NORTH = "north";
    public static final String ACTION_SOUTH = "south";
    public static final String ACTION_EAST  = "east";
    public static final String ACTION_WEST  = "west";

    protected int goalx = 2;
    protected int goaly = 4;
    
    public static int [][] map = new int[][]{
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 0},
    };
    
    SimulatedEnvironment env;
    
     
    @Override
    public Domain generateDomain() {
        
        SADomain domain = new SADomain();
        
        domain.addActionTypes(
                        new UniversalActionType(ACTION_NORTH),
                        new UniversalActionType(ACTION_SOUTH),
                        new UniversalActionType(ACTION_EAST),
                        new UniversalActionType(ACTION_WEST));

        AgentStateModel  stateModel   = new AgentStateModel();
        RewardFunction   reward       = new Reward();
        TerminalFunction isEndEpisode = new IsEndEpisode(this.goalx, this.goaly);

        domain.setModel(new FactoredModel(stateModel, reward, isEndEpisode));

        return domain;
    }
    
    public static void main(String [] args){
        QLearningAlgorithm gen = new QLearningAlgorithm();
        SADomain domain = (SADomain) gen.generateDomain();

        State initialState = new AgentState(2, 0);
        SimulatedEnvironment env = new SimulatedEnvironment(domain, initialState);
    
        QLearning agent = new QLearning(domain, 0.3, new SimpleHashableStateFactory(), 0, 0.1);
        
        EpsilonGreedy epsilon = new EpsilonGreedy(agent, 0.7);
        agent.setLearningPolicy(epsilon);
        
        int episodes = 0;
        while(episodes <= 500){
            Episode e = agent.runLearningEpisode(env, -1);
            System.out.println("Episodio " + episodes + ": " + e.actionString());
            epsilon.setEpsilon(epsilon.getEpsilon() * 0.995);
            env.resetEnvironment();
            episodes++;
        }
        
        agent.writeQTable("qtable.txt");
    }
}