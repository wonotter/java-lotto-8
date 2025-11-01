package lotto.model.ticket;

import java.util.Objects;
import lotto.exception.ErrorMessage;

public class PurchaseAmount {

    private static final int LOTTO_PRICE = 1000;

    private final int amount;

    public PurchaseAmount(String input) {
        validateNotNullOrEmpty(input);
        int parsedAmount = parseAmount(input);
        validatePositive(parsedAmount);
        validateUnit(parsedAmount);

        this.amount = parsedAmount;
    }

    private void validateNotNullOrEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_NULL_OR_EMPTY.getMessage());
        }
    }

    private int parseAmount(String input) {
        long longValue = parseLong(input);
        validateIntRange(longValue);

        return (int) longValue;
    }

    private long parseLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_NOT_NUMBER.getMessage(), e);
        }
    }

    private void validateIntRange(long longValue) {
        if (longValue < Integer.MIN_VALUE || longValue > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_OUT_OF_RANGE.getMessage());
        }
    }

    private void validatePositive(int parsedAmount) {
        if (parsedAmount <= 0) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_NOT_POSITIVE.getMessage());
        }
    }

    private void validateUnit(int parsedAmount) {
        if (parsedAmount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_NOT_UNIT.getMessage());
        }
    }

    public int getTicketCount() {
        return amount / LOTTO_PRICE;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        PurchaseAmount that = (PurchaseAmount) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
