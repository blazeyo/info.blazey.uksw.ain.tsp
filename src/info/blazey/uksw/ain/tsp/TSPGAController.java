package info.blazey.uksw.ain.tsp;

import java.util.Random;

/**
 *
 * @author blazej
 */
public class TSPGAController {

  private final double mutationChance = 0.02;
  private final int tournamentGroupSize = 3;
  private final int populationSize = 150;
  private final int generationsCount = 150;
  
  private static Random randomGenerator;
  private static int seed;

  static {
    Random r = new Random();
    seed = r.nextInt();
    randomGenerator = new Random(seed);
  }

  public static int nextInt(int range) {
//    Random r = new Random();
//    return r.nextInt(range);
    return randomGenerator.nextInt(range);
  }

  public static double nextDouble() {
    return randomGenerator.nextDouble();
  }

  private Generation generation;

  public TSPGAController(Graph graph) {
    PathSciezkowaMutationOperatorSwap mutationOperator = new PathSciezkowaMutationOperatorSwap(this.mutationChance);
    PathSciezkowaCrossoverOperatorWithEdgeRecombination crossoverOperator = new PathSciezkowaCrossoverOperatorWithEdgeRecombination();
    PathSelectionOperatorTournament selectionOperator = new PathSelectionOperatorTournament(this.tournamentGroupSize);

    this.generation = new GenerationTSPSciezkowa(graph, selectionOperator, crossoverOperator, mutationOperator);
    this.generation.initialize(this.populationSize);
  }

  public Generation getFinalGeneration() {
    for (int i = 0; i < generationsCount; i++) {
      Main.log(generation.getStatistics().toString());
      generation = generation.getNext();
    }
    Main.log("Final generation: " + generation.getStatistics().toString());
    return this.generation;
  }

}
