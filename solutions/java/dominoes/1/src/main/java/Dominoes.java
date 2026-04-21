import java.util.*;

public class Dominoes {
    private List<Domino> chain = new ArrayList<>();
    private List<Domino> input;
    private int inputSize;

    public List<Domino> formChain(List<Domino> inputList) throws ChainNotFoundException {
        if (input == null) {
            this.input = new LinkedList<>(inputList);
            checkIfEulersCycle(input);
            this.inputSize = input.size();
        }

        traverse(input);

        if (chain.size() != inputSize) {
            throw new ChainNotFoundException("No domino chain found.");
        }

        return chain;
    }

    private void checkIfEulersCycle(List<Domino> list) throws ChainNotFoundException {
        List<Integer> listOfDegrees = new ArrayList<>();
        for (Domino d : list) {
            listOfDegrees.add(d.getLeft());
            listOfDegrees.add(d.getRight());
        }

        for (Integer i : listOfDegrees) {
            if (Collections.frequency(listOfDegrees, i) % 2 != 0) {
                throw new ChainNotFoundException("No domino chain found.");
            }
        }
    }

    private void traverse(List<Domino> list) {
        for (int i = 0; i < list.size(); ++i) {
            Domino dom = list.get(i);
            if (canAppend(dom, chain)) {
                chain.add(dom);
                Domino saved = list.remove(i);
                traverse(list);
                if (chain.size() == inputSize) {
                    return;
                }
                list.add(i, saved);
                chain.remove(chain.size()-1);
            }
            dom = new Domino(dom.getRight(), dom.getLeft());
            if (canAppend(dom, chain)) {
                chain.add(dom);
                Domino saved = list.remove(i);
                traverse(list);
                list.add(i, saved);
                if (chain.size() == inputSize) {
                    return;
                }
                chain.remove(chain.size()-1);
            }
        }
    }

    private boolean canAppend(Domino dom, List<Domino> to) {
        return to.isEmpty() || to.get(to.size()-1).getRight() == dom.getLeft();
    }
}