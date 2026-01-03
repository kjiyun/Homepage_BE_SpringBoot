package kahlua.KahluaProject.global.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Locale;

public class TimeFormatUtils {

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("M월", Locale.KOREAN);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M월 d일 (E)", Locale.KOREAN);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN);

    public static String toMonthKST(LocalDateTime time) {
        return toKST(time).format(MONTH_FORMATTER);
    }

    public static String toDateKST(LocalDateTime time) {
        return toKST(time).format(DATE_FORMATTER);
    }

    public static String toTimeKST(LocalDateTime time) {
        return toKST(time).format(TIME_FORMATTER);
    }

    private static ZonedDateTime toKST(LocalDateTime utcTime) {
        // LocalDateTime을 UTC 기준으로 간주하여 ZonedDateTime으로 변환 후 KST로 변환
        return utcTime.atZone(ZoneOffset.UTC).withZoneSameInstant(KST);
    }
}