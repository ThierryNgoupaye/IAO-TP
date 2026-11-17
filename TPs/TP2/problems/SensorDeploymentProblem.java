package TPs.TP2.problems;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;
import java.util.Arrays;


public class SensorDeploymentProblem extends Problem {

    private final double[] targetsPositions;
    private final double coverRadius;
    private final double maximalLevelRedondance;

    public SensorDeploymentProblem(String variableType, int numberOfVariables, double[] targetsPositions, double coverRadius, double maximalLevelRedondance) {
        this.targetsPositions = targetsPositions;
        this.coverRadius = coverRadius;
        this.maximalLevelRedondance = maximalLevelRedondance;


        if (numberOfVariables % 2 != 0){
            System.out.println("Le nombre de variables de décision pour ce problème doit être pair !");
            System.exit(-1);
        }
        this.setNumberOfVariables(numberOfVariables);
        this.lowerLimit_ = new double[numberOfVariables];
        this.upperLimit_ = new double[numberOfVariables];
        Arrays.fill(upperLimit_, 100.0);
        Arrays.fill(lowerLimit_, 0.0);


        if(variableType.compareTo("Real") == 0){
            this.solutionType_ = new RealSolutionType(this);
        }
        else {
            System.out.println("Le type de variable des variables de décision doit être Real et non " + variableType) ;
            System.exit(-1) ;
        }
        this.problemName_ = "Déploiement multi-objectifs d'un réseau de capteurs.";
        this.numberOfObjectives_ = 2;
    }

    @Override
    public void evaluate(Solution solution) throws JMException {
        Variable[] sensorPositions = solution.getDecisionVariables();

        // Calcul de f1
        double f1  = 0.0;
        for (int j=0; j<this.targetsPositions.length; j+=2) {
            double Q = 0;
            for (int i=0; i < sensorPositions.length; i+=2){
                double distanceBetweenTargetSensor = this.euclidienDistance(sensorPositions[i].getValue(), this.targetsPositions[j], sensorPositions[i+1].getValue(), this.targetsPositions[j+1]);
                if (distanceBetweenTargetSensor <= this.coverRadius){
                    Q += 1 - distanceBetweenTargetSensor/this.coverRadius;
                    if (Q >= this.maximalLevelRedondance) {
                        Q = this.maximalLevelRedondance;
                        break;
                    }
                }
            }
            f1 += Q;
        }

        // Calcul de f2
        int numberOfSensors = sensorPositions.length/2;
        double distance = 0;
        for (int i = 0; i <= sensorPositions.length-2; i+=2){
            double distance_ = 0;
            for (int j = i+2; j<sensorPositions.length; j+=2){
                distance_+= this.euclidienDistance(sensorPositions[i].getValue(), sensorPositions[j].getValue(), sensorPositions[i+1].getValue(), sensorPositions[j+1].getValue());
            }
            distance+= distance_;
        }
        double f2 =  (2.0 / (numberOfSensors*(numberOfSensors - 1) )) * distance;

        solution.setObjective(0, -f1);
        solution.setObjective(1, f2);
    }


    private double euclidienDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
