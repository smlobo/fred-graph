# fred-graph
Java query FRED data, parse with Jackson, draw a graph with JFreeChart.

# Build
`mvn clean package`

# Run
`java -jar target/fred-graph-*-jar-with-dependencies.jar <seriesId1> [[:] <seriesId2> ...]`

# Examples
- `java -jar target/fred-graph-*-jar-with-dependencies.jar POP`
- `java -jar target/fred-graph-*-jar-with-dependencies.jar GNPCA POP`
- `java -jar target/fred-graph-*-jar-with-dependencies.jar NYLF CALF TXLF`
- `java -jar target/fred-graph-*-jar-with-dependencies.jar NYNAN CANAN TXNAN`
- `java -jar target/fred-graph-*-jar-with-dependencies.jar NYUR NJUR NCUR CAUR TXUR`

# Examples with multiple Y-axis
- Total employees (TX & CA) vs Unemployment rate (TX & CA): `java -jar target/fred-graph-1.0-SNAPSHOT-jar-with-dependencies.jar TXNA CANA : TXUR CAUR`
- Fed funds rate vs Unemployment rate vs GDP vs Change in CPI: `java -jar target/fred-graph-1.0-SNAPSHOT-jar-with-dependencies.jar FEDFUNDS : UNRATE : GDP : BPCCRO1Q156NBEA`

# References
- [FRED API](https://fred.stlouisfed.org/docs/api/fred/)
- [JFreeChart](http://www.jfree.org/jfreechart/)
- [Jackson](https://github.com/FasterXML/jackson)

# Update 2023/05/01
- Changed dependency to JFreeChart 1.5.4 which is built locally
```
% git clone https://github.com/jfree/jfreechart.git
% cd jfreechart/
% git checkout --track origin/v1.5.x
% mvn clean install
```