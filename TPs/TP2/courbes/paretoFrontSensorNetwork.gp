set output "paretoFrontSensorNetwork_10.eps";

set xlabel "-f1";
set ylabel "f2";

set style data points;

set term post eps enhanced color solid "Helvetica" 18;

plot [*:*][*:*] "/home/thierry/projets/polytech-dijon/IntroIA-Jmetal/FUN_10" using 1:2 title "1er front de Pareto pour n=10"