package kahlua.KahluaProject.domain.ticket;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kahlua.KahluaProject.domain.ticket.Type.FRESHMAN;
import static kahlua.KahluaProject.domain.ticket.Type.GENERAL;

@DisplayName("Ticket 도메인 테스트")
public class ticketDomainTest {

    private Ticket ticket;
    private Participants participants;

    @Test
    @DisplayName("일반티켓이 생성되는지 확인하는 테스트")
    void createGeneralTicket() {
        //given
        ticket = Ticket.builder()
                .buyer("kahlua")
                .phone_num("010-1234-5678")
                .type(GENERAL)
                .build();

        participants = Participants.builder()
                .name("kahlua1")
                .phone_num("010-1234-5678")
                .ticket(ticket)
                .build();

        //when, then
        Assertions.assertThat(ticket.getBuyer()).isEqualTo("kahlua");
        Assertions.assertThat(participants.getTicket()).isEqualTo(ticket);
    }

    @Test
    @DisplayName("신입생티켓이 생성되는지 확인하는 테스트")
    void createFreshmanTicket() {
        //given
        ticket = Ticket.builder()
                .buyer("kahlua")
                .phone_num("010-1234-5678")
                .type(FRESHMAN)
                .major("COMPUTER")
                .studentId("C511111")
                .meeting(Meeting.DAY1_ATTEND)
                .build();

        //when, then
        Assertions.assertThat(ticket.getBuyer()).isEqualTo("kahlua");
        Assertions.assertThat(ticket.getType()).isEqualTo(FRESHMAN);
        Assertions.assertThat(ticket.getMeeting()).isEqualTo(Meeting.DAY1_ATTEND);
    }
}
