package bt.gcit.edu.maintenance.service;

import bt.gcit.edu.maintenance.entity.MaintenanceTicket;
import java.util.List;

public interface MaintenanceService {
    MaintenanceTicket createTicket(MaintenanceTicket ticket);
    List<MaintenanceTicket> getTicketsByEmployee(String email);
    List<MaintenanceTicket> getAllTickets();
    MaintenanceTicket updateTicketStatus(Long id, String status, String managerNotes);
}