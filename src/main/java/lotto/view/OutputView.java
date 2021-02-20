package lotto.view;

import lotto.domain.LottoRank;
import lotto.domain.LottoResult;
import lotto.domain.LottoTicket;
import lotto.domain.LottoTickets;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class OutputView {
    private static final String PURCHASED_LOTTO_TICKET_COUNTS_MESSAGE = "%d개를 구매했습니다.\n";
    private static final String PREFIX = "[";
    private static final String DELIMITER = ", ";
    private static final String SUFFIX = "]";
    private static final String RESULT_HEADER_MESSAGE = "\n당첨 통계";
    private static final String HYPHENS = "---------";
    private static final String LOTTO_STATISTICS_MESSAGE = "%d개 일치 (%d원)- %d개\n";
    private static final String LOTTO_STATISTICS_BONUS_BALL_MESSAGE = "%d개 일치, 보너스 볼 일치(%d원)- %d개\n";
    private static final String YIELD_MESSAGE = "총 수익률은 %.2f입니다.\n";

    private OutputView() {
    }

    public static void printPurchasedLottoTicketCounts(int lottoTicketCounts) {
        System.out.printf(PURCHASED_LOTTO_TICKET_COUNTS_MESSAGE, lottoTicketCounts);
    }

    public static void printAllLottoTicketNumbers(LottoTickets lottoTickets) {
        lottoTickets.getLottoTickets()
                .forEach(OutputView::printEachLottoTicketNumbers);
        System.out.println();
    }

    private static void printEachLottoTicketNumbers(LottoTicket lottoTicket) {
        String numbers = lottoTicket.getLottoNumbers()
                .stream()
                .map(lottoNumber -> String.valueOf(lottoNumber.getNumber()))
                .collect(Collectors.joining(DELIMITER, PREFIX, SUFFIX));
        System.out.println(numbers);
    }

    public static void printLottoResult(LottoResult lottoResult, double yield) {
        System.out.println(RESULT_HEADER_MESSAGE);
        System.out.println(HYPHENS);
        Arrays.stream(LottoRank.values())
                .filter(lottoRank -> lottoRank != LottoRank.MISS)
                .sorted(Comparator.reverseOrder())
                .forEach(lottoRank -> printStatisticsByRank(lottoRank, lottoResult));
        System.out.printf(YIELD_MESSAGE, yield);
    }

    private static void printStatisticsByRank(LottoRank lottoRank, LottoResult lottoResult) {
        int matchCounts = lottoRank.getMatchCounts();
        int prizeMoney = lottoRank.getPrizeMoney();
        int ticketCountsByRank = (int) lottoResult.getTicketCountsByRank(lottoRank);
        if (lottoRank == LottoRank.SECOND_PRIZE) {
            System.out.printf(LOTTO_STATISTICS_BONUS_BALL_MESSAGE, matchCounts, prizeMoney, ticketCountsByRank);
            return;
        }
        System.out.printf(LOTTO_STATISTICS_MESSAGE, matchCounts, prizeMoney, ticketCountsByRank);
    }
}
