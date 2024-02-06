package main.commands;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public abstract class ServiceChoice implements Command {
    private Integer serviceId;
    private Integer userId;
    private String problemDescription;
    private Date day;
    private Date time;
}
