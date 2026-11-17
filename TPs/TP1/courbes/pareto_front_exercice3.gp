set output "FrontParetoExercice3.eps";

set xlabel "f1";
set ylabel "f2";

set style data points;

set term post eps enhanced color solid "Helvetica" 18;

plot [*:*][*:*] "/home/thierry/projets/polytech-dijon/IntroIA-Jmetal/FUN" using 1:2 title "1er front de Pareto"