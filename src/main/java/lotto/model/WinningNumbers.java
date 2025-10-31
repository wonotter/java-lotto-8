package lotto.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lotto.exception.ErrorMessage;

public class WinningNumbers {

    private static final int WINNING_NUMBER_COUNT = 6;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;
    private static final String DELIMITER = ",";

    private final List<Integer> numbers;

    public WinningNumbers(String input) {
        validateNotNullOrEmpty(input);
        List<Integer> parsedNumbers = parseNumbers(input);
        validateCount(parsedNumbers);
        validateRange(parsedNumbers);
        validateDuplicate(parsedNumbers);

        this.numbers = parsedNumbers;
    }

    private void validateNotNullOrEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.WINNING_NUMBERS_NULL_OR_EMPTY.getMessage());
        }
    }

    private List<Integer> parseNumbers(String input) {
        try {
            return Arrays.stream(input.split(DELIMITER))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.WINNING_NUMBERS_NOT_NUMBER.getMessage(), e);
        }
    }

    private void validateCount(List<Integer> parsedNumbers) {
        if (parsedNumbers.size() != WINNING_NUMBER_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.WINNING_NUMBERS_INVALID_COUNT.getMessage());
        }
    }

    private void validateRange(List<Integer> parsedNumbers) {
        boolean isOutOfRange = parsedNumbers.stream()
                .anyMatch(number -> number < MIN_LOTTO_NUMBER || number > MAX_LOTTO_NUMBER);

        if (isOutOfRange) {
            throw new IllegalArgumentException(ErrorMessage.WINNING_NUMBERS_INVALID_RANGE.getMessage());
        }
    }

    private void validateDuplicate(List<Integer> parsedNumbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(parsedNumbers);

        if (uniqueNumbers.size() != parsedNumbers.size()) {
            throw new IllegalArgumentException(ErrorMessage.WINNING_NUMBERS_DUPLICATE.getMessage());
        }
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public boolean contains(int number) {
        return numbers.contains(number);
    }

    // Lotto 객체 번호와 비교하여 당첨 판별 메서드
    public int countMatches(List<Integer> otherNumbers) {
        return (int) otherNumbers.stream()
                .filter(numbers::contains)
                .count();
    }
}
