package net.javaguides.springboot_app.util;

public class EncryptionUtil {

    // Only Masking now
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "****";
        String[] parts = email.split("@");
        String namePart = parts[0];
        if (namePart.length() <= 2) return "****@" + parts[1];
        return namePart.substring(0, 2) + "****@" + parts[1];
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return "****";
        return "****" + phone.substring(phone.length() - 4);
    }
}
