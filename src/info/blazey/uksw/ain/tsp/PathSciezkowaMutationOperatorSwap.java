package info.blazey.uksw.ain.tsp;

/**
 *
 * @author blazej
 */
public class PathSciezkowaMutationOperatorSwap extends PathMutationOperator {

  public PathSciezkowaMutationOperatorSwap(double chance) {
    super(chance);
  }

  public void performMutation(Path path) {
    int index1 = TSPGAController.nextInt(path.getNodesCount());
    int index2 = TSPGAController.nextInt(path.getNodesCount());
    while (index1 == index2) {
      index2 = TSPGAController.nextInt(path.getNodesCount());
    }
    path.swapNodes(index1, index2);
  }

  @Override
  public String toString() {
    return "Swap 2 random towns";
  }

}
