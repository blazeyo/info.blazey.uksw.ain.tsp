package info.blazey.uksw.ain.tsp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author blazej
 */
public class TSPGAController {

  private final int populationSize = 150;
  private final int generationsCount = 500;
  private final double mutationChance = 0.05;
  private final int tournamentGroupSize = 2;
  private final double crossoverChance = 0.7;
  
  private static Random randomGenerator;
  private static int seed;

  static {
    Random r = new Random();
    seed = r.nextInt();
    randomGenerator = new Random(seed);
  }

  public static int nextInt(int range) {
    return randomGenerator.nextInt(range);
  }

  public static double nextDouble() {
    return randomGenerator.nextDouble();
  }

  private Generation generation;

  public TSPGAController(Graph graph) {
    PathSciezkowaMutationOperatorSwap mutationOperator = new PathSciezkowaMutationOperatorSwap(this.mutationChance);

//    PathSciezkowaCrossoverOperatorWithEdgeRecombination crossoverOperator = new PathSciezkowaCrossoverOperatorWithEdgeRecombination();
    PathCrossoverOperatorOX crossoverOperator = new PathCrossoverOperatorOX(crossoverChance);

//    PathSelectionOperatorTournament selectionOperator = new PathSelectionOperatorTournament(this.tournamentGroupSize);
    PathSelectionOperator selectionOperator = new PathSelectionOperatorRouletteWheel();


    this.generation = new GenerationTSPSciezkowa(graph, selectionOperator, crossoverOperator, mutationOperator);
    this.generation.initialize(this.populationSize);
  }

  private StringBuilder gnuplotFileContent;

  public Generation getFinalGeneration() throws IOException {
    gnuplotFileContent = new StringBuilder();

    logStats(1);
    for (int i = 0; i < generationsCount; i++) {
      generation = generation.getNext();
      logStats(2+i);
    }
    saveGnuplotFile();

    return this.generation;
  }

  private void logStats(int generationCounter) {
    Main.log(generation.getStatistics().toString());
    gnuplotFileContent.append(generation.getStatistics().getGnuplotRow(generationCounter));
  }

  private void saveGnuplotFile() throws IOException {
    String content =
              "# Nature inspired algorithms\n"
            + "# Solving symetric travelling salesman problem with GA.\n"
            + "# Błażej Owczarczyk\n"
            + "#\n"
            + "# generations count: " + generationsCount + "\n"
            + "# population size: " + populationSize + "\n"
            + "# mutation chance: " + mutationChance + "\n"
            + "# tournament group size: " + tournamentGroupSize + "\n"
            + "#\n"
            + "# seed: " + seed + "\n"
            + "#\n"
            + "# generation-number best-individual average-individual worst-individual\n"
            + gnuplotFileContent.toString();

    File outputFile = getGnuplotFileHandler();
    Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));
    try {
      out.write(content);
    } finally {
      out.close();
    }

    GnuplotImageGenerator g = new GnuplotImageGenerator(outputFile, "TSP GA", "Generations", "Path Length");
    g.addPlot(1, 2, "Best");
    g.addPlot(1, 3, "Average");
    g.generate();
  }

  private File getGnuplotFileHandler() throws IOException {
    File dataFile = new File("TSP-GA");
    if (dataFile.exists()) {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
      String newFileName = "TSP-GA-" + format.format(new Date());

      File dataFileCopy = new File(newFileName);
      dataFile.renameTo(dataFileCopy);
      dataFile = new File("TSP-GA");

      File imageFile = new File(dataFile.getPath() + ".png");
      if (imageFile.exists()) {
        File imageFileCopy = new File(newFileName + ".png");
        imageFile.renameTo(imageFileCopy);
      }
    } else {
      dataFile.createNewFile();
    }
    return dataFile;
  }

}
