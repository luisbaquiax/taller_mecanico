package org.ayd.apimecahnicalworkshop.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAssigmentDTO {
    private Long jobAssignmentId;
    private Long jobId;
    private Long userId;
    private String roleAssignment;
    private String notes;
}
