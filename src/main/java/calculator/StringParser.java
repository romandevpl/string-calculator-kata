package main.java.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {
    public static final Pattern SINGLE_DELIMITER_PATTERN = Pattern.compile("^(//(.*)\n).*$");
    public static final Pattern MULTIPLE_DELIMITER_PATTERN = Pattern.compile("\\[(.+?)\\]");
    public static final List<String> DEFAULT_DELIMITERS = Arrays.asList(",", "\n");

    public List<String> parseStringExpression(String expression) {
        List<String> delimiters = findDelimiters(expression);
        return extractNumbers(expression, delimiters);
    }

    private List<String> findDelimiters(String statement) {
        Matcher matcher = SINGLE_DELIMITER_PATTERN.matcher(statement);
        if (matcher.matches()) {
            return parseDelimiters(matcher.group(2));
        } else {
            return DEFAULT_DELIMITERS;
        }
    }

    private List<String> parseDelimiters(String delimiterExpression) {
        Matcher matcher = MULTIPLE_DELIMITER_PATTERN.matcher(delimiterExpression);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result.isEmpty() ? Arrays.asList(delimiterExpression) : result;
    }

    private List<String> extractNumbers(String statement, List<String> delimiters) {
        Matcher matcher = SINGLE_DELIMITER_PATTERN.matcher(statement);
        if (matcher.matches()) {
            statement = statement.substring(matcher.group(1).length());
        }
        List<String> result = Arrays.asList(statement);
        for (String delimiter : delimiters) {
            List<String> splitNumbers = new ArrayList<>();
            for (String element : result) {
                if (element.equals(delimiter))
                    throw new RuntimeException("Wrong input format : double delimiter.");
                splitNumbers.addAll(Arrays.asList(element.split(Pattern.quote(delimiter))));
            }
            result = splitNumbers;
        }
        return result;
    }
}
