package Service;

import static java.lang.Math.random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import javax.xml.bind.DatatypeConverter;

public class Sercurity {

    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+-=[]{}|;:'\",.<>?";

    //kiem tra mat khau voi chuoi ma hoa
    public boolean verify(String inputPassword, String hashPassWord)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inputPassword.getBytes());
        byte[] digest = md.digest();
        String myChecksum = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hashPassWord.equals(myChecksum);
    }

    //ma hoa mat khau
    public String MD5(String passWord) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(passWord.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return myHash;
    }

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Thêm một ký tự viết hoa và một ký tự đặc biệt
        password.append(UPPERCASE_CHARACTERS.charAt(random.nextInt(UPPERCASE_CHARACTERS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Thêm các ký tự ngẫu nhiên cho đến khi đạt được tối thiểu 8 ký tự
        while (password.length() < 8) {
            String characterSet = random.nextBoolean() ? LOWERCASE_CHARACTERS : DIGITS;
            password.append(characterSet.charAt(random.nextInt(characterSet.length())));
        }

        // Trộn lẫn chuỗi
        String randomizedPassword = shuffleString(password.toString());

        return randomizedPassword;
    }

    public String createOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        while (otp.length() < 6) {
            otp.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return otp.toString();
    }

    // su dung de tron lan chuoi
    public static String shuffleString(String input) {
        SecureRandom random = new SecureRandom();
        char[] charArray = input.toCharArray();
        for (int i = charArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = charArray[index];
            charArray[index] = charArray[i];
            charArray[i] = temp;
        }
        return new String(charArray);
    }

    public static void main(String[] args) {
        Sercurity sercurity = new Sercurity();
        try {
            System.out.println(sercurity.MD5("1"));
            System.out.println(sercurity.generateRandomString());
        } catch (Exception e) {
        }
    }
}
