package lotto.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import lotto.model.LottoRank;
import lotto.model.LottoStatistics;
import lotto.model.ticket.Lotto;
import lotto.model.ticket.LottoTickets;

public class OutputView {

    private static final String PURCHASE_MESSAGE_FORMAT = "%d개를 구매했습니다.";
    private static final String LOTTO_FORMAT = "[%s]";
    private static final String NUMBER_DELIMITER = ", ";
    private static final String STATISTICS_HEADER = "당첨 통계";
    private static final String STATISTICS_SEPARATOR = "---";
    private static final String PROFIT_RATE_FORMAT = "총 수익률은 %.1f%%입니다.";
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###");

    public void printPurchaseCount(int count) {
        System.out.println();
        System.out.println(String.format(PURCHASE_MESSAGE_FORMAT, count));
    }

    public void printLottoTickets(LottoTickets tickets) {
        for (Lotto lotto : tickets.getTickets()) {
            List<Integer> sortedNumbers = getSortedNumbers(lotto);
            String numbersText = formatNumbers(sortedNumbers);

            System.out.println(String.format(LOTTO_FORMAT, numbersText));
        }
    }

    private List<Integer> getSortedNumbers(Lotto lotto) {
        return lotto.getNumbers().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    private String formatNumbers(List<Integer> numbers) {
        return numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(NUMBER_DELIMITER));
    }

    public void printStatistics(LottoStatistics statistics) {
        System.out.println();
        System.out.println(STATISTICS_HEADER);
        System.out.println(STATISTICS_SEPARATOR);
        printRankStatistics(statistics);
    }

    private void printRankStatistics(LottoStatistics statistics) {
        List.of(LottoRank.FIFTH, LottoRank.FOURTH, LottoRank.THIRD, LottoRank.SECOND, LottoRank.FIRST)
                .forEach(rank -> printRank(rank, statistics));
    }

    private void printRank(LottoRank rank, LottoStatistics statistics) {
        String matchInfo = createMatchInfo(rank);
        String prizeInfo = formatMoney(rank.getPrizeMoney());
        int count = statistics.getCount(rank);

        System.out.println(matchInfo + " (" + prizeInfo + "원) - " + count + "개");
    }

    private String createMatchInfo(LottoRank rank) {
        if (rank.requireBonus()) {
            return rank.getMatchCount() + "개 일치, 보너스 볼 일치";
        }

        return rank.getMatchCount() + "개 일치";
    }

    private String formatMoney(int money) {
        return MONEY_FORMAT.format(money);
    }

    public void printProfitRate(double profitRate) {
        System.out.println(String.format(PROFIT_RATE_FORMAT, profitRate));
    }

    // 사용자가 잘못 입력하는 경우 에러 메시지를 출력하는 메서드
    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
