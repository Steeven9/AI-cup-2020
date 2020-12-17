# Compile

`javac src/*.java -d .`


# Run

`java Main filename [index | -1]`

where `filename` is the path of the problem you want to run and `index` is an optional index of the node
for the nearest-neighbour algorithm to start from. If `index` is -1, all the nodes will be checked and the best one will be kept (might be slow!).

## Examples

`java Main ./problems/ch130.tsp`

`java Main ./problems/ch130.tsp 1`

`java Main ./problems/ch130.tsp -1`
