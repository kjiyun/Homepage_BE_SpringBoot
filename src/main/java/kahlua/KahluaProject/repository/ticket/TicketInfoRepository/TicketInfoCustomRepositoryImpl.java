package kahlua.KahluaProject.repository.ticket.TicketInfoRepository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
                        Expressions.stringTemplate("JSON_UNQUOTE(JSON_EXTRACT({0}, '$.dateTime'))", ticketInfo.ticketInfoData)
                                .desc()
                )
                .limit(limit)
                .fetch();
    }

    @Override
    public List<TicketInfo> findTicketInfosByDateTime(LocalDateTime dateTime, int limit) {
        return jpaQueryFactory
                .selectFrom(ticketInfo)
                .where(
                        Expressions.stringTemplate("JSON_UNQUOTE(JSON_EXTRACT({0}, '$.dateTime'))", ticketInfo.ticketInfoData)
                                .lt(dateTime.toString())
                )
                .orderBy(
                        Expressions.stringTemplate("JSON_UNQUOTE(JSON_EXTRACT({0}, '$.dateTime'))", ticketInfo.ticketInfoData)
                                .desc()
                )
                .limit(limit)
                .fetch();
    }
}
