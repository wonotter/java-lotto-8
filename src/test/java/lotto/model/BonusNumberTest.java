package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lotto.exception.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BonusNumberTest {

    private WinningNumbers winningNumbers;

    @BeforeEach
    void setUp() {
        winningNumbers = new WinningNumbers("1,2,3,4,5,6");
    }

    @Nested
    @DisplayName("정상 동작 테스트")
    class 정상_동작_테스트 {

        @DisplayName("올바른 보너스 번호로 객체를 생성한다")
        @Test
        void 올바른_보너스_번호로_객체를_생성한다() {
            BonusNumber bonusNumber = new BonusNumber("7", winningNumbers);

            assertThat(bonusNumber.getNumber()).isEqualTo(7);
        }

        @DisplayName("공백이 포함된 보너스 번호도 정상적으로 파싱한다")
        @Test
        void 공백이_포함된_보너스_번호도_정상적으로_파싱한다() {
            BonusNumber bonusNumber = new BonusNumber("  10  ", winningNumbers);

            assertThat(bonusNumber.getNumber()).isEqualTo(10);
        }

        @DisplayName("당첨 번호에 포함되지 않은 번호를 보너스 번호로 사용할 수 있다")
        @ParameterizedTest
        @ValueSource(strings = {"7", "8", "10", "20", "45"})
        void 당첨_번호에_포함되지_않은_번호를_보너스_번호로_사용할_수_있다(String input) {
            BonusNumber bonusNumber = new BonusNumber(input, winningNumbers);

            assertThat(bonusNumber.getNumber()).isEqualTo(Integer.parseInt(input));
        }
    }

    @Nested
    @DisplayName("예외 발생 테스트")
    class 예외_발생_테스트 {

        @DisplayName("null을 입력하면 예외가 발생한다")
        @Test
        void null을_입력하면_예외가_발생한다() {
            assertThatThrownBy(() -> new BonusNumber(null, winningNumbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.BONUS_NUMBER_NULL_OR_EMPTY.getMessage());
        }

        @DisplayName("빈 문자열을 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "  "})
        void 빈_문자열을_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new BonusNumber(input, winningNumbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.BONUS_NUMBER_NULL_OR_EMPTY.getMessage());
        }

        @DisplayName("숫자가 아닌 문자열을 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"a", "abc", "7번", "일곱", "7.5", "10.0"})
        void 숫자가_아닌_문자열을_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new BonusNumber(input, winningNumbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.BONUS_NUMBER_NOT_NUMBER.getMessage());
        }

        @DisplayName("1보다 작은 번호를 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"0", "-1", "-10", "-100"})
        void 일_보다_작은_번호를_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new BonusNumber(input, winningNumbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.BONUS_NUMBER_INVALID_RANGE.getMessage());
        }

        @DisplayName("45보다 큰 번호를 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"46", "50", "100", "1000"})
        void 사십오_보다_큰_번호를_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new BonusNumber(input, winningNumbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.BONUS_NUMBER_INVALID_RANGE.getMessage());
        }

        @DisplayName("당첨 번호와 중복된 번호를 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"1", "2", "3", "4", "5", "6"})
        void 당첨_번호와_중복된_번호를_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new BonusNumber(input, winningNumbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.BONUS_NUMBER_DUPLICATE.getMessage());
        }

        @DisplayName("다른 당첨 번호 세트에서도 중복을 검증한다")
        @Test
        void 다른_당첨_번호_세트에서도_중복을_검증한다() {
            WinningNumbers otherWinningNumbers = new WinningNumbers("10,20,30,40,41,42");

            assertThatThrownBy(() -> new BonusNumber("10", otherWinningNumbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.BONUS_NUMBER_DUPLICATE.getMessage());
        }
    }
}