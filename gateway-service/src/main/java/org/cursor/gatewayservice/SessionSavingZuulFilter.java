package org.cursor.gatewayservice;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.RequiredArgsConstructor;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

@RequiredArgsConstructor
public class SessionSavingZuulFilter extends ZuulFilter {

    private final SessionRepository<? extends Session> repository;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        var context = RequestContext.getCurrentContext();
        var httpSession = context.getRequest().getSession();
        var session = repository.createSession();
        context.addZuulRequestHeader("Cookie", "SESSION=" + httpSession.getId());
        System.out.println("ZuulPreFilter session proxy: " + session.getId());
        return null;
    }
}
