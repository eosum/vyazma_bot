package main.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    private Long serviceId;
    private String name;
    private Date workingTimeStart;
    private Date workingTimeEnd;
    private String description;
    private Long roomId;
    private String employeePosition;

    public Service(Long serviceId, String name, String description, Long roomId, String employeePosition) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.roomId = roomId;
        this.employeePosition = employeePosition;
    }
}
