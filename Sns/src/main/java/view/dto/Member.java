package view.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Member {

    private int memberNo;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate; 
    private String phone;
    private String nick;
    private String email;
    private String memberID;
    private String memberPW;
    private Timestamp joinTime;
    private String isOpened = "Y";
    private String isBlocked = "N";
    private String introduce;
    private String profileName;
    private String isExit = "N";
    private Timestamp exitTime;
}
