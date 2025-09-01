package org.ayd.apimecahnicalworkshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class DetailJobDTO {

    private String nameTypeJob;
    private String nameServiceType;
    private String nameStatusJob;
    private Integer jobId;
    private String model;
    private String color;
    private String name;
    private String lastName;
    private LocalDateTime createdAt;

    public DetailJobDTO(String nameTypeJob, String nameServiceType, String nameStatusJob,
                        Integer jobId, String model, String color, String name,
                        String lastName, LocalDateTime createdAt) {
        this.nameTypeJob = nameTypeJob;
        this.nameServiceType = nameServiceType;
        this.nameStatusJob = nameStatusJob;
        this.jobId = jobId;
        this.model = model;
        this.color = color;
        this.name = name;
        this.lastName = lastName;
        this.createdAt = createdAt;
    }

}
