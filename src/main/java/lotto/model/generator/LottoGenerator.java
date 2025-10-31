package lotto.model.generator;

import java.util.List;
import lotto.model.Lotto;

public interface LottoGenerator {
    
    List<Lotto> generate(int count);
}
