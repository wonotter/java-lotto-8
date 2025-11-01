package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LottoStatisticsTest {

    private LottoStatistics statistics;

    @BeforeEach
    void setUp() {
        statistics = new LottoStatistics();
    }

    @Nested
    @DisplayName("생성 테스트")
    class 생성_테스트 {

        @Test
        @DisplayName("생성 시 모든 등수와 초기 카운트는 0이다")
        void 초기화_테스트() {
            assertThat(statistics.getCount(LottoRank.FIRST)).isEqualTo(0);
            assertThat(statistics.getCount(LottoRank.SECOND)).isEqualTo(0);
            assertThat(statistics.getCount(LottoRank.THIRD)).isEqualTo(0);
            assertThat(statistics.getCount(LottoRank.FOURTH)).isEqualTo(0);
            assertThat(statistics.getCount(LottoRank.FIFTH)).isEqualTo(0);
        }

        @Test
        @DisplayName("생성 시 총 상금은 0이다")
        void 초기_총상금_테스트() {
            assertThat(statistics.calculateTotalPrize()).isEqualTo(0L);
        }
    }

    @Nested
    @DisplayName("record 메서드 테스트")
    class Record_메서드_테스트 {

        @Test
        @DisplayName("특정 등수를 기록하면 해당 등수의 카운트가 1 증가한다")
        void 단일_기록_테스트() {
            // when
            statistics.record(LottoRank.FIRST);

            // then
            assertThat(statistics.getCount(LottoRank.FIRST)).isEqualTo(1);
            assertThat(statistics.getCount(LottoRank.SECOND)).isEqualTo(0);
        }

        @Test
        @DisplayName("같은 등수를 여러 번 기록하면 카운트가 누적된다")
        void 중복_기록_테스트() {
            // when
            statistics.record(LottoRank.THIRD);
            statistics.record(LottoRank.THIRD);
            statistics.record(LottoRank.THIRD);

            // then
            assertThat(statistics.getCount(LottoRank.THIRD)).isEqualTo(3);
        }

        @Test
        @DisplayName("다양한 등수를 기록하면 각각의 카운트가 증가한다")
        void 다양한_등수_기록_테스트() {
            // when
            statistics.record(LottoRank.FIRST);
            statistics.record(LottoRank.SECOND);
            statistics.record(LottoRank.THIRD);
            statistics.record(LottoRank.THIRD);
            statistics.record(LottoRank.FIFTH);

            // then
            assertThat(statistics.getCount(LottoRank.FIRST)).isEqualTo(1);
            assertThat(statistics.getCount(LottoRank.SECOND)).isEqualTo(1);
            assertThat(statistics.getCount(LottoRank.THIRD)).isEqualTo(2);
            assertThat(statistics.getCount(LottoRank.FOURTH)).isEqualTo(0);
            assertThat(statistics.getCount(LottoRank.FIFTH)).isEqualTo(1);
        }

        @Test
        @DisplayName("낙첨(NONE)도 정상적으로 기록된다")
        void 낙첨_기록_테스트() {
            // when
            statistics.record(LottoRank.NONE);
            statistics.record(LottoRank.NONE);

            // then
            assertThat(statistics.getCount(LottoRank.NONE)).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("recordAll 메서드 테스트")
    class RecordAll_메서드_테스트 {

        @Test
        @DisplayName("리스트의 모든 등수를 한 번에 기록한다")
        void 일괄_기록_테스트() {
            // given
            List<LottoRank> ranks = List.of(
                    LottoRank.FIRST,
                    LottoRank.THIRD,
                    LottoRank.THIRD,
                    LottoRank.FIFTH,
                    LottoRank.NONE
            );

            // when
            statistics.recordAll(ranks);

            // then
            assertThat(statistics.getCount(LottoRank.FIRST)).isEqualTo(1);
            assertThat(statistics.getCount(LottoRank.SECOND)).isEqualTo(0);
            assertThat(statistics.getCount(LottoRank.THIRD)).isEqualTo(2);
            assertThat(statistics.getCount(LottoRank.FOURTH)).isEqualTo(0);
            assertThat(statistics.getCount(LottoRank.FIFTH)).isEqualTo(1);
            assertThat(statistics.getCount(LottoRank.NONE)).isEqualTo(1);
        }

        @Test
        @DisplayName("빈 리스트를 전달하면 아무 변화가 없다")
        void 빈_리스트_테스트() {
            // given
            List<LottoRank> emptyRanks = List.of();

            // when
            statistics.recordAll(emptyRanks);

            // then
            assertThat(statistics.getCount(LottoRank.FIRST)).isEqualTo(0);
            assertThat(statistics.getCount(LottoRank.SECOND)).isEqualTo(0);
            assertThat(statistics.getCount(LottoRank.THIRD)).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("calculateTotalPrize 메서드 테스트")
    class CalculateTotalPrize_메서드_테스트 {

        @Test
        @DisplayName("1등 1개의 총 상금은 20억이다")
        void 일등_상금_테스트() {
            // given
            statistics.record(LottoRank.FIRST);

            // when
            long totalPrize = statistics.calculateTotalPrize();

            // then
            assertThat(totalPrize).isEqualTo(2_000_000_000L);
        }

        @Test
        @DisplayName("2등 1개의 총 상금은 3천만원이다")
        void 이등_상금_테스트() {
            // given
            statistics.record(LottoRank.SECOND);

            // when
            long totalPrize = statistics.calculateTotalPrize();

            // then
            assertThat(totalPrize).isEqualTo(30_000_000L);
        }

        @Test
        @DisplayName("3등 1개의 총 상금은 150만원이다")
        void 삼등_상금_테스트() {
            // given
            statistics.record(LottoRank.THIRD);

            // when
            long totalPrize = statistics.calculateTotalPrize();

            // then
            assertThat(totalPrize).isEqualTo(1_500_000L);
        }

        @Test
        @DisplayName("4등 1개의 총 상금은 5만원이다")
        void 사등_상금_테스트() {
            // given
            statistics.record(LottoRank.FOURTH);

            // when
            long totalPrize = statistics.calculateTotalPrize();

            // then
            assertThat(totalPrize).isEqualTo(50_000L);
        }

        @Test
        @DisplayName("5등 1개의 총 상금은 5천원이다")
        void 오등_상금_테스트() {
            // given
            statistics.record(LottoRank.FIFTH);

            // when
            long totalPrize = statistics.calculateTotalPrize();

            // then
            assertThat(totalPrize).isEqualTo(5_000L);
        }

        @Test
        @DisplayName("낙첨의 상금은 0원이다")
        void 낙첨_상금_테스트() {
            // given
            statistics.record(LottoRank.NONE);

            // when
            long totalPrize = statistics.calculateTotalPrize();

            // then
            assertThat(totalPrize).isEqualTo(0L);
        }

        @Test
        @DisplayName("여러 등수의 총 상금은 각 등수 상금의 합이다")
        void 복합_상금_계산_테스트() {
            // given
            statistics.record(LottoRank.THIRD);   // 1,500,000원
            statistics.record(LottoRank.FOURTH);  // 50,000원
            statistics.record(LottoRank.FIFTH);   // 5,000원
            statistics.record(LottoRank.FIFTH);   // 5,000원

            // when
            long totalPrize = statistics.calculateTotalPrize();

            // then
            // 1,500,000 + 50,000 + 5,000 + 5,000 = 1,560,000
            assertThat(totalPrize).isEqualTo(1_560_000L);
        }

        @Test
        @DisplayName("같은 등수가 여러 개면 상금이 곱해진다")
        void 같은_등수_복수_테스트() {
            // given
            statistics.record(LottoRank.FIFTH);
            statistics.record(LottoRank.FIFTH);
            statistics.record(LottoRank.FIFTH);

            // when
            long totalPrize = statistics.calculateTotalPrize();

            // then
            assertThat(totalPrize).isEqualTo(15_000L);  // 5,000 * 3
        }
    }

    @Nested
    @DisplayName("calculateProfitRate 메서드 테스트")
    class CalculateProfitRate_메서드_테스트 {

        @Test
        @DisplayName("수익률은 (총 상금 / 구매 금액 * 100)으로 계산된다")
        void 수익률_계산_테스트() {
            // given
            statistics.record(LottoRank.FIFTH);  // 5,000원
            int purchaseAmount = 10_000;

            // when
            double profitRate = statistics.calculateProfitRate(purchaseAmount);

            // then
            assertThat(profitRate).isEqualTo(50.0);  // (5,000 / 10,000) * 100 = 50%
        }

        @Test
        @DisplayName("당첨되지 않으면 수익률은 0이다")
        void 낙첨_수익률_테스트() {
            // given
            statistics.record(LottoRank.NONE);
            statistics.record(LottoRank.NONE);
            int purchaseAmount = 10_000;

            // when
            double profitRate = statistics.calculateProfitRate(purchaseAmount);

            // then
            assertThat(profitRate).isEqualTo(0.0);
        }

        @Test
        @DisplayName("수익률은 소수점 첫째 자리에서 반올림된다")
        void 수익률_반올림_테스트() {
            // given
            statistics.record(LottoRank.FIFTH);  // 5,000원
            int purchaseAmount = 14_000;

            // when
            double profitRate = statistics.calculateProfitRate(purchaseAmount);

            // then
            // (5,000 / 14,000) * 100 = 35.714285...
            // 소수점 첫째 자리에서 반올림 → 35.7
            assertThat(profitRate).isEqualTo(35.7);
        }
    }
}
