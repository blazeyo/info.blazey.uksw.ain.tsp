package info.blazey.uksw.ain.tsp;

import info.blazey.uksw.ain.tsp.Graph.EdgeExistsException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author blazej
 */
public class TSPLibParser {

  private static GraphUndirected graph;
  private static ArrayList<Node> towns;
  
  private static Scanner scanner;

  private static String edgeWeightType;
  private static String edgeWeightFormat;

  private static void init(String resourceName) {
    graph = new GraphUndirected();
    towns = new ArrayList<Node>();
    scanner = new Scanner(TSPGAController.class.getResourceAsStream(resourceName));

    edgeWeightType = "";
    edgeWeightFormat = "";

    extractFileFormat();
  }

  public static boolean graphIsSupported(String resourceName) {
    if (!resourceName.endsWith(".tsp")) {
      return false;
    }
    init(resourceName);

    if (edgeWeightType.equals("EXPLICIT")) {
      if (edgeWeightFormat.equals("UPPER_ROW")) {
        return true;
      }
    } else if (edgeWeightType.equals("EUC_2D")) {
      return true;
    }

    return false;
  }

  public static Graph getGraph(String resourceName) {
    init(resourceName);

    Main.log("* Filling graph");

    scanner = new Scanner(TSPGAController.class.getResourceAsStream(resourceName));
    try {
      if (edgeWeightType.equals("EXPLICIT")) {
        if (edgeWeightFormat.equals("UPPER_ROW")) {
          fillGraphExplicitUpperRow();
        } else {
          return null;
        }
      } else if (edgeWeightType.equals("EUC_2D")) {
        fillGraphEuc2d();
      } else {
        return null;
      }
    } catch (EdgeExistsException ex) {
      ex.printStackTrace();
    }
    

    Main.log("* Graph filled:");
    Main.log("\tName: " + graph.getName());
    Main.log("\tComment: " + graph.getComment());
    Main.log("\tSize: " + graph.getSize());

    return graph;
  }

  private static void extractFileFormat() {
    try {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        if (line.indexOf(":") == -1) {
          break;
        }

        String paramName = line.split(":")[0].trim();
        String paramValue = line.split(":")[1].trim();

        if (paramName.equals("NAME")) {
          graph.setName(paramValue);
        }
        if (paramName.equals("COMMENT")) {
          graph.setComment(paramValue);
        }
        if (paramName.equals("DIMENSION")) {
          graph.setSize(Integer.parseInt(paramValue));
        }
        if (paramName.equals("EDGE_WEIGHT_TYPE")) {
          edgeWeightType = paramValue;
        }
        if (paramName.equals("EDGE_WEIGHT_FORMAT")) {
          edgeWeightFormat = paramValue;
        }
      }
    } finally {
      scanner.close();
    }
  }

  private static void fillGraphEuc2d() throws EdgeExistsException {
    // Move scanner pointer to weights section
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (line.equals("NODE_COORD_SECTION"))
        break;
    }

    ArrayList<NodeWithCoordinates> nodes = new ArrayList<NodeWithCoordinates>();
    int row = 0;
    BIGWHILE:
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      String[] lineElements = line.split(" ");

      if (lineElements.length == 3) {
        double x = Double.parseDouble(lineElements[1]);
        double y = Double.parseDouble(lineElements[2]);

        nodes.add(new NodeWithCoordinates(row++, x, y));
      }
    }

    for (NodeWithCoordinates from : nodes) {
      for (NodeWithCoordinates to : nodes) {
        if (!graph.edgeExists(graph.getTownEdges(from), to)) {
          graph.addEdge(from, to, from.getDistanceTo(to));
        }
      }
    }
  }

  private static void fillGraphExplicitUpperRow() throws EdgeExistsException {
    // Move scanner pointer to weights section
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.equals("EDGE_WEIGHT_SECTION"))
        break;
    }

    int row = 0;
    BIGWHILE:
    while (scanner.hasNextLine()) {
      // We start from first column as diagonal items are removed from the matrix
      int column = 1 + row;
      Node origin = getTown(row++);
      String line = scanner.nextLine().trim();
      if (line.split(" ").length > 0 && line.equals("EOF") == false) {
        for (String distance : line.split(" ")) {
          if (distance.trim().length() < 1) {
            continue;
          }
          Node destination = getTown(column++);
          try {
            graph.addEdge(origin, destination, Double.parseDouble(distance.trim()));
          } catch (NumberFormatException e) {
            break BIGWHILE;
          }
        }
      }
    }
  }

  private static Node getTown(int index) {
    // We are adding towns with consequent indexes
    if (towns.size() < index + 1) {
      towns.add(index, new Node(index));
    }
    return towns.get(index);
  }

}
