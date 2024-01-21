package mapadjacency;

import static com.google.common.collect.ImmutableSetMultimap.toImmutableSetMultimap;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;

class MapAdjacencyExtractor {
  public static void main(String[] args) throws Exception {
    File file =
        new File(
            "C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 3\\game\\map_data\\provinces.png");
    BufferedImage image = ImageIO.read(file);
    var adjacencyMatrix = findAdjacencyMatrix(image);
  }

  static ImmutableSetMultimap<String, String> findAdjacencyMatrix(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    var adjacencyMatrixBuilder = ImmutableSetMultimap.<Integer, Integer>builder();
    Set<Integer> checkedPairs = Sets.newHashSet();

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int currentColor = image.getRGB(x, y);
        int[] dx = {1, 0};
        int[] dy = {0, 1};

        for (int i = 0; i < dx.length; i++) {
          int newX = x + dx[i];
          int newY = y + dy[i];

          if (newX < width && newY < height) {
            int adjacentColor = image.getRGB(newX, newY);
            if (currentColor != adjacentColor) {
              int minColor = Math.min(currentColor, adjacentColor);
              int maxColor = Math.max(currentColor, adjacentColor);
              int colorPair = minColor * 31 + maxColor;

              // Avoid adding the same color pair
              if (!checkedPairs.contains(colorPair)) {
                adjacencyMatrixBuilder.put(currentColor, adjacentColor);
                adjacencyMatrixBuilder.put(adjacentColor, currentColor);
                checkedPairs.add(colorPair);
              }
            }
          }
        }
      }
    }

    // Convert the colors to hex only after building the matrix
    return adjacencyMatrixBuilder.build().entries().stream()
        .collect(toImmutableSetMultimap(a -> toHex(a.getKey()), a -> toHex(a.getValue())));
  }

  static String toHex(int pixel) {
    return String.format("x%06X", (pixel & 0xFFFFFF));
  }

  static class Graph {
    private final boolean[][] adjacencyMatrix;
    private final boolean[] visited;
    private final int numberOfNodes;

    public Graph(boolean[][] adjacencyMatrix) {
      this.adjacencyMatrix = adjacencyMatrix;
      this.numberOfNodes = adjacencyMatrix.length;
      this.visited = new boolean[numberOfNodes];
    }

    public List<Integer> findContiguousNodes(int startNode, int n) {
      List<Integer> path = new ArrayList<>();
      dfs(startNode, n, path);
      return path;
    }

    private boolean dfs(int currentNode, int n, List<Integer> path) {
      path.add(currentNode);
      visited[currentNode] = true;

      if (path.size() == n) {
        return true; // Found n contiguous nodes
      }

      for (int nextNode = 0; nextNode < numberOfNodes; nextNode++) {
        if (adjacencyMatrix[currentNode][nextNode] && !visited[nextNode]) {
          if (dfs(nextNode, n, path)) {
            return true;
          }
        }
      }

      // Backtrack
      path.remove(path.size() - 1);
      visited[currentNode] = false;
      return false;
    }
  }
}
