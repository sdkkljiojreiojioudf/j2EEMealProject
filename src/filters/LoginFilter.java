package filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("*")
public class LoginFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) {
        //init
        context = filterConfig.getServletContext();
        context.log("LoginFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        String authenticateUrl = req.getContextPath() + "/authenticate/authenticate.xhtml";
        String loggedUrl = req.getContextPath() + "/index.xhtml";

        boolean logged = session != null && session.getAttribute("userSession") != null;
        boolean authenticateReq = req.getRequestURI().equals(authenticateUrl);

        boolean resourcesReq = req.getRequestURI().contains("resource");

        if (resourcesReq) {
            filterChain.doFilter(req, resp);
        } else if (logged && authenticateReq) {
            context.log("user connected redirect to logged url");
            resp.sendRedirect(loggedUrl);
        } else if (!logged && !authenticateReq) {
            context.log("Unauthorized user redirected to authenticated Url");
            resp.sendRedirect(authenticateUrl);
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
        //destroy
    }
}