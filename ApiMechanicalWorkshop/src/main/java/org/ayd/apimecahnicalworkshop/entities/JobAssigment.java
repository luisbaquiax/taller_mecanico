package org.ayd.apimecahnicalworkshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobs_assignments")
@Data @NoArgsConstructor @AllArgsConstructor
public class JobAssigment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_assignment_id", nullable = false)
    private Long jobAssignmentId;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "role_assignment", nullable = false, length = 20)
    private String roleAssignment;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
//
//    @Column(name = "assigned_at", nullable = false)
//    private String assignedAt;
//
//    @Column(name = "unassigned_at")
//    private String unassignedAt;
}
