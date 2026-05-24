package bt.gcit.edu.maintenance.rest;

import bt.gcit.edu.maintenance.entity.MaintenanceTicket;
import bt.gcit.edu.maintenance.service.MaintenanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.security.Principal;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping("/tickets")
    public ResponseEntity<MaintenanceTicket> createTicket(
            @RequestBody MaintenanceTicket ticket,
            Principal principal) {

        // Extract email from JWT and bind it to the entity to satisfy the non-null
        // constraint
        if (principal != null) {
            ticket.setReportedByEmail(principal.getName());
        }

        return ResponseEntity.ok(maintenanceService.createTicket(ticket));
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<List<MaintenanceTicket>> getMyTickets(Principal principal) {
        String email = principal.getName();
        return ResponseEntity.ok(maintenanceService.getTicketsByEmployee(email));
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<MaintenanceTicket>> getAllTickets() {
        return ResponseEntity.ok(maintenanceService.getAllTickets());
    }

    @PutMapping("/tickets/{id}/status")
    public ResponseEntity<?> updateTicketStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> updateRequest) {
        try {
            String status = updateRequest.get("status");
            String notes = updateRequest.get("managerNotes");
            MaintenanceTicket updated = maintenanceService.updateTicketStatus(id, status, notes);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        try {
            maintenanceService.deleteTicket(id);
            return ResponseEntity.ok(Map.of("message", "Ticket deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}