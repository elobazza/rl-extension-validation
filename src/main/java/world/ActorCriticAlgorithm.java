package world;

import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.actorcritic.ActorCritic;
import burlap.behavior.singleagent.learning.actorcritic.actor.BoltzmannActor;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.Domain;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.statehashing.HashableState;
import burlap.statehashing.simple.SimpleHashableStateFactory;

/**
 * @author Eloísa Bazzanella
 */
public class ActorCriticAlgorithm implements DomainGenerator {

    public static final String VAR_X = "x";
    public static final String VAR_Y = "y";

    public static final String ACTION_NORTH = "north";
    public static final String ACTION_SOUTH = "south";
    public static final String ACTION_EAST  = "east";
    public static final String ACTION_WEST  = "west";

    protected int goalx = 2;
    protected int goaly = 5;
    
    public static int [][] map = new int[][]{
                    {0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 1, 0},
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
        ActorCriticAlgorithm gen = new ActorCriticAlgorithm();
        SADomain domain = (SADomain) gen.generateDomain();
        State initialState = new AgentState(2, 0);
        SimulatedEnvironment env = new SimulatedEnvironment(domain, initialState);
    
        BoltzmannActor actor = new BoltzmannActor(domain, new SimpleHashableStateFactory(), 0.5);
        CriticImplementation critic = new CriticImplementation(1, new SimpleHashableStateFactory(), 0.5, 0, 0.3);
        ActorCritic agent = new ActorCritic(actor, critic);
        
        int episodes = 0;
        while(episodes <= 100){
            Episode e = agent.runLearningEpisode(env, -1);
            System.out.println("Episodio " + episodes + ": " + e.actionString());
            env.resetEnvironment();
            episodes++;
        }
        
        for(HashableState v : critic.vIndex.keySet()) {
            System.out.println(v.s().toString() + "|" + critic.getV(v).v);
        }
        
    }
}