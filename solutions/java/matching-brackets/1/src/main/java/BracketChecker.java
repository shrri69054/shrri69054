import java.util.Stack;

class BracketChecker {
    private final String expression;

    BracketChecker(String expression) {
        this.expression = expression;
    }

    boolean areBracketsMatchedAndNestedCorrectly() {
        Stack<Character> stack = new Stack<>();
        
        for (char c : expression.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if (!isMatchingPair(top, c)) {
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }

    private boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }

    public static void main(String[] args) {
        BracketChecker checker1 = new BracketChecker("{what is (42)}?");
        System.out.println(checker1.areBracketsMatchedAndNestedCorrectly());  // Should print: true

        BracketChecker checker2 = new BracketChecker("[text}");
        System.out.println(checker2.areBracketsMatchedAndNestedCorrectly());  // Should print: false
    }
}