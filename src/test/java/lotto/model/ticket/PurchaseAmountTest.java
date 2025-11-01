package lotto.model.ticket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lotto.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PurchaseAmountTest {

    @Nested
    @DisplayName("정상 동작 테스트")
    class 정상_동작_테스트 {

        @DisplayName("올바른 구입 금액으로 객체를 생성한다")
        @Test
        void 올바른_구입_금액으로_객체를_생성한다() {
            PurchaseAmount purchaseAmount = new PurchaseAmount("8000");

            assertThat(purchaseAmount.getAmount()).isEqualTo(8000);
        }

        @DisplayName("구입 금액에 따라 올바른 로또 장수를 계산한다")
        @ParameterizedTest
        @ValueSource(strings = {"1000", "5000", "8000", "10000"})
        void 구입_금액에_따라_올바른_로또_장수를_계산한다(String input) {
            PurchaseAmount purchaseAmount = new PurchaseAmount(input);
            int expectedCount = Integer.parseInt(input) / 1000;

            assertThat(purchaseAmount.getTicketCount()).isEqualTo(expectedCount);
        }

        @DisplayName("최소 금액 1000원으로 로또 1장을 구매할 수 있다")
        @Test
        void 최소_금액_천원으로_로또_1장을_구매할_수_있다() {
            PurchaseAmount purchaseAmount = new PurchaseAmount("1000");

            assertThat(purchaseAmount.getTicketCount()).isEqualTo(1);
            assertThat(purchaseAmount.getAmount()).isEqualTo(1000);
        }

        @DisplayName("큰 금액으로도 정상적으로 로또를 구매할 수 있다")
        @Test
        void 큰_금액으로도_정상적으로_로또를_구매할_수_있다() {
            PurchaseAmount purchaseAmount = new PurchaseAmount("100000");

            assertThat(purchaseAmount.getTicketCount()).isEqualTo(100);
            assertThat(purchaseAmount.getAmount()).isEqualTo(100000);
        }
    }

    @Nested
    @DisplayName("예외 발생 테스트")
    class 예외_발생_테스트 {

        @Test
        @DisplayName("빈 문자열 입력 시 예외가 발생한다")
        void 빈_문자열_입력_시_예외가_발생한다() {
            assertThatThrownBy(() -> new PurchaseAmount(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.PURCHASE_NULL_OR_EMPTY.getMessage());
        }

        @DisplayName("숫자가 아닌 문자열을 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"abc", "12.34", "1000원"})
        void 숫자가_아닌_문자열을_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new PurchaseAmount(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.PURCHASE_NOT_NUMBER.getMessage());
        }

        @DisplayName("int 범위를 벗어난 숫자를 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"2147483648", "-2147483649", "9999999999999"})
        void int_범위를_벗어난_숫자를_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new PurchaseAmount(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.PURCHASE_OUT_OF_RANGE.getMessage());
        }

        @DisplayName("0 이하의 금액을 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"0", "-1000", "-100"})
        void 영_이하의_금액을_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new PurchaseAmount(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.PURCHASE_NOT_POSITIVE.getMessage());
        }

        @DisplayName("1000원 단위가 아닌 금액을 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"999", "1500", "8001", "10999"})
        void 천원_단위가_아닌_금액을_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new PurchaseAmount(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.PURCHASE_NOT_UNIT.getMessage());
        }
    }
}
