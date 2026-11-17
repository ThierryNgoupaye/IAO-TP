package TPs.TP2.algporithm;


import TPs.TP2.problems.SensorDeploymentProblem;
import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.SelectionFactory;
import jmetal.util.Configuration;
import jmetal.util.JMException;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class SensorDeploymentNSGAIIMain {

    public static Logger logger_ ;           // Logger object
    public static FileHandler fileHandler_ ; // FileHandler object


    public static void main(String[] args) throws IOException, JMException, ClassNotFoundException {

        Problem problem   ; // The problem to solve
        Algorithm algorithm ; // The algorithm to use
        Operator crossover ; // Crossover operator
        Operator  mutation  ; // Mutation operator
        Operator  selection ; // Selection operator
        double[] targetsPositions = new double[]{50, 50, 41, 50, 90, 90, 86, 89, 10, 10, 15, 95};
        double corverRadius =10;
        double maximalLevelRedondance = 2.0;

        HashMap<String, Double> parameters ; // Operator parameters

        // Logger object and file to store log messages
        logger_      = Configuration.logger_ ;
        fileHandler_ = new FileHandler("NSGAII_main.log");
        logger_.addHandler(fileHandler_) ;

        // Création du problème et instanciation de l'algorithme NSGAII (pour la résolution des problèmes multi-objectifs)
        int numberOfSensors = 10;
        problem = new SensorDeploymentProblem("Real", numberOfSensors * 2 , targetsPositions, corverRadius, maximalLevelRedondance);
        algorithm = new NSGAII(problem);


        // Définition des paramètres (taille de la population et nombre d'évaluations maximum.)
        algorithm.setInputParameter("populationSize",100);
        algorithm.setInputParameter("maxEvaluations",25000);


        // Définition des opérateurs de croisement, mutation et sélection.
        parameters = new HashMap<>() ;
        parameters.put("probability", 0.9) ;
        parameters.put("distributionIndex", 20.0) ;
        crossover = CrossoverFactory.getCrossoverOperator("SBXCrossover", parameters);

        parameters = new HashMap<>() ;
        parameters.put("probability", 1.0/problem.getNumberOfVariables()) ;
        parameters.put("distributionIndex", 20.0) ;
        mutation = MutationFactory.getMutationOperator("PolynomialMutation", parameters);

        // Selection Operator
        selection = SelectionFactory.getSelectionOperator("BinaryTournament2", null) ;

        // Add the operators to the algorithm
        algorithm.addOperator("crossover",crossover);
        algorithm.addOperator("mutation",mutation);
        algorithm.addOperator("selection",selection);


        // Execution de l'algorithme pour la résolution du problème
        long initTime = System.currentTimeMillis();
        SolutionSet population = algorithm.execute();
        long estimatedTime = System.currentTimeMillis() - initTime;

        // Result messages
        logger_.info("Total execution time: "+estimatedTime + " ms");
        logger_.info("Variables values have been written to file VAR");
        population.printVariablesToFile("VAR_" + numberOfSensors);
        logger_.info("Objectives values have been written to file FUN");
        population.printObjectivesToFile("FUN_" + numberOfSensors);

        int evaluations = (Integer) algorithm.getOutputParameter("evaluations");
        logger_.info("Speed      : " + evaluations + " evaluations") ;

    }
}
