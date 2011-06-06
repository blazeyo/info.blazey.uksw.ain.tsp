package info.blazey.uksw.ain.tsp;

/**
 *
 * @author blazej
 */
public class GenerationStatisticsTSP implements GenerationStatistics {

  private double bestPathLenght = 0;

  private GenerationTSP generation;

  public GenerationStatisticsTSP(GenerationTSP generation) {
    this.generation = generation;

    Path shortestTour = generation.getGraph().getShortestTour();
    if (shortestTour != null) {
      bestPathLenght = shortestTour.getDistance();
    }
  }

  public double getBest() {
    Path bestPath = null;
    for (Path p : generation.getPopulation()) {
      if (bestPath == null || p.getDistance() < bestPath.getDistance()) {
        bestPath = p;
      }
    }
    return bestPath.getDistance();
  }

  public double getAverage() {
    double sum = 0;
    int count = 0;
    for (Path p : generation.getPopulation()) {
      sum += p.getDistance();
      count += 1;
    }
    return (double)sum / count;
  }

  public double getWorst() {
    Path worstPath = null;
    for (Path p : generation.getPopulation()) {
      if (worstPath == null || p.getDistance() > worstPath.getDistance()) {
        worstPath = p;
      }
    }
    return worstPath.getDistance();
  }

  @Override
  public String toString() {
    return "\tBest: " + getBest() + "; Average: " + getAverage() + "; Worst: " + getWorst();
  }

  public String getGnuplotRow(int generationNumber) {
    return generationNumber + "\t\t" + getBest() + "\t\t" + getAverage() + "\t\t" + getWorst() + "\t\t" + bestPathLenght + "\n";
  }

}
