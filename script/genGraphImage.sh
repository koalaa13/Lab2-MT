#/bin/bash

java -cp ~/IntelijIdeaProjects/Lab2-MT/out/production/Lab2-MT/ GenGraph $1
dot -Tpng -o graph.png graph.txt