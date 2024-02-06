package main.core;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest {
    private Long guestId;
    private String name;
    private String surname;
    private String middleName;
    private String passportSeria;
    private String passportNumber;
    private String passportOrganization;
    private Date passportDate;
    private String passportDivisionCode;
}
