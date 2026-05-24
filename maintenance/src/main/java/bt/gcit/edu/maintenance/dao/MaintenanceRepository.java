package bt.gcit.edu.maintenance.dao;

import bt.gcit.edu.maintenance.entity.MaintenanceTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceTicket, Long> {
    // Fetches only the tickets submitted by a specific employee email profile
    List<MaintenanceTicket> findByReportedByEmail(String email);
}