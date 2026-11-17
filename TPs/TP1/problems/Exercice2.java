package TPs.TP1.problems;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

import java.util.Arrays;


public class Exercice2 extends Problem {

    private final double alpha;
    private final double beta;


    public Exercice2(String variableType, int numberOfVariables) {
        this.setNumberOfVariables(numberOfVariables);
        this.numberOfObjectives_ = 1;
        this.lowerLimit_ = new double[numberOfVariables];
        this.upperLimit_ = new double[numberOfVariables];
        Arrays.fill(upperLimit_, 1.0);
        Arrays.fill(lowerLimit_, 0.0);

        if(variableType.compareTo("Real") == 0){
            this.solutionType_ = new RealSolutionType(this);
        }
        else {
            System.out.println("OneMax: solution type " + variableType + " invalid") ;
            System.exit(-1) ;
        }
        this.problemName_ = "calibrage pipeline CI/CD";
        this.alpha = 0.5;
        this.beta = 0.8;


    }

    @Override
    public void evaluate(Solution solution) throws JMException {
        // On récupère les variables de décision de la solution
        Variable[] decisionVariables  = solution.getDecisionVariables();

        double sum = 0;
        for (Variable integer: decisionVariables) {
            sum += integer.getValue();
        }
        double gx = 1 + 9 * (sum) / (this.numberOfVariables_-1);

        double f1 = decisionVariables[0].getValue();
        double f2 = gx * (1-Math.sqrt(f1/gx));

        double F = this.alpha*f1 + this.beta*f2;
        solution.setObjective(0, F);


    }
}
