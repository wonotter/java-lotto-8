package lotto.model;

import java.util.Objects;
import lotto.exception.ErrorMessage;

public class BonusNumber {

    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;

    private final int number;

    public BonusNumber(String input, WinningNumbers winningNumbers) {
        validateNotNullOrEmpty(input);
        int parsedNumber = parseNumber(input);
        validateRange(parsedNumber);
        validateNotDuplicate(parsedNumber, winningNumbers);

        this.number = parsedNumber;
    }

    private void validateNotNullOrEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_NULL_OR_EMPTY.getMessage());
        }
    }

    private int parseNumber(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_NOT_NUMBER.getMessage(), e);
        }
    }

    private void validateRange(int parsedNumber) {
        if (parsedNumber < MIN_LOTTO_NUMBER || parsedNumber > MAX_LOTTO_NUMBER) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_INVALID_RANGE.getMessage());
        }
    }

    private void validateNotDuplicate(int parsedNumber, WinningNumbers winningNumbers) {
        if (winningNumbers.contains(parsedNumber)) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_DUPLICATE.getMessage());
        }
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BonusNumber that = (BonusNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
