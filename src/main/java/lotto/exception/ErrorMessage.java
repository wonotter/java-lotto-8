package lotto.exception;

public enum ErrorMessage {

    PURCHASE_NOT_NUMBER("구입 금액은 유효한 숫자 형식이어야 합니다."),
    PURCHASE_OUT_OF_RANGE("구입 금액이 유효한 범위를 벗어났습니다."),
    PURCHASE_NOT_POSITIVE("구입 금액이 양수가 아닙니다."),
    PURCHASE_NOT_UNIT("구입 금액이 1,000원 단위로 나누어 떨어지지 않습니다."),

    WINNING_NUMBERS_NULL_OR_EMPTY("당첨 번호 값이 비어있습니다."),
    WINNING_NUMBERS_NOT_NUMBER("당첨 번호는 유효한 숫자 형식이어야 합니다."),
    WINNING_NUMBERS_INVALID_COUNT("당첨 번호는 6개여야 합니다."),
    WINNING_NUMBERS_INVALID_RANGE("당첨 번호는 1부터 45 사이의 숫자여야 합니다."),
    WINNING_NUMBERS_DUPLICATE("당첨 번호는 중복될 수 없습니다."),

    BONUS_NUMBER_NULL_OR_EMPTY("보너스 번호 값이 비어있습니다."),
    BONUS_NUMBER_NOT_NUMBER("보너스 번호는 유효한 숫자 형식이어야 합니다."),
    BONUS_NUMBER_INVALID_RANGE("보너스 번호는 1부터 45 사이의 숫자여야 합니다."),
    BONUS_NUMBER_DUPLICATE("보너스 번호는 당첨 번호와 중복될 수 없습니다."),

    LOTTO_NUMBERS_MUST_BE_SIX("로또 번호는 6개여야 합니다."),
    LOTTO_NUMBERS_INVALID_RANGE("로또 번호는 1부터 45 사이의 숫저여야 합니다."),
    LOTTO_NUMBERS_DUPLICATE("로또 번호는 중복될 수 없습니다."),
    ;

    private final String ERROR_PREFIX = "[ERROR] ";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}
