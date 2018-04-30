package main.java.calculator;

import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {

    public static final int MAX_NUMBER = 1000;

    public int Add(String numbers) {
        if (numbers == null || numbers.equals("")) {
            return 0;
        }
        StringParser parser = new StringParser();
        List<String> extractedNumbers = parser.parseStringExpression(numbers);
        List<Integer> negativeNumbers = findNegativeNumbers(extractedNumbers);
        if (!negativeNumbers.isEmpty()) {
            throw new RuntimeException("Negatives not allowed: " + negativeNumbers);
        }
        return extractedNumbers.stream()
                .mapToInt(Integer::parseInt)
                .filter(n -> n <= MAX_NUMBER)
                .sum();

    }

    private List<Integer> findNegativeNumbers(List<String> numbers) {
        return numbers.stream()
                .mapToInt(Integer::parseInt)
                .filter(n -> n < 0)
                .boxed()
                .collect(Collectors.toList());
    }
}
