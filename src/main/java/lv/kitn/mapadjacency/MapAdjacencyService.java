package lv.kitn.mapadjacency;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static com.google.common.collect.ImmutableSetMultimap.toImmutableSetMultimap;
import static java.util.function.Predicate.not;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MapAdjacencyService {
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
    log.debug("Calculating adjacency matrix for {}", image);
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

    log.debug("Returning calculated matrix");
    return adjacencyMatrixBuilder.build().entries().stream()
        .collect(toImmutableSetMultimap(a -> toHex(a.getKey()), a -> toHex(a.getValue())));
  }

  static String toHex(int pixel) {
    return String.format("x%06X", (pixel & 0xFFFFFF));
  }

  public ImmutableSetMultimap<String, String> loadAdditionalAdjacencyMatrix(String filePath) {
    try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
      var map = ImmutableSetMultimap.<String, String>builder();
      new CSVReaderBuilder(reader)
          .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
          .withSkipLines(1)
          .build()
          .readAll()
          .forEach(
              row -> {
                var color1 = uppercaseTheColorHex(row[0]);
                var color2 = uppercaseTheColorHex(row[1]);
                map.put(color1, color2).put(color2, color1);
              });
      return map.build();
    } catch (Exception e) {
      throw new RuntimeException("Could not load additional adjacency matrix for " + filePath, e);
    }
  }

  static String uppercaseTheColorHex(String unformattedHex) {
    return 'x' + unformattedHex.substring(1).toUpperCase(Locale.ROOT);
  }

  public static ImmutableSet<ImmutableSet<String>> getGroups(
      ImmutableSet<String> allowedProvinces,
      ImmutableSetMultimap<String, String> adjacencyMatrix,
      Integer minGroupSize) {
    log.debug("Calculating groups with min size of {}", minGroupSize);
    var groups = new HashSet<Set<String>>();
    var visited = new HashSet<String>();

    for (var province : allowedProvinces) {
      if (visited.contains(province)) {
        continue;
      }
      var group = new ArrayList<String>();
      visited.add(province);
      group.add(province);
      var currentProvince = province;
      var i = 0;
      while (group.size() < minGroupSize) {
        for (var adjacent : adjacencyMatrix.get(currentProvince)) {
          if (!visited.contains(adjacent) && allowedProvinces.contains(adjacent)) {
            visited.add(adjacent);
            group.add(adjacent);
          }
        }
        if (i + 1 > group.size()) {
          break;
        }
        currentProvince = group.get(i++);
      }
      groups.add(new HashSet<>(group));
    }

    consolidateSmallGroups(groups, adjacencyMatrix, minGroupSize / 5);

    return groups.stream()
        .filter(not(Set::isEmpty))
        .map(ImmutableSet::copyOf)
        .collect(toImmutableSet());
  }

  private static void consolidateSmallGroups(
      Set<Set<String>> groups,
      ImmutableSetMultimap<String, String> adjacencyMatrix,
      int consolidationThreshold) {
    log.debug("Consolidating groups smaller than {}", consolidationThreshold);
    var smallGroups =
        groups.stream().filter(group -> group.size() < consolidationThreshold).iterator();
    while (smallGroups.hasNext()) {
      var smallGroup = smallGroups.next();
      var targetGroup = Optional.<Set<String>>empty();
      for (var province : smallGroup) {
        for (var adjacentProvince : adjacencyMatrix.get(province)) {
          targetGroup =
              groups.stream()
                  .filter(g -> g.contains(adjacentProvince))
                  .filter(g -> !g.equals(smallGroup))
                  .findAny();
          if (targetGroup.isPresent()) break;
        }
        if (targetGroup.isPresent()) break;
      }
      if (targetGroup.isPresent()) {
        targetGroup.orElseThrow().addAll(smallGroup);
        smallGroup.clear();
      }
    }
  }
}
