package magazine.utils;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by pvc on 08.11.2015.
 */
public class CharsetFilter implements Filter {

    // кодировка
    private String encoding;

    @Override
    public void init(FilterConfig config) throws ServletException {
        // читаем из конфигурации
        encoding = config.getInitParameter("requestEncoding");

        // если не установлена - устанавливаем Cp1251
        if( encoding==null ) encoding="UTF-8";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        next.doFilter(request, response);
    }

    public void destroy(){}
}