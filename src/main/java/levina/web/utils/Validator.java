package levina.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MY on 05.09.2016.
 */
public class Validator {
    private static Pattern usrNamePattern = Pattern.compile("[\\w+]{6,14}");
    private static Pattern namePattern = Pattern.compile("^[a-zA-Z\'\\s]+");
    private static Pattern emailPattern = Pattern.compile("(\\w{4,})@(\\w+\\.)([a-z]{2,4})");
    private static Pattern passwordPattern = Pattern.compile("(?=.*\\d)(?=.*[a-z]).{6,}");
    private static Pattern passportSeriesPattern = Pattern.compile("[A-Z]{1,4}");
    private static Pattern passportNumberPattern = Pattern.compile("[\\d]{6,14}");
    private static Pattern personalNumberPattern = Pattern.compile("[A-Z0-9]{11,16}");
    private static Pattern birthdayPattern = Pattern.compile("(19|20)\\d\\d([- /.])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])");
    private static Pattern datesFirstPattern = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
    private static Pattern datesSecondPattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static Pattern phonePattern = Pattern.compile("\\+(375)(\\d){2}(\\d){3}(-)?(\\d){2}(-)?(\\d){2}");

    public static boolean validateEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateUserName(String userName) {
        Matcher matcher = usrNamePattern.matcher(userName);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    public static boolean validateName(String name) {
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public static boolean validatePassportSeries(String series) {
        Matcher matcher = passportSeriesPattern.matcher(series);
        return matcher.matches();
    }

    public static boolean validatePassportNumber(String number) {
        Matcher matcher = passportNumberPattern.matcher(number);
        return matcher.matches();
    }

    public static boolean validatePersonalNumber(String number) {
        Matcher matcher = personalNumberPattern.matcher(number);
        return matcher.matches();
    }

    public static boolean validateBirthday(String birthday) {
        Matcher matcher = birthdayPattern.matcher(birthday);
        return matcher.matches();
    }

    public static boolean validatePhone(String phone) {
        Matcher matcher = phonePattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean validateDatesFormat(String date) {
        Matcher matcherFirst = datesFirstPattern.matcher(date);
        Matcher matcherSecond = datesSecondPattern.matcher(date);
        return matcherFirst.matches() || matcherSecond.matches();
    }

    public static boolean checkDates(String start, String end) {
        SimpleDateFormat formatStandard = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        java.util.Date dateStart = null;
        java.util.Date dateEnd = null;
        try {
            dateStart = format.parse(start);
            dateEnd = format.parse(end);
        } catch (ParseException e) {
            try {
                dateStart = formatStandard.parse(start);
                dateEnd = formatStandard.parse(end);
            } catch (ParseException e1) {
                return false;
            }
        }
        return dateStart.getTime() >= Calendar.getInstance().getTime().getTime() &&
                dateStart.getTime() <= dateEnd.getTime();
    }

}
