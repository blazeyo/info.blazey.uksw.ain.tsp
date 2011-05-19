package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author blazej
 */
public class PathSelectionOperatorTournament implements PathSelectionOperator {
  
  private int groupSize;

  public PathSelectionOperatorTournament(int groupSize) {
    this.groupSize = groupSize;
  }

  public ArrayList<Path> filter(ArrayList<Path> paths) {
    ArrayList<Path> result = new ArrayList<Path>();

    int counter = 0;
    while (result.size() != paths.size()) {
      counter++;
      result.add(this.getNewTournamentWinner(paths));
    }
    Main.log("Performed " + counter + " selections");

    return result;
  }

  private Path getNewTournamentWinner(ArrayList<Path> paths) {
    ArrayList<Path> group = new ArrayList<Path>();
    for (int i = 0; i < this.groupSize; i++) {
      Path p = paths.get(TSPGAController.nextInt(paths.size()));
      group.add(p);
    }
    Collections.sort(group);
    return group.get(0);
  }

  @Override
  public String toString() {
    return "Tournament selection";
  }

}
