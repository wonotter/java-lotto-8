package lotto.model.ticket;

import java.util.Collections;
import java.util.List;

public class LottoTickets {

    private final List<Lotto> tickets;

    public LottoTickets(List<Lotto> tickets) {
        this.tickets = tickets;
    }

    public int size() {
        return tickets.size();
    }

    // Collections.unmodifiableList()로 불변 리스트를 반환
    // 외부에서 리스트 수정이 불가능 하도록 조치
    public List<Lotto> getTickets() {
        return Collections.unmodifiableList(tickets);
    }
}
