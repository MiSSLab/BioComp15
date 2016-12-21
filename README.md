# BioComp15
### 15. Filogeneza dystansowa.
Dla danej macierzy odległości między liśćmi utworzyć ważone drzewo ukorzenione metodą UPGMA oraz nieukorzenione metodą NJ. Wylistowanie obu drzew oraz możliwość automatycznej weryfikacji zgodności ich topologii (tj. z pominięciem długości krawędzi). Drzewa filogenetyczne, Konstruowanie drzew filogenetycznych – metody odległościowe,

### Run
```
mvn package
java -jar -Dfilename="resources/data1.matrix" target/distance-phylogenetics-jar-with-dependencies.jar
```

### TODO
* Refaktoring 
    - poprawienie etykietowania
    - poprawienie logowania w AdjacencyMatrix i Comparatorze
    - zrobienie logowania, żeby można było przekazywać -D
    - przetłumaczyć readme na angielski...

    