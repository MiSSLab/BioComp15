# BioComp15
### 15. Filogeneza dystansowa.
Dla danej macierzy odległości między liśćmi utworzyć ważone drzewo ukorzenione metodą UPGMA oraz nieukorzenione metodą NJ. Wylistowanie obu drzew oraz możliwość automatycznej weryfikacji zgodności ich topologii (tj. z pominięciem długości krawędzi). Drzewa filogenetyczne, Konstruowanie drzew filogenetycznych – metody odległościowe,

### Run
```
mvn package
java -jar -Dfilename="resources/data1.matrix" target/distance-phylogenetics-jar-with-dependencies.jar
```

### TODO
* Dokumentacja (plik PDF) powinna zawierać krótkie informacje na temat:
    - podstawowej obsługi przygotowanej aplikacji    
    - danych wejściowych i wyjściowych (format i inne wymagania)
    - zastosowanych algorytmów (opis algorytmu) oraz technologii (biblioteki) 
* refaktoring NJ
* ! porównywanie topologii
    - wymyślić... 
* rysowanie drzewa nieukorzenionego uzyskanego po NJ
