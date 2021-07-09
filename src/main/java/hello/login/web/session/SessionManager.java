package hello.login.web.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    public static final String SESSOIN_COOKIE_NAME = "mySessoinId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /*
     * 세션 생성
     * 1.세션 id생성
     * 2.세션 저장소에 sessionId와 보관할 값 저장
     * 3.sessoinId로 응답 쿠키를 생성해서 클라이언트에 전달
     * */
    public void createSession(Object value, HttpServletResponse response) {

        // * 1.세션 id생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // * 2.쿠키를 생성
        Cookie mySessionCookie = new Cookie(SESSOIN_COOKIE_NAME, sessionId);

        // * 3.쿠키를 클라이언트에 전달
        response.addCookie(mySessionCookie);
    }

    /*
    * 세션 조회
    * */
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSOIN_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    /*
     * 만료
     * */
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSOIN_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName){
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())// Arrays.stream(배열) : 배열을 스트림으로 바꿔준다.
                .filter(c->c.getName().equals(SESSOIN_COOKIE_NAME))
                .findAny()
                .orElse(null);
    }


}
