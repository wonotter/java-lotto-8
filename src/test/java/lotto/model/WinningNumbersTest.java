package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lotto.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WinningNumbersTest {

    @Nested
    @DisplayName("정상 동작 테스트")
    class 정상_동작_테스트 {

        @DisplayName("올바른 당첨 번호로 객체를 생성한다")
        @Test
        void 올바른_당첨_번호로_객체를_생성한다() {
            WinningNumbers winningNumbers = new WinningNumbers("1,2,3,4,5,6");

            assertThat(winningNumbers.getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
        }

        @DisplayName("공백이 포함된 당첨 번호도 정상적으로 파싱한다")
        @Test
        void 공백이_포함된_당첨_번호도_정상적으로_파싱한다() {
            WinningNumbers winningNumbers = new WinningNumbers("8, 21, 23, 41, 42, 43");

            assertThat(winningNumbers.getNumbers()).containsExactly(8, 21, 23, 41, 42, 43);
        }

        @DisplayName("당첨 번호에 특정 번호가 포함되어 있는지 확인할 수 있다")
        @Test
        void 당첨_번호에_특정_번호가_포함되어_있는지_확인할_수_있다() {
            WinningNumbers winningNumbers = new WinningNumbers("1,2,3,4,5,6");

            assertThat(winningNumbers.contains(1)).isTrue();
            assertThat(winningNumbers.contains(7)).isFalse();
        }

        @DisplayName("다른 번호 목록과 일치하는 개수를 계산할 수 있다")
        @Test
        void 다른_번호_목록과_일치하는_개수를_계산할_수_있다() {
            WinningNumbers winningNumbers = new WinningNumbers("1,2,3,4,5,6");
            List<Integer> otherNumbers = List.of(1, 2, 3, 7, 8, 9);

            assertThat(winningNumbers.countMatches(otherNumbers)).isEqualTo(3);
        }

        @DisplayName("최소 번호 1과 최대 번호 45를 포함할 수 있다")
        @Test
        void 최소_번호_1과_최대_번호_45를_포함할_수_있다() {
            WinningNumbers winningNumbers = new WinningNumbers("1,2,3,43,44,45");

            assertThat(winningNumbers.getNumbers()).contains(1, 45);
        }
    }

    @Nested
    @DisplayName("예외 발생 테스트")
    class 예외_발생_테스트 {

        @DisplayName("null을 입력하면 예외가 발생한다")
        @Test
        void null을_입력하면_예외가_발생한다() {
            assertThatThrownBy(() -> new WinningNumbers(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.WINNING_NUMBERS_NULL_OR_EMPTY.getMessage());
        }

        @DisplayName("빈 문자열을 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "  "})
        void 빈_문자열을_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new WinningNumbers(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.WINNING_NUMBERS_NULL_OR_EMPTY.getMessage());
        }

        @DisplayName("숫자가 아닌 문자열을 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"1,2,3,4,5,a", "1.2,3,4,5,6", "일,2,3,4,5,6", "1,2,3,4,5,6번"})
        void 숫자가_아닌_문자열을_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new WinningNumbers(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.WINNING_NUMBERS_NOT_NUMBER.getMessage());
        }

        @DisplayName("6개가 아닌 개수를 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"1,2,3,4,5", "1,2,3,4,5,6,7", "1", "1,2,3", "1,2,3,4,5,6,7,8,9,10"})
        void 여섯_개가_아닌_개수를_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new WinningNumbers(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.WINNING_NUMBERS_INVALID_COUNT.getMessage());
        }

        @DisplayName("1 보다 작은 번호를 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"0,1,2,3,4,5", "-1,2,3,4,5,6", "-10,1,2,3,4,5"})
        void 일_보다_작은_번호를_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new WinningNumbers(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.WINNING_NUMBERS_INVALID_RANGE.getMessage());
        }

        @DisplayName("45보다 큰 번호를 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"1,2,3,4,5,46", "40,41,42,43,44,50", "1,2,3,4,5,100"})
        void 사십오_보다_큰_번호를_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new WinningNumbers(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.WINNING_NUMBERS_INVALID_RANGE.getMessage());
        }

        @DisplayName("중복된 번호를 입력하면 예외가 발생한다")
        @ParameterizedTest
        @ValueSource(strings = {"1,2,3,4,5,5", "1,1,2,3,4,5", "10,10,10,10,10,10", "1,2,3,4,1,6"})
        void 중복된_번호를_입력하면_예외가_발생한다(String input) {
            assertThatThrownBy(() -> new WinningNumbers(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.WINNING_NUMBERS_DUPLICATE.getMessage());
        }
    }
}