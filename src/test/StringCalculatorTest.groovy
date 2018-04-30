package test

import main.java.calculator.StringCalculator
import spock.lang.Specification

class StringCalculatorTest extends Specification {

    StringCalculator stringCalculator = new StringCalculator()

    def "Empty string should return zero"() {
        when:
        def result = stringCalculator.Add("")
        then:
        result == 0
    }

    def "Calculator with one number returns the same number"() {
        when:
        def result = stringCalculator.Add("1")
        then:
        result == 1
    }

    def "Calculator with two numbers return the sum of them"() {
        when:
        def result = stringCalculator.Add("1,2")
        then:
        result == 1 + 2
    }

    def "Calculator with few numbers returns the sum of all of them"() {
        when:
        def result = stringCalculator.Add("1,2,3,4,5")
        then:
        result == 1 + 2 + 3 + 4 + 5
    }

    def "Double comma is not accepted"() {
        when:
        def result = stringCalculator.Add("1,,2,3")
        then:
        thrown Exception
    }

    def "New line character should be handled"() {
        when:
        def result = stringCalculator.Add("1\n2,3")
        then:
        result == 1 + 2 + 3
    }

    def "Both new line next to comma delimiters are not accepted"() {
        when:
        def result = stringCalculator.Add("1,\n")
        then:
        thrown Exception
    }

    def "New delimiter should be handled"() {
        when:
        String numbers = '''//;\n1;2'''
        def result = stringCalculator.Add(numbers)
        then:
        result == 1 + 2
    }

    def "Negative numbers are not allowed"() {
        when:
        String numbers = "1,-2,3,-4,0"
        def result = stringCalculator.Add(numbers)
        then:
        Exception exception = thrown()
        exception.message == "Negatives not allowed: [-2, -4]"
    }

    def "Numbers greater than 1000 are ignored"() {
        when:
        String numbers = "5,1000,10001"
        def result = stringCalculator.Add(numbers)
        then:
        result == 5 + 1000
    }

    def "Delimiter in brackets is handled"() {
        when:
        String expression = "//[+]\n5+5"
        def result = stringCalculator.Add(expression)
        then:
        result == 5 + 5
    }

    def "Multiple delimiters in brackets are handled"() {
        when:
        String expression = "//[++][**][xx]\n5++10**15xx20"
        def result = stringCalculator.Add(expression)
        then:
        result == 5 + 10 + 15 + 20
    }
}
