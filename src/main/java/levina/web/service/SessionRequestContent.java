package levina.web.service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by MY on 11.08.2016.
 */
public class SessionRequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    // конструкторы
// метод извлечения информации из запроса
    public void extractValues(HttpServletRequest request) {

    }
    // метод добавления в запрос данных для передачи в jsp
    public void insertAttributes(HttpServletRequest request) {

    }
// some methods
}
