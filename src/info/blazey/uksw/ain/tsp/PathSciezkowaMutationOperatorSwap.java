package info.blazey.uksw.ain.tsp;

/**
 *
 * @author blazej
 */
public class PathSciezkowaMutationOperatorSwap implements PathMutationOperator {

  private double chance;

  public PathSciezkowaMutationOperatorSwap(double chance) {
    this.chance = chance;
  }

  public void performMutation(Path path) {
    int index1 = TSPGAController.nextInt(path.getNodesCount());
    int index2 = TSPGAController.nextInt(path.getNodesCount());
    while (index1 == index2) {
      index2 = TSPGAController.nextInt(path.getNodesCount());
    }
    path.swapNodes(index1, index2);
    
//    Main.log("Mutation performed. Path valid: " + path.isValid());
  }

  public boolean mutate() {
    return TSPGAController.nextDouble() <= chance;
  }

  @Override
  public String toString() {
    return "Swap 2 random towns";
  }

}
