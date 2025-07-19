package litmus7.employeemanager.com.util;

public class ValidatorUtil {
    

    public static boolean isValidEmail(String email) {
        return email.contains("@");
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        return phone.trim().matches("^[0-9]{10,15}$");
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]+");
    }

}
