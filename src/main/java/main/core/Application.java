package main.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Application {
    private Long applicationId;
    private Integer cardNumber;
    private String passportSeria;
    private String passportNumber;
    private Boolean confirmation;
    private Date date;
    private Date startTime;
    private Date endTime;

}
