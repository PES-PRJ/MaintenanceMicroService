package bt.gcit.edu.maintenance.service;

import bt.gcit.edu.maintenance.dao.MaintenanceRepository; // Verify your actual package name here
import bt.gcit.edu.maintenance.entity.MaintenanceTicket;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    @Override
    public MaintenanceTicket createTicket(MaintenanceTicket ticket) {
        // Set lifecycle tracking timestamp status defaults safely
        if (ticket.getStatus() == null || ticket.getStatus().trim().isEmpty()) {
            ticket.setStatus("REPORTED");
        }

        if (ticket.getCreatedAt() == null) {
            ticket.setCreatedAt(LocalDateTime.now());
        }

        return maintenanceRepository.save(ticket);
    }

    @Override
    public List<MaintenanceTicket> getTicketsByEmployee(String email) {
        return maintenanceRepository.findByReportedByEmail(email);
    }

    @Override
    public List<MaintenanceTicket> getAllTickets() {
        return maintenanceRepository.findAll();
    }

    @Override
    public MaintenanceTicket updateTicketStatus(Long id, String status, String managerNotes) {
        MaintenanceTicket ticket = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance ticket not found with id: " + id));

        ticket.setStatus(status);
        ticket.setManagerNotes(managerNotes);

        return maintenanceRepository.save(ticket);
    }

    @Override
    public void deleteTicket(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new RuntimeException("Maintenance ticket not found with id: " + id);
        }
        maintenanceRepository.deleteById(id);
    }
}