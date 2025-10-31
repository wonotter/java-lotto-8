package lotto.model.generator;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import lotto.model.ticket.Lotto;

public class DefaultLottoGenerator implements LottoGenerator {

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 45;
    private static final int COUNT = 6;

    @Override
    public List<Lotto> generate(int count) {
        List<Lotto> tickets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tickets.add(generateSingleLotto());
        }

        return tickets;
    }

    private Lotto generateSingleLotto() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(MIN_VALUE, MAX_VALUE, COUNT);

        return new Lotto(numbers);
    }
}
