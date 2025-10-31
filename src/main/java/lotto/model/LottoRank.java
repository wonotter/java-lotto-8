package lotto.model;

import java.util.Arrays;

public enum LottoRank {

    FIRST(6, false, 2_000_000_000),
    SECOND(5, true, 30_000_000),
    THIRD(5, false, 1_500_000),
    FOURTH(4, false, 50_000),
    FIFTH(3, false, 5_000),
    NONE(0, false, 0),
    ;

    private final int matchCount;
    private final boolean requireBonus;
    private final int prizeMoney;

    LottoRank(int matchCount, boolean requireBonus, int prizeMoney) {
        this.matchCount = matchCount;
        this.requireBonus = requireBonus;
        this.prizeMoney = prizeMoney;
    }

    public static LottoRank valueOf(int matchCount, boolean matchBonus) {
        if (matchCount == 5 && matchBonus) {
            return SECOND;
        }
        return findByMatchCount(matchCount);
    }

    private static LottoRank findByMatchCount(int matchCount) {
        return Arrays.stream(values())
                .filter(rank -> rank.matchCount == matchCount)
                .filter(rank -> !rank.requireBonus)
                .findFirst()
                .orElse(NONE);
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isWinning() {
        return this != NONE;
    }

    public boolean requireBonus() {
        return requireBonus;
    }
}
