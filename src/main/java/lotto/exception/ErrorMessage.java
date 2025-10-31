package lotto.exception;

public enum ErrorMessage {

    PURCHASE_NOT_NUMBER("구입 금액은 유효한 숫자 형식이어야 합니다."),
    PURCHASE_OUT_OF_RANGE("구입 금액이 유효한 범위를 벗어났습니다."),
    PURCHASE_NOT_POSITIVE("구입 금액이 양수가 아닙니다."),
    PURCHASE_NOT_UNIT("구입 금액이 1,000원 단위로 나누어 떨어지지 않습니다."),
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
