# deploiement_global.gp
R = 10   # rayon de couverture

$CIBLES << EOD
50 50
41 50
90 90
86 89
10 10
15 95
EOD

# ---------- Solutions (un bloc par solution) ----------
$S1 << EOD
88.06423305074125 87.57171002553432
86.93384485388543 87.09346953245766
46.66853486806709 52.46467585714932
46.52526867734838 52.49528994340853
46.735085803112256 51.9081294967511
46.93505099994763 51.487232402056485
88.35695337967132 87.96165834362925
46.078581832220586 52.3431401829941
EOD

$S2 << EOD
87.22446492739381 88.17568192528154
87.23103041871059 88.17704755130819
87.22326637111954 88.16521832295444
87.22429065877009 88.17365862066762
EOD

$S3 << EOD
48.20127470441115 51.03214921940523
48.04066215283058 50.83046928322353
47.98493693661973 50.584098907825314
48.03482094892979 50.85984068571765
85.5088880969052 86.93783308663144
48.242970830648716 50.577432391297215
84.08230425200128 87.69527656965728
47.95162961360912 50.75376358353531
48.03101892058604 50.829823152982726
48.02426233184976 51.100526794903935
EOD

# Noms des blocs à tracer (ajouter ici une nouvelle solution : "S1 S2 S3 S4")
liste = "S1 S2 S3"

# Palette (rouge et bleu évités : réservés aux cibles et capteurs), réutilisée en boucle si besoin
couleurs = "#2ca02c #ff7f0e #9467bd #17becf #8c564b #e377c2 #bcbd22 #006d5b #7f7f7f #e6ab02"
nc = words(couleurs)

# ---------- Réglages communs ----------
set terminal pngcairo size 900,900 enhanced font "Arial,12"
set xlabel "x"
set ylabel "y"
set xrange [-5:105]
set yrange [-5:105]
set size ratio -1
set grid
set key outside right top
set style fill empty          # cercles : contour seulement
set style circle radius R

# ---------- Un PNG par solution ----------
do for [k=1:words(liste)] {
    nom  = word(liste, k)
    bloc = "$" . nom

    eval sprintf("stats %s nooutput", bloc)
    n = STATS_records

    set output sprintf("deploiement_%s.png", nom)
    set title sprintf("Solution %d (%d capteurs)", k, n)

    eval sprintf("plot for [i=1:%d] %s every ::i-1::i-1 using 1:2:(R) \
        title sprintf('Couverture capteur %%d', i) \
        with circles lc rgb word(couleurs, (i-1)%%nc+1) lw 2, \
        %s using 1:2 title 'Capteurs' with points pt 7 ps 1.5 lc rgb 'blue', \
        $CIBLES using 1:2 title 'Cibles' with points pt 7 ps 1.5 lc rgb 'red'", n, bloc, bloc)
}

set output