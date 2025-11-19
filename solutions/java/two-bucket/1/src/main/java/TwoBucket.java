import java.util.*;
class TwoBucket {
    private final Result result;
    TwoBucket(int bucketOneCap, int bucketTwoCap, int desiredLiters, String startBucket) {
        boolean isBucketOne = startBucket.equals("one");
        Queue<TreeNode> queue = new ArrayDeque<>();
        Set<TreeNode> used = new HashSet<>();
        TreeNode root = new TreeNode(isBucketOne ? bucketOneCap : 0, isBucketOne ? 0 : bucketTwoCap, isBucketOne ? Action.FILL_ONE : Action.FILL_TWO, 1);
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.getBucketOneLiters() == desiredLiters) {
                result = new Result(node.getDepth(), "one", node.getBucketTwoLiters());
                return;
            } else if (node.getBucketTwoLiters() == desiredLiters) {
                result = new Result(node.getDepth(), "two", node.getBucketOneLiters());
                return;
            }
            Bucket bucketOne = new Bucket(bucketOneCap, node.getBucketOneLiters());
            Bucket bucketTwo = new Bucket(bucketTwoCap, node.getBucketTwoLiters());
            Set<Action> validActions = getValidActions(bucketOne, bucketTwo, node.getAction());
            for (Action action : validActions) {
                TreeNode child = getChild(bucketOne.copy(), bucketTwo.copy(), action, node.getDepth() + 1);
                if (used.contains(child) || (isBucketOne ? child.getBucketOneLiters() == 0 && child.getBucketTwoLiters() == bucketTwoCap : child.getBucketOneLiters() == bucketOneCap && child.getBucketTwoLiters() == 0)) continue;
                queue.add(child);
                used.add(child);
            }
        }
        result = null;
    }
    private Set<Action> getValidActions(Bucket bucketOne, Bucket bucketTwo, Action previousAction) {
        Set<Action> result = new HashSet<>(Arrays.asList(Action.values()));
        if (bucketOne.isEmpty()) {
            result.remove(Action.EMPTY_ONE);
            result.remove(Action.POUR_ONE_TO_TWO);
        } else if (bucketOne.isFull()) {
            result.remove(Action.FILL_ONE);
            result.remove(Action.POUR_TWO_TO_ONE);
        }
        if (bucketTwo.isEmpty()) {
            result.remove(Action.EMPTY_TWO);
            result.remove(Action.POUR_TWO_TO_ONE);
        } else if (bucketTwo.isFull()) {
            result.remove(Action.FILL_TWO);
            result.remove(Action.POUR_ONE_TO_TWO);
        }
        switch (previousAction) {
            case EMPTY_ONE -> result.remove(Action.FILL_ONE);
            case EMPTY_TWO -> result.remove(Action.FILL_TWO);
            case FILL_ONE -> result.remove(Action.EMPTY_ONE);
            case FILL_TWO -> result.remove(Action.EMPTY_TWO);
        }
        return result;
    }
    private TreeNode getChild(Bucket bucketOne, Bucket bucketTwo, Action action, int depth) {
        switch (action) {
            case POUR_ONE_TO_TWO -> bucketOne.pour(bucketTwo);
            case POUR_TWO_TO_ONE -> bucketTwo.pour(bucketOne);
            case EMPTY_ONE -> bucketOne.empty();
            case EMPTY_TWO -> bucketTwo.empty();
            case FILL_ONE -> bucketOne.fill();
            case FILL_TWO -> bucketTwo.fill();
        }
        return new TreeNode(bucketOne.getLiters(), bucketTwo.getLiters(), action, depth);
    }
    Result getResult() throws UnreachableGoalException {
        if (result == null) throw new UnreachableGoalException();
        return result;
    }
}
class TreeNode {
    private final int bucketOneLiters, bucketTwoLiters, depth;
    private final Action action;
    public TreeNode(int bucketOneLiters, int bucketTwoLiters, Action action, int depth) {
        this.bucketOneLiters = bucketOneLiters;
        this.bucketTwoLiters = bucketTwoLiters;
        this.depth = depth;
        this.action = action;
    }
    public int getBucketOneLiters() {
        return bucketOneLiters;
    }
    public int getBucketTwoLiters() {
        return bucketTwoLiters;
    }
    public int getDepth() {
        return depth;
    }
    public Action getAction() {
        return action;
    }
    @Override
    public int hashCode() {
        return Objects.hash(bucketOneLiters, bucketTwoLiters, action);
    }
    @Override
    public boolean equals(Object obj) {
        TreeNode other = (TreeNode) obj;
        return this.bucketOneLiters == other.getBucketOneLiters() && this.bucketTwoLiters == other.getBucketTwoLiters() && this.action == other.getAction();
    }
}
class Bucket {
    private final int capacity;
    private int liters;
    public Bucket(int capacity, int liters) {
        this.capacity = capacity;
        this.liters = liters;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setLiters(int liters) {
        this.liters = liters;
    }
    public int getLiters() {
        return liters;
    }
    public Bucket copy() {
        return new Bucket(capacity, liters);
    }
    public void pour(Bucket otherBucket) {
        int otherLiters = otherBucket.getLiters();
        int otherRemainder = otherBucket.getCapacity() - otherLiters;
        if (otherRemainder >= this.liters) {
            otherBucket.setLiters(otherLiters + this.liters);
            this.empty();
        } else {
            otherBucket.fill();
            this.setLiters(this.liters - otherRemainder);
        }
    }
    public void empty() {
        setLiters(0);
    }
    public boolean isEmpty() {
        return liters == 0;
    }
    public void fill() {
        setLiters(capacity);
    }
    public boolean isFull() {
        return liters == capacity;
    }
}
enum Action {
    POUR_ONE_TO_TWO,
    POUR_TWO_TO_ONE,
    EMPTY_ONE,
    EMPTY_TWO,
    FILL_ONE,
    FILL_TWO
}