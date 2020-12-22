import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a TSP problem. Stores various data read from a .tsp file.
 * @author Stefano Taillefert
 */
public class ProblemInstance {
    public List<String> lines;
    public String name;
    public double bestKnownCost;
    public double currentCost = Integer.MAX_VALUE;
    public List<Point> points = new ArrayList<>();
    public int numPoints;
    public double[][] distMatrix;
    public List<Integer> solution = new ArrayList<>();

    public ProblemInstance(List<String> data) {
        lines = data;
        name = lines.get(0).split(":")[1].strip();
        bestKnownCost = Integer.parseInt(lines.get(5).split(":")[1].strip());

        // Add points with their coordinates
        for (int i = 7; i < lines.size() - 1; ++i) {
            String[] line = lines.get(i).strip().split(" ");
            points.add(new Point(Integer.parseInt(line[0]), Double.parseDouble(line[1]), Double.parseDouble(line[2])));
        }

        numPoints = points.size();
        distMatrix = new double[numPoints][numPoints];

        // Store the distance for every point
        for (int i = 0; i < numPoints; ++i) {
            for (int j = 0; j < numPoints; ++j) {
                distMatrix[i][j] = Point.getDistance(points.get(i), points.get(j));
            }
        }
    }

    public void solve(int startIndex) {
        if (startIndex > numPoints) {
            System.err.println("Error: Invalid index (they start from 1)");
            System.exit(-1);
        }

        solution = new ArrayList<>();
        currentCost = 0;
        int currentNode = startIndex;

        solution.add(currentNode);

        // First node is already in, so start one ahead
        for (int i = 1; i < numPoints; ++i) {
            // Get list of distances from here
            double[] dist = distMatrix[currentNode];

            // Find closest city
            int index = 0;
            double d = Double.POSITIVE_INFINITY;

            for (int k = 0; k < dist.length; ++k) {
                if (dist[k] < d && dist[k] != 0 && !solution.contains(k)) {
                    index = k;
                    d = dist[k];
                }
            }

            solution.add(index);
            currentNode = index;
            currentCost += d;
        }

        solution.add(startIndex);
        currentCost += distMatrix[startIndex][currentNode];
    }

    public void optimize() {
        // TODO
    }
}
