package lotto.controller;

import java.util.ArrayList;
import java.util.List;
import lotto.model.LottoRank;
import lotto.model.LottoStatistics;
import lotto.model.generator.LottoGenerator;
import lotto.model.ticket.Lotto;
import lotto.model.ticket.LottoTickets;
import lotto.model.ticket.PurchaseAmount;
import lotto.model.winning.BonusNumber;
import lotto.model.winning.WinningLotto;
import lotto.model.winning.WinningNumbers;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoGenerator lottoGenerator;

    public LottoController(InputView inputView, OutputView outputView, LottoGenerator lottoGenerator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoGenerator = lottoGenerator;
    }

    public void run() {
        PurchaseAmount purchaseAmount = readPurchaseAmount();
        LottoTickets tickets = generateLottoTickets(purchaseAmount);
        printTickets(tickets);

        WinningLotto winningLotto = createWinningLotto();
        LottoStatistics statistics = calculateStatistics(tickets, winningLotto);

        printResults(statistics, purchaseAmount);
    }

    private PurchaseAmount readPurchaseAmount() {
        while (true) {
            try {
                String input = inputView.readPurchaseAmount();
                return new PurchaseAmount(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private LottoTickets generateLottoTickets(PurchaseAmount purchaseAmount) {
        int count = purchaseAmount.getTicketCount();
        List<Lotto> tickets = lottoGenerator.generate(count);

        return new LottoTickets(tickets);
    }

    private void printTickets(LottoTickets tickets) {
        outputView.printPurchaseCount(tickets.size());
        outputView.printLottoTickets(tickets);
    }

    private WinningLotto createWinningLotto() {
        WinningNumbers winningNumbers = readWinningNumbers();
        BonusNumber bonusNumber = readBonusNumber(winningNumbers);

        return new WinningLotto(winningNumbers, bonusNumber);
    }

    private WinningNumbers readWinningNumbers() {
        while (true) {
            try {
                String input = inputView.readWinningNumbers();
                return new WinningNumbers(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private BonusNumber readBonusNumber(WinningNumbers winningNumbers) {
        while (true) {
            try {
                String input = inputView.readBonusNumber();
                return new BonusNumber(input, winningNumbers);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private LottoStatistics calculateStatistics(LottoTickets tickets, WinningLotto winningLotto) {
        List<LottoRank> ranks = matchTickets(tickets, winningLotto);
        LottoStatistics statistics = new LottoStatistics();
        statistics.recordAll(ranks);

        return statistics;
    }

    private List<LottoRank> matchTickets(LottoTickets tickets, WinningLotto winningLotto) {
        List<Lotto> lottoList = tickets.getTickets();
        List<LottoRank> ranks = new ArrayList<>();

        for (Lotto lotto : lottoList) {
            LottoRank rank = winningLotto.match(lotto);
            ranks.add(rank);
        }

        return ranks;
    }

    private void printResults(LottoStatistics statistics, PurchaseAmount purchaseAmount) {
        outputView.printStatistics(statistics);

        double profitRate = statistics.calculateProfitRate(purchaseAmount.getAmount());
        outputView.printProfitRate(profitRate);
    }
}
