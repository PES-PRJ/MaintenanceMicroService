package bt.gcit.edu.maintenance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_tickets")
public class MaintenanceTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset_serial_no", nullable = false)
    private String assetSerialNo; // Links to Asset Microservice

    @Column(name = "reported_by_email", nullable = false)
    private String reportedByEmail; // Links to User Microservice

    @Column(name = "issue_type", nullable = false)
    private String issueType; // WEAR_AND_TEAR, HARDWARE_MALFUNCTION, UPGRADE_REQUEST, SOFTWARE_CRASH

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String status; // REPORTED, IN_PROGRESS, RESOLVED

    @Column(columnDefinition = "TEXT", name = "manager_notes")
    private String managerNotes; // Logged by the Asset Manager when fixing it

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // ==========================================
    // Constructors
    // ==========================================
    public MaintenanceTicket() {}

    public MaintenanceTicket(Long id, String assetSerialNo, String reportedByEmail, String issueType, 
                             String description, String status, String managerNotes, LocalDateTime createdAt) {
        this.id = id;
        this.assetSerialNo = assetSerialNo;
        this.reportedByEmail = reportedByEmail;
        this.issueType = issueType;
        this.description = description;
        this.status = status;
        this.managerNotes = managerNotes;
        this.createdAt = createdAt;
    }

    // ==========================================
    // Getters and Setters
    // ==========================================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetSerialNo() {
        return assetSerialNo;
    }

    public void setAssetSerialNo(String assetSerialNo) {
        this.assetSerialNo = assetSerialNo;
    }

    public String getReportedByEmail() {
        return reportedByEmail;
    }

    public void setReportedByEmail(String reportedByEmail) {
        this.reportedByEmail = reportedByEmail;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManagerNotes() {
        return managerNotes;
    }

    public void setManagerNotes(String managerNotes) {
        this.managerNotes = managerNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}