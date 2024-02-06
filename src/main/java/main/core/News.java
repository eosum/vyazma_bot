package main.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class News {
    private Long id;
    private String header;
    private String description;
    private Long creatorId;
    private Date date;
}
