package lotto.model.ticket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("생성 테스트")
    class 생성_테스트 {

        @Test
        @DisplayName("정상적인 로또 번호로 생성할 수 있다")
        void 정상_생성_테스트() {
            // given & when
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

            // then
            assertThat(lotto).isNotNull();
            assertThat(lotto.getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
        }

        @Test
        @DisplayName("로또 번호가 5개 미만이면 예외가 발생한다")
        void 번호_개수_부족_예외_테스트() {
            assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("로또 번호는 6개여야 합니다");
        }

        @Test
        @DisplayName("로또 번호가 7개 이상이면 예외가 발생한다")
        void 번호_개수_초과_예외_테스트() {
            assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("로또 번호는 6개여야 합니다");
        }

        @Test
        @DisplayName("로또 번호가 1보다 작으면 예외가 발생한다")
        void 최소값_범위_미만_예외_테스트() {
            assertThatThrownBy(() -> new Lotto(List.of(0, 1, 2, 3, 4, 5)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("로또 번호는 1부터 45 사이의 숫저여야 합니다.");
        }

        @Test
        @DisplayName("로또 번호가 45보다 크면 예외가 발생한다")
        void 최대값_범위_초과_예외_테스트() {
            assertThatThrownBy(() -> new Lotto(List.of(41, 42, 43, 44, 45, 46)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("로또 번호는 1부터 45 사이의 숫저여야 합니다.");
        }

        @Test
        @DisplayName("로또 번호에 음수가 포함되면 예외가 발생한다")
        void 음수_예외_테스트() {
            assertThatThrownBy(() -> new Lotto(List.of(-1, 1, 2, 3, 4, 5)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("로또 번호는 1부터 45 사이의 숫저여야 합니다.");
        }

        @Test
        @DisplayName("로또 번호가 중복되면 예외가 발생한다")
        void 일부_중복_예외_테스트() {
            assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 3, 4, 5)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("로또 번호는 중복될 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("equals와 hashCode 테스트")
    class Equals_HashCode_테스트 {

        @Test
        @DisplayName("같은 번호를 가진 로또는 동등하다")
        void 동등성_테스트() {
            // given
            Lotto lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            Lotto lotto2 = new Lotto(List.of(1, 2, 3, 4, 5, 6));

            // when & then
            assertThat(lotto1).isEqualTo(lotto2);
        }

        @Test
        @DisplayName("다른 번호를 가진 로또는 동등하지 않다")
        void 비동등성_테스트() {
            // given
            Lotto lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            Lotto lotto2 = new Lotto(List.of(7, 8, 9, 10, 11, 12));

            // when & then
            assertThat(lotto1).isNotEqualTo(lotto2);
        }

        @Test
        @DisplayName("같은 번호를 가진 로또는 같은 hashCode를 가진다")
        void hashCode_동일_테스트() {
            // given
            Lotto lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            Lotto lotto2 = new Lotto(List.of(1, 2, 3, 4, 5, 6));

            // when & then
            assertThat(lotto1.hashCode()).isEqualTo(lotto2.hashCode());
        }
    }
}
