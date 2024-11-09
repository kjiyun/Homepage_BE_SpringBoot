package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByReservationDate(LocalDate date);
    List<Reservation> findAllByUser_Id(Long userId);
}
