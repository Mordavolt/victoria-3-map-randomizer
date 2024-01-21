package lv.kitn.mapadjacency;

import static com.google.common.collect.ImmutableSetMultimap.toImmutableSetMultimap;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;

@Service
public class MapAdjacencyExtractor {

  public ImmutableSetMultimap<String, String> findAdjacencyMatrix(String filePath) {
    try {
      File file = new File(filePath);
      BufferedImage image = ImageIO.read(file);
      return findAdjacencyMatrix(image);
    } catch (Exception e) {
      throw new RuntimeException("Could not calculate the adjacency matrix for " + filePath, e);
    }
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

    // Convert the colors to hex only after lv.kitn.building the matrix
    return adjacencyMatrixBuilder.build().entries().stream()
        .collect(toImmutableSetMultimap(a -> toHex(a.getKey()), a -> toHex(a.getValue())));
  }

  static String toHex(int pixel) {
    return String.format("x%06X", (pixel & 0xFFFFFF));
  }
}
