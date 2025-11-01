package lotto.model.ticket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LottoTicketsTest {

    @Nested
    @DisplayName("생성 테스트")
    class 생성_테스트 {

        @Test
        @DisplayName("로또 티켓들을 정상적으로 생성할 수 있다")
        void 정상_생성_테스트() {
            // given
            List<Lotto> tickets = List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                    new Lotto(List.of(7, 8, 9, 10, 11, 12))
            );

            // when
            LottoTickets lottoTickets = new LottoTickets(tickets);

            // then
            assertThat(lottoTickets).isNotNull();
        }

        @Test
        @DisplayName("빈 리스트로 로또 티켓들을 생성할 수 있다")
        void 빈_리스트_생성_테스트() {
            // given
            List<Lotto> emptyTickets = List.of();

            // when
            LottoTickets lottoTickets = new LottoTickets(emptyTickets);

            // then
            assertThat(lottoTickets).isNotNull();
            assertThat(lottoTickets.size()).isZero();
        }

        @Test
        @DisplayName("단일 티켓으로 로또 티켓들을 생성할 수 있다")
        void 단일_티켓_생성_테스트() {
            // given
            List<Lotto> singleTicket = List.of(new Lotto(List.of(1, 2, 3, 4, 5, 6)));

            // when
            LottoTickets lottoTickets = new LottoTickets(singleTicket);

            // then
            assertThat(lottoTickets).isNotNull();
            assertThat(lottoTickets.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("여러 티켓으로 로또 티켓들을 생성할 수 있다")
        void 여러_티켓_생성_테스트() {
            // given
            List<Lotto> tickets = List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                    new Lotto(List.of(7, 8, 9, 10, 11, 12)),
                    new Lotto(List.of(13, 14, 15, 16, 17, 18))
            );

            // when
            LottoTickets lottoTickets = new LottoTickets(tickets);

            // then
            assertThat(lottoTickets).isNotNull();
            assertThat(lottoTickets.size()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("getTickets 메서드 테스트")
    class GetTickets_메서드_테스트 {

        @Test
        @DisplayName("getTickets는 저장된 티켓 리스트를 반환한다")
        void 티켓_리스트_반환_테스트() {
            // given
            Lotto lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            Lotto lotto2 = new Lotto(List.of(7, 8, 9, 10, 11, 12));
            List<Lotto> tickets = List.of(lotto1, lotto2);
            LottoTickets lottoTickets = new LottoTickets(tickets);

            // when
            List<Lotto> returnedTickets = lottoTickets.getTickets();

            // then
            assertThat(returnedTickets).hasSize(2);
            assertThat(returnedTickets).containsExactly(lotto1, lotto2);
        }

        @Test
        @DisplayName("getTickets는 빈 리스트를 반환할 수 있다")
        void 빈_리스트_반환_테스트() {
            // given
            LottoTickets lottoTickets = new LottoTickets(List.of());

            // when
            List<Lotto> returnedTickets = lottoTickets.getTickets();

            // then
            assertThat(returnedTickets).isEmpty();
        }

        @Test
        @DisplayName("getTickets를 여러 번 호출해도 같은 내용의 리스트를 반환한다")
        void 여러번_호출_일관성_테스트() {
            // given
            Lotto lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            Lotto lotto2 = new Lotto(List.of(7, 8, 9, 10, 11, 12));
            List<Lotto> tickets = List.of(lotto1, lotto2);
            LottoTickets lottoTickets = new LottoTickets(tickets);

            // when
            List<Lotto> firstCall = lottoTickets.getTickets();
            List<Lotto> secondCall = lottoTickets.getTickets();

            // then
            assertThat(firstCall).containsExactlyElementsOf(secondCall);
            assertThat(firstCall).containsExactly(lotto1, lotto2);
            assertThat(secondCall).containsExactly(lotto1, lotto2);
        }

        @Test
        @DisplayName("getTickets는 불변 리스트를 반환한다 - add 연산이 불가능하다")
        void 불변_리스트_반환_add_테스트() {
            // given
            List<Lotto> tickets = List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 6))
            );
            LottoTickets lottoTickets = new LottoTickets(tickets);

            // when
            List<Lotto> returnedTickets = lottoTickets.getTickets();

            // then
            assertThatThrownBy(() -> returnedTickets.add(new Lotto(List.of(7, 8, 9, 10, 11, 12))))
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("getTickets는 불변 리스트를 반환한다 - remove 연산이 불가능하다")
        void 불변_리스트_반환_remove_테스트() {
            // given
            List<Lotto> tickets = List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                    new Lotto(List.of(7, 8, 9, 10, 11, 12))
            );
            LottoTickets lottoTickets = new LottoTickets(tickets);

            // when
            List<Lotto> returnedTickets = lottoTickets.getTickets();

            // then
            assertThatThrownBy(() -> returnedTickets.remove(0))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
