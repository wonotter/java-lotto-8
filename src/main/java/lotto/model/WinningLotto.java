package lotto.model;

public class WinningLotto {

    private final WinningNumbers winningNumbers;
    private final BonusNumber bonusNumber;

    public WinningLotto(WinningNumbers winningNumbers, BonusNumber bonusNumber) {
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    public LottoRank match(Lotto lotto) {
        int matchCount = calculateMatchCount(lotto);
        boolean matchBonus = checkBonusMatch(lotto);

        return LottoRank.valueOf(matchCount, matchBonus);
    }

    private int calculateMatchCount(Lotto lotto) {
        return winningNumbers.countMatches(lotto.getNumbers());
    }

    private boolean checkBonusMatch(Lotto lotto) {
        return lotto.contains(bonusNumber.getNumber());
    }

    public WinningNumbers getWinningNumbers() {
        return winningNumbers;
    }

    public BonusNumber getBonusNumber() {
        return bonusNumber;
    }
}
