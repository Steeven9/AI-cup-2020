import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * AI Cup 2020 @ USI, Lugano
 * The main wrapper to compute a TSP problem. The algorithm used is best Nearest-Neighbour.
 * @author Stefano Taillefert
 */
public class Main {
    // Usage: java Main filename [index]
    // - filename: filename of the problem to test
    // - index (optional): index (starting from 1) of the node to start at, or empty to test all
    public static void main(String[] args) {
        int index = -1;
        String file = "";

        if (args.length == 1) {
            // File only
            if (Files.isRegularFile(Paths.get(args[0]))) {
                file = args[0];
            } else {
                System.err.println("Error: Invalid filename");
                System.exit(-1);
            }
        } else if (args.length == 2) {
            // Node specified, override index
            if (Files.isRegularFile(Paths.get(args[0]))) {
                file = args[0];
            } else {
                System.err.println("Error: Invalid filename");
                System.exit(-1);
            }

            try {
                index = Integer.parseInt(args[1]);
                if (index == 0 || index < -1) {
                    System.err.println("Error: Invalid index (they start from 1)");
                    System.exit(-1);
                }
                if (index != -1) {
                    --index;
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Error: Invalid index (they start from 1)");
                System.exit(-1);
            }
        } else {
            exit();
        }

        List<String> lines = new ArrayList<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ioe) {
            System.err.println("Error reading file " + file);
            System.exit(-1);
        }

        ProblemInstance p = new ProblemInstance(lines);
        long startTime, endTime;

        if (index == -1) {
            //Test all nodes in sequence
            List<Integer> tempSolution = p.solution;
            double tempSolutionCost = p.currentCost;

            startTime = System.nanoTime();
            for (int i = 0; i < p.numPoints; ++i) {
                p.solve(i);

                if (p.currentCost < tempSolutionCost) {
                    tempSolution = p.solution;
                    tempSolutionCost = p.currentCost;
                }
            }
            endTime = System.nanoTime();

            p.solution = tempSolution;
            p.currentCost = tempSolutionCost;
        } else {
            startTime = System.nanoTime();
            p.solve(index);
            endTime = System.nanoTime();
        }

        // If time taken is more than 3 minutes, report error
        if ((endTime - startTime) / 1000 >= 180000000) {
            System.err.println("Error: timeout (more than 3 minutes)");
        }

        p.solution.forEach(e -> System.out.print((e + 1) + " "));     //cities indices start from 1
        System.out.print("\nSolution for " + p.name + ": " + p.currentCost);
        System.out.println(" (best known is " + p.bestKnownCost + ")");
        System.out.println("Start node: " + (p.solution.get(0) + 1));
        System.out.println("Time taken (ms): " + (endTime - startTime) / 1000000);
        System.out.println("Nodes visited: " + (p.solution.size() - 1) + "/" + p.numPoints);
    }

    private static void exit() {
        System.err.println("Usage: java Main filename [index | -1]");
        System.exit(-1);
    }
}
