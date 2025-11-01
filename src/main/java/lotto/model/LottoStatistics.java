package lotto.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoStatistics {

    private static final int PROFIT_RATE_SCALE = 1;
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private final Map<LottoRank, Integer> rankCounts;

    public LottoStatistics() {
        this.rankCounts = initializeRankCounts();
    }

    private Map<LottoRank, Integer> initializeRankCounts() {
        Map<LottoRank, Integer> counts = new EnumMap<>(LottoRank.class);
        for (LottoRank rank : LottoRank.values()) {
            counts.put(rank, 0);
        }

        return counts;
    }

    public void recordAll(List<LottoRank> ranks) {
        for (LottoRank rank : ranks) {
            record(rank);
        }
    }

    public void record(LottoRank rank) {
        int currentCount = rankCounts.get(rank);
        rankCounts.put(rank, currentCount + 1);
    }

    public int getCount(LottoRank rank) {
        return rankCounts.get(rank);
    }

    public double calculateProfitRate(int purchaseAmount) {
        long totalPrize = calculateTotalPrize();

        return calculateRate(totalPrize, purchaseAmount);
    }

    public long calculateTotalPrize() {
        return rankCounts.entrySet().stream()
                .mapToLong(entry -> calculatePrize(entry.getKey(), entry.getValue()))
                .sum();
    }

    private long calculatePrize(LottoRank rank, int count) {
        return (long) rank.getPrizeMoney() * count;
    }

    private double calculateRate(long totalPrize, int purchaseAmount) {
        BigDecimal prize = BigDecimal.valueOf(totalPrize);
        BigDecimal amount = BigDecimal.valueOf(purchaseAmount);

        return prize.multiply(ONE_HUNDRED)
                .divide(amount, PROFIT_RATE_SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
