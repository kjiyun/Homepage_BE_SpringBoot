package kahlua.KahluaProject.repository.reservation;

import kahlua.KahluaProject.domain.reservation.Reservation;
import kahlua.KahluaProject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationCustomRepository {
    List<Reservation> findByUserOrderByReservationDateDescStartTimeAsc(User user);
}
