package main.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long employeeId;
    private String name;
    private String surname;
    private String middleName;
    private String position;
    private String email;
    private String phoneNumber;
}
