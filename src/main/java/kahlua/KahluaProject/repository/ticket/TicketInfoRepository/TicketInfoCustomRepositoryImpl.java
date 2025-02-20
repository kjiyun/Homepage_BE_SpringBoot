package kahlua.KahluaProject.repository.ticket.TicketInfoRepository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static kahlua.KahluaProject.domain.ticketInfo.QTicketInfo.ticketInfo;


@Repository
@RequiredArgsConstructor
public class TicketInfoCustomRepositoryImpl implements TicketInfoCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TicketInfo> findTicketInfos(int limit) {
        return jpaQueryFactory
                .selectFrom(ticketInfo)
                .orderBy(
                        Expressions.stringTemplate("JSON_UNQUOTE(JSON_EXTRACT({0}, '$.date_time'))", ticketInfo.ticketInfoData)
                                .desc()
                )
                .limit(limit)
                .fetch();
    }

    @Override
    public List<TicketInfo> findTicketInfosOrderByDateTime(LocalDateTime cursorDateTime, int limit) {
        DateTimeExpression<LocalDateTime> dbDateTimeExpr = Expressions.dateTimeTemplate(
                LocalDateTime.class,
                "STR_TO_DATE(CONCAT("
                        + "CONCAT('', JSON_UNQUOTE(JSON_EXTRACT({0}, '$.date_time[0]'))), '-',"
                        + "LPAD(CONCAT('', JSON_UNQUOTE(JSON_EXTRACT({0}, '$.date_time[1]'))), 2, '0'), '-',"
                        + "LPAD(CONCAT('', JSON_UNQUOTE(JSON_EXTRACT({0}, '$.date_time[2]'))), 2, '0'), ' ',"
                        + "LPAD(CONCAT('', JSON_UNQUOTE(JSON_EXTRACT({0}, '$.date_time[3]'))), 2, '0'), ':',"
                        + "LPAD(CONCAT('', JSON_UNQUOTE(JSON_EXTRACT({0}, '$.date_time[4]'))), 2, '0'), ':',"
                        + "LPAD(CONCAT('', JSON_UNQUOTE(JSON_EXTRACT({0}, '$.date_time[5]'))), 2, '0')"
                        + "), '%Y-%m-%d %H:%i:%s')",
                ticketInfo.ticketInfoData
        );

        String cursorFormatted = cursorDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        DateTimeExpression<LocalDateTime> cursorDateTimeExpr = Expressions.dateTimeTemplate(
                LocalDateTime.class,
                "STR_TO_DATE({0}, '%Y-%m-%d %H:%i:%s')",
                Expressions.constant(cursorFormatted)
        );

        //cursorDateTime보다 과거, 최신순, 페이징
        return jpaQueryFactory
                .selectFrom(ticketInfo)
                .where(dbDateTimeExpr.lt(cursorDateTimeExpr))
                .orderBy(dbDateTimeExpr.desc())
                .limit(limit)
                .fetch();
    }

}
