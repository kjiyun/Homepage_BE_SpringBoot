package kahlua.KahluaProject.repository.reservation;

import kahlua.KahluaProject.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationCustomRepository {
}
