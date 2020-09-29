# fred-graph
Java query FRED data, parse with Jackson, draw a graph with JFreeChart.

# Build
`mvn clean package`

# Run
`java -jar target/fred-graph-*-jar-with-dependencies.jar <seriesId1> [<seriesId2> ...]`

# Examples
- `java -jar target/fred-graph-*-jar-with-dependencies.jar POP`
- `java -jar target/fred-graph-*-jar-with-dependencies.jar GNPCA POP`
- `java -jar target/fred-graph-*-jar-with-dependencies.jar NYLF CALF TXLF`
- `java -jar target/fred-graph-*-jar-with-dependencies.jar NYNAN CANAN TXNAN`
- `java -jar target/fred-graph-*-jar-with-dependencies.jar NYUR NJUR NCUR CAUR TXUR`

# References
- [FRED API](https://fred.stlouisfed.org/docs/api/fred/)
- [JFreeChart](http://www.jfree.org/jfreechart/)
- [Jackson](https://github.com/FasterXML/jackson)
