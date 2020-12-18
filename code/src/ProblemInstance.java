import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a TSP problem. Stores various data read from a .tsp file.
 * @author Stefano Taillefert
 */
public class ProblemInstance {
    public List<String> lines;
    public String name;
    public int bestKnownSolution;
    public int bestComputedSolution = -1;
    public int currentSolution = 0;
    public List<Point> points = new ArrayList<>();
    public int numPoints;
    public double[][] distMatrix;

    public ProblemInstance(List<String> data) {
        lines = data;
        name = lines.get(0).split(":")[1].strip();
        bestKnownSolution = Integer.parseInt(lines.get(5).split(":")[1].strip());

        // Add points with their coordinates
        for (int i = 7; i < lines.size() - 1; ++i) {
            String[] line = lines.get(i).split(" ");
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

    public List<Integer> solve(int startIndex) {
        List<Integer> result = new ArrayList<>();
        List<Integer> visited = new ArrayList<>();
        int currentNode = startIndex;

        result.add(startIndex);
        visited.add(startIndex);

        // First node is already in, so start one ahead
        for (int i = 1; i < numPoints; ++i) {
            // Get list of distances from here
            double[] dist = distMatrix[currentNode];

            // Find closest city
            int index = 0;
            double d = Double.POSITIVE_INFINITY;
            for (int k = 0; k < dist.length; ++k) {
                if (dist[k] < d && dist[k] != 0 && !visited.contains(k)) {
                    index = k;
                    d = dist[k];
                }
            }

            result.add(index);
            visited.add(index);
            currentNode = index;
            currentSolution += d;
        }

        // Save only best solution found
        if (bestComputedSolution == -1 || currentSolution < bestComputedSolution) {
            bestComputedSolution = currentSolution;
        }

        return result;
    }
}
