# Compile

`javac src/*.java -d .`


# Run

`java Main filename [index]`

where `filename` is the path of the problem you want to run and `index` is an optional index of the node
for the nearest-neighbour algorithm to start from. If `index` is empty, all the nodes will be checked and the best one will be kept (might be slow!).

## Examples

`java Main ./problems/ch130.tsp`

`java Main ./problems/ch130.tsp 1`
