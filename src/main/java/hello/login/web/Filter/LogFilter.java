package hello.login.web.Filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("log filter doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try{
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response);
            /* 요청이 들어오면 여기까지 수행되고 */
        }catch(Exception e){
            /* 응답이 들어오면 여기부터 시작해서 */
            throw e;
        }finally{
            log.info("RESPONSE [{}][{}]",uuid, requestURI);
            /* 수행된다 */
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
