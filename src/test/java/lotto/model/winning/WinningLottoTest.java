package lotto.model.winning;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lotto.model.LottoRank;
import lotto.model.ticket.Lotto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WinningLottoTest {

    private WinningNumbers winningNumbers;
    private BonusNumber bonusNumber;
    private WinningLotto winningLotto;

    @BeforeEach
    void setUp() {
        // 당첨 번호: 1, 2, 3, 4, 5, 6
        // 보너스 번호: 7
        winningNumbers = new WinningNumbers("1,2,3,4,5,6");
        bonusNumber = new BonusNumber("7", winningNumbers);
        winningLotto = new WinningLotto(winningNumbers, bonusNumber);
    }

    @Test
    @DisplayName("당첨 번호와 보너스 번호로 WinningLotto를 생성할 수 있다")
    void 정상_생성_테스트() {
        // given & when
        WinningNumbers numbers = new WinningNumbers("10,11,12,13,14,15");
        BonusNumber bonus = new BonusNumber("20", numbers);
        WinningLotto lotto = new WinningLotto(numbers, bonus);

        // then
        assertThat(lotto).isNotNull();
        assertThat(lotto.getWinningNumbers()).isEqualTo(numbers);
        assertThat(lotto.getBonusNumber()).isEqualTo(bonus);
    }

    @Test
    @DisplayName("6개 모두 일치하면 1등이다")
    void 일등_테스트() {
        // given - 당첨 번호: 1,2,3,4,5,6
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        // when
        LottoRank rank = winningLotto.match(lotto);

        // then
        assertThat(rank).isEqualTo(LottoRank.FIRST);
    }

    @Test
    @DisplayName("5개 일치하고 보너스가 일치하면 2등이다")
    void 이등_테스트() {
        // given - 당첨: 1,2,3,4,5,6 | 보너스: 7
        // 로또: 1,2,3,4,5,7 (5개 + 보너스)
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 7));

        // when
        LottoRank rank = winningLotto.match(lotto);

        // then
        assertThat(rank).isEqualTo(LottoRank.SECOND);
    }

    @Test
    @DisplayName("5개 일치하고 보너스가 일치하지 않으면 3등이다")
    void 삼등_테스트() {
        // given - 당첨: 1,2,3,4,5,6 | 보너스: 7
        // 로또: 1,2,3,4,5,8 (5개만 일치, 보너스 X)
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 8));

        // when
        LottoRank rank = winningLotto.match(lotto);

        // then
        assertThat(rank).isEqualTo(LottoRank.THIRD);
    }

    @Test
    @DisplayName("4개 일치하면 4등이다")
    void 사등_테스트() {
        // given - 1,2,3,4,8,9 (4개 일치)
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 8, 9));

        // when
        LottoRank rank = winningLotto.match(lotto);

        // then
        assertThat(rank).isEqualTo(LottoRank.FOURTH);
    }

    @Test
    @DisplayName("3개 일치하면 5등이다")
    void 오등_테스트() {
        // given - 1,2,3,10,11,12 (3개 일치)
        Lotto lotto = new Lotto(List.of(1, 2, 3, 10, 11, 12));

        // when
        LottoRank rank = winningLotto.match(lotto);

        // then
        assertThat(rank).isEqualTo(LottoRank.FIFTH);
    }

    @Test
    @DisplayName("2개 일치, 보너스 포함해도 낙첨이다")
    void 낙첨_2개_보너스_테스트() {
        // given - 1,2,7,20,21,22 (2개 + 보너스)
        Lotto lotto = new Lotto(List.of(1, 2, 7, 20, 21, 22));

        // when
        LottoRank rank = winningLotto.match(lotto);

        // then
        assertThat(rank).isEqualTo(LottoRank.NONE);
    }

    @Nested
    @DisplayName("통합 시나리오 테스트")
    class 통합_시나리오_테스트 {

        @Test
        @DisplayName("실제 로또 당첨 시나리오 - 여러 티켓 검증")
        void 실제_시나리오_테스트() {
            // given - 당첨번호: 1,2,3,4,5,6 | 보너스: 7
            Lotto ticket1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));   // 1등
            Lotto ticket2 = new Lotto(List.of(1, 2, 3, 4, 5, 7));   // 2등
            Lotto ticket3 = new Lotto(List.of(1, 2, 3, 4, 5, 8));   // 3등
            Lotto ticket4 = new Lotto(List.of(1, 2, 3, 4, 8, 9));   // 4등
            Lotto ticket5 = new Lotto(List.of(1, 2, 3, 8, 9, 10));  // 5등
            Lotto ticket6 = new Lotto(List.of(1, 2, 8, 9, 10, 11)); // 낙첨

            // when & then
            assertThat(winningLotto.match(ticket1)).isEqualTo(LottoRank.FIRST);
            assertThat(winningLotto.match(ticket2)).isEqualTo(LottoRank.SECOND);
            assertThat(winningLotto.match(ticket3)).isEqualTo(LottoRank.THIRD);
            assertThat(winningLotto.match(ticket4)).isEqualTo(LottoRank.FOURTH);
            assertThat(winningLotto.match(ticket5)).isEqualTo(LottoRank.FIFTH);
            assertThat(winningLotto.match(ticket6)).isEqualTo(LottoRank.NONE);
        }

        @Test
        @DisplayName("2등과 3등의 차이를 정확히 구분한다")
        void 이등_삼등_구분_테스트() {
            // given - 당첨: 10,20,30,40,41,42 | 보너스: 43
            WinningNumbers numbers = new WinningNumbers("10,20,30,40,41,42");
            BonusNumber bonus = new BonusNumber("43", numbers);
            WinningLotto lotto = new WinningLotto(numbers, bonus);

            Lotto ticket2nd = new Lotto(List.of(10, 20, 30, 40, 41, 43));  // 5개 + 보너스
            Lotto ticket3rd = new Lotto(List.of(10, 20, 30, 40, 41, 45));  // 5개만

            // when & then
            assertThat(lotto.match(ticket2nd)).isEqualTo(LottoRank.SECOND);
            assertThat(lotto.match(ticket3rd)).isEqualTo(LottoRank.THIRD);
        }
    }
}

