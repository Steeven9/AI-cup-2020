import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * AI Cup 2020 @ USI, Lugano
 * The main wrapper to compute a TSP problem.
 * @author Stefano Taillefert
 */
public class Main {

    // TODO max time: 3 min
    // TODO clean up garbage prints

    // Usage: Main filename [index | -1]
    // - filename: filename of the problem to test
    // - index (optional): index of the node to start at or -1 to test all; if empty, use a random one
    public static void main(String[] args) {
        int index = -2;
        String file = "";

        if (args.length == 1) {
            // File only -> pick random node (done later)
            if (Files.isRegularFile(Paths.get(args[0]))) {
                file = args[0];
                System.out.println("Using file " + file);
            } else {
                System.out.println("Invalid filename");
                System.exit(-1);
            }
            System.out.println("Starting from random node");
        } else if (args.length == 2) {
            // Node specified, override index
            if (Files.isRegularFile(Paths.get(args[0]))) {
                file = args[0];
                System.out.println("Using file " + file);
            } else {
                System.out.println("Invalid filename");
                System.exit(-1);
            }

            try {
                index = Integer.parseInt(args[1]);
                if (index == -1) {
                    System.out.println("Testing all nodes (might be slow!)");
                } else {
                    System.out.println("Starting from node " + index);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid index");
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

        // Initiate puroburemu!
        ProblemInstance p = new ProblemInstance(lines);
        List<String> solutionPath = new ArrayList<>();
        long startTime = 0, endTime = 0;

        if (index == -1) {
            //Test all nodes in sequence
            List<String> tempSolution;
            startTime = System.nanoTime();
            for (int i = 0; i < p.numPoints; ++i) {
                tempSolution = p.solve(i);
                if (p.currentSolution == p.bestComputedSolution) {
                    solutionPath = tempSolution;
                }
            }
            endTime = System.nanoTime();
        } else {
            if (index == -2) {
                // Pick random starting node
                index = ThreadLocalRandom.current().nextInt(0, p.numPoints);
                System.out.println("Node chosen: " + index);
            }
            startTime = System.nanoTime();
            solutionPath = p.solve(index);
            endTime = System.nanoTime();
        }

        solutionPath.forEach(e -> System.out.print(e + " "));
        System.out.print("\nSolution for " + p.name + ": " + p.bestComputedSolution);
        System.out.println(" (best known is " + p.bestKnownSolution + ")");
        System.out.println("Time taken (ms): " + (endTime - startTime) / 1000);
    }

    private static void exit() {
        System.err.println("Usage: Main filename [index | -1]");
        System.exit(-1);
    }
}
