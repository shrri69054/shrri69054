public class LogLevels {

    // Extract the message from the log line
    public static String message(String logLine) {
        // Split the string at ":" and return the message part, trimming any leading/trailing whitespace
        String[] parts = logLine.split(": ", 2);
        return parts[1].trim();
    }

    // Extract the log level from the log line and convert it to lowercase
    public static String logLevel(String logLine) {
        // Remove the surrounding brackets and return the log level in lowercase
        return logLine.substring(1, logLine.indexOf("]")).trim().toLowerCase();
    }

    // Reformat the log line to "message (level)" format
    public static String reformat(String logLine) {
        // Get the message and log level
        String message = message(logLine);
        String level = logLevel(logLine); // No need to call .toLowerCase() here again
        // Return the formatted string: message (level)
        return message + " (" + level + ")";
    }

    // This main method is for testing and can be omitted in your solution if not required by Exercism
    public static void main(String[] args) {
        // Test the methods with sample log lines
        String logLine1 = "[ERROR]: Invalid operation";
        System.out.println(LogLevels.message(logLine1));  // Output: "Invalid operation"
        System.out.println(LogLevels.logLevel(logLine1));  // Output: "error"
        System.out.println(LogLevels.reformat(logLine1));  // Output: "Invalid operation (error)"

        String logLine2 = "[WARNING]: Disk almost full\r\n";
        System.out.println(LogLevels.message(logLine2));  // Output: "Disk almost full"
        System.out.println(LogLevels.logLevel(logLine2));  // Output: "warning"
        System.out.println(LogLevels.reformat(logLine2));  // Output: "Disk almost full (warning)"

        String logLine3 = "[INFO]: Operation completed";
        System.out.println(LogLevels.message(logLine3));  // Output: "Operation completed"
        System.out.println(LogLevels.logLevel(logLine3));  // Output: "info"
        System.out.println(LogLevels.reformat(logLine3));  // Output: "Operation completed (info)"
    }
}
