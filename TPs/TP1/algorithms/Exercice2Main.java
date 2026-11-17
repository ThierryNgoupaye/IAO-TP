package TPs.TP1.algorithms;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.metaheuristics.singleObjective.geneticAlgorithm.gGA;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.SelectionFactory;
import TPs.TP1.problems.Exercice2;
import jmetal.util.JMException;

import java.util.HashMap;

public class Exercice2Main {

    public static void main(String[] args) throws JMException, ClassNotFoundException {
        Problem problem   ;         // The problem to solve
        Algorithm algorithm ;         // The algorithm to use
        Operator crossover ;         // Crossover operator
        Operator  mutation  ;         // Mutation operator
        Operator  selection ;         // Selection operator

        HashMap<String, Double> parameters ;

        // Instantiation du problème.
        problem = new Exercice2("Real", 30);

        // Instanciation de l'algorithmique (traitant des problèmes mono-objectifs)
        algorithm = new gGA(problem);
        algorithm.setInputParameter("populationSize",100);
        algorithm.setInputParameter("maxEvaluations", 25000);


        // Opérateur de mutation et de croisement.
        parameters = new HashMap<>() ;
        parameters.put("probability", 0.9) ;
        parameters.put("distributionIndex", 20.0) ;
        crossover = CrossoverFactory.getCrossoverOperator("SBXCrossover", parameters);

        parameters = new HashMap<>() ;
        parameters.put("probability", 1.0/problem.getNumberOfVariables()) ;
        parameters.put("distributionIndex", 20.0) ;
        mutation = MutationFactory.getMutationOperator("PolynomialMutation", parameters);

        // Opérateur de selection
        selection = SelectionFactory.getSelectionOperator("BinaryTournament", null) ;


        /* Enregistrement des différents opérateurs dans l'algorithme */
        algorithm.addOperator("crossover",crossover);
        algorithm.addOperator("mutation",mutation);
        algorithm.addOperator("selection",selection);


        long initTime = System.currentTimeMillis();
        SolutionSet population = algorithm.execute();
        long estimatedTime = System.currentTimeMillis() - initTime;
        System.out.println("Total execution time: " + estimatedTime);
        /* Log messages */
        System.out.println("Objectives values have been written to file FUN");
        population.printObjectivesToFile("FUN");
        System.out.println("Variables values have been written to file VAR");
        population.printVariablesToFile("VAR");
    }
}
