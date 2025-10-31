package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class LottoRankTest {

    @Nested
    @DisplayName("당첨 등수 판별 테스트")
    class 당첨_등수_판별_테스트 {

        @DisplayName("6개 일치하면 1등이다")
        @Test
        void 여섯개_일치하면_1등이다() {
            LottoRank rank = LottoRank.valueOf(6, false);

            assertThat(rank).isEqualTo(LottoRank.FIRST);
        }

        @DisplayName("5개 일치하고 보너스가 일치하면 2등이다")
        @Test
        void 다섯개_일치하면_2등이다() {
            LottoRank rank = LottoRank.valueOf(5, true);

            assertThat(rank).isEqualTo(LottoRank.SECOND);
        }

        @DisplayName("5개 일치하고 보너스가 불일치하면 3등이다")
        @Test
        void 다섯개_일치하고_보너스가_불일치하면_3등이다() {
            LottoRank rank = LottoRank.valueOf(5, false);

            assertThat(rank).isEqualTo(LottoRank.THIRD);
        }

        @DisplayName("4개 일치하면 4등이다")
        @Test
        void 네개_일치하면_4등이다() {
            LottoRank rank = LottoRank.valueOf(4, false);

            assertThat(rank).isEqualTo(LottoRank.FOURTH);
        }

        @DisplayName("4개 일치하고 보너스가 일치해도 4등이다")
        @Test
        void 네개_일치하고_보너스가_일치해도_4등이다() {
            LottoRank rank = LottoRank.valueOf(4, true);

            assertThat(rank).isEqualTo(LottoRank.FOURTH);
        }

        @DisplayName("3개 일치하면 5등이다")
        @Test
        void 세개_일치하면_5등이다() {
            LottoRank rank = LottoRank.valueOf(3, false);

            assertThat(rank).isEqualTo(LottoRank.FIFTH);
        }

        @DisplayName("2개 이하로 일치하면 꽝이다")
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2})
        void 두개_이하로_일치하면_꽝이다(int matchCount) {
            LottoRank rank = LottoRank.valueOf(matchCount, false);

            assertThat(rank).isEqualTo(LottoRank.NONE);
        }

        @DisplayName("보너스 일치 여부와 상관없이 꽝 판별이 정확하다")
        @ParameterizedTest
        @CsvSource({
                "2, false",
                "2, true",
                "1, false",
                "1, true",
                "0, false",
                "0, true"
        })
        void 보너스_일치_여부와_상관없이_꽝_판별이_정확하다(int matchCount, boolean matchBonus) {
            LottoRank rank = LottoRank.valueOf(matchCount, matchBonus);

            assertThat(rank).isEqualTo(LottoRank.NONE);
        }
    }

    @Nested
    @DisplayName("당첨 여부 확인 테스트")
    class 당첨_여부_확인_테스트 {

        @DisplayName("1등부터 5등까지는 당첨이다")
        @Test
        void 일등부터_오등까지는_당첨이다() {
            assertThat(LottoRank.FIRST.isWinning()).isTrue();
            assertThat(LottoRank.SECOND.isWinning()).isTrue();
            assertThat(LottoRank.THIRD.isWinning()).isTrue();
            assertThat(LottoRank.FOURTH.isWinning()).isTrue();
            assertThat(LottoRank.FIFTH.isWinning()).isTrue();
        }

        @DisplayName("NONE은 꽝이다")
        @Test
        void NONE은_꽝이다() {
            assertThat(LottoRank.NONE.isWinning()).isFalse();
        }
    }
}
