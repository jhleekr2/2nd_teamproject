package view.util;

public class PasswordValidator {

    public static boolean isValid(String password) {
        // 비밀번호 조건 검증 (8자 이상, 대문자 포함, 숫자 및 특수문자 포함)
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";
        return password != null && password.matches(passwordPattern);
    }
}
