package view.dto;

import lombok.Data;

@Data
public class Login {
    private String memberID;
    private String password;
    private String memberNo;
    private boolean isLogin;
}
