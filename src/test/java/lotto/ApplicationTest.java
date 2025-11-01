package lotto;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import lotto.exception.ErrorMessage;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("1000j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Test
    void 구매_금액이_1000원_단위가_아닐_때_예외_발생() {
        assertSimpleTest(() -> {
            runException("8500");
            assertThat(output()).contains(ErrorMessage.PURCHASE_NOT_UNIT.getMessage());
        });
    }

    @Test
    void 당첨_번호가_6개가_아닐_때_예외_발생() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5");
            assertThat(output()).contains(ErrorMessage.WINNING_NUMBERS_INVALID_COUNT.getMessage());
        });
    }

    @Test
    void 당첨_번호에_중복이_있을_때_예외_발생() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,5");
            assertThat(output()).contains(ErrorMessage.WINNING_NUMBERS_DUPLICATE.getMessage());
        });
    }

    @Test
    void 보너스_번호_형식이_잘못되었을_때_예외_발생() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6", "a");
            assertThat(output()).contains(ErrorMessage.BONUS_NUMBER_NOT_NUMBER.getMessage());
        });
    }

    @Test
    void 보너스_번호가_당첨_번호와_중복될_때_예외_발생() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6", "6");
            assertThat(output()).contains(ErrorMessage.BONUS_NUMBER_DUPLICATE.getMessage());
        });
    }

    @Test
    void 구매_금액_잘못_입력시_재입력_받음() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    // 첫 번째 "1000j"는 잘못된 입력, 두 번째 "3000"은 정상 입력
                    run("1000j", "3000", "1,2,3,4,5,6", "7");

                    assertThat(output()).contains(
                            "[ERROR] 구입 금액은 유효한 숫자 형식이어야 합니다.",  // 첫 번째 입력 에러
                            "구입금액을 입력해 주세요.",
                            "3개를 구매했습니다."
                    );
                },
                List.of(1, 2, 3, 10, 11, 12),
                List.of(4, 5, 6, 13, 14, 15),
                List.of(7, 8, 9, 16, 17, 18)
        );
    }

    @Test
    void 당첨_번호_잘못_입력시_재입력_받음() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    // 당첨 번호를 잘못 입력 후 재입력
                    run("3000", "1,2,3,4,5", "1,2,3,4,5,6", "7");  // 첫 번째는 5개(잘못됨), 두 번째는 6개(정상)

                    assertThat(output()).contains(
                            "[ERROR] 당첨 번호는 6개여야 합니다.",  // 첫 번째 당첨 번호 에러
                            "당첨 번호를 입력해 주세요",
                            "3개를 구매했습니다."
                    );
                },
                List.of(1, 2, 3, 10, 11, 12),
                List.of(4, 5, 6, 13, 14, 15),
                List.of(7, 8, 9, 16, 17, 18)
        );
    }

    @Test
    void 보너스_번호_잘못_입력시_재입력_받음() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("3000", "1,2,3,4,5,6", "abc", "7");

                    assertThat(output()).contains(
                            "[ERROR] 보너스 번호는 유효한 숫자 형식이어야 합니다.",
                            "보너스 번호를 입력해 주세요",
                            "3개를 구매했습니다."
                    );
                },
                List.of(1, 2, 3, 10, 11, 12),
                List.of(4, 5, 6, 13, 14, 15),
                List.of(7, 8, 9, 16, 17, 18)
        );
    }

    @Test
    void 모든_로또가_꽝일_때_수익률_계산() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("3000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "3개를 구매했습니다.",
                            "3개 일치 (5,000원) - 0개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 0.0%입니다."
                    );
                },
                List.of(10, 11, 12, 13, 14, 15),
                List.of(20, 21, 22, 23, 24, 25),
                List.of(30, 31, 32, 33, 34, 35)
        );
    }

    @Test
    void 여러_등수_동시_당첨() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("5000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "5개를 구매했습니다.",
                            "3개 일치 (5,000원) - 2개",
                            "4개 일치 (50,000원) - 1개",
                            "5개 일치 (1,500,000원) - 1개"
                    );
                },
                List.of(1, 2, 3, 10, 11, 12),        // 3개 일치
                List.of(1, 2, 3, 4, 11, 12),         // 4개 일치
                List.of(1, 2, 3, 4, 5, 12),          // 5개 일치
                List.of(1, 2, 3, 10, 11, 13),        // 3개 일치
                List.of(10, 11, 12, 13, 14, 15)      // 꽝
        );
    }

    @Test
    void 일등_당첨_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("1000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "1개를 구매했습니다.",
                            "6개 일치 (2,000,000,000원) - 1개",
                            "총 수익률은 200000000.0%입니다."
                    );
                },
                List.of(1, 2, 3, 4, 5, 6)  // 1등: 6개 모두 일치
        );
    }

    @Test
    void 이등_당첨_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("1000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "1개를 구매했습니다.",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 1개",
                            "총 수익률은 3000000.0%입니다."
                    );
                },
                List.of(1, 2, 3, 4, 5, 7)  // 2등: 5개 일치 + 보너스 볼
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
