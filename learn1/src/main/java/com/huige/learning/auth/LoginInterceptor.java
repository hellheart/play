package com.huige.learning.auth;

import com.huige.learning.config.JwtUtil;
import com.huige.learning.config.TokenBlacklist;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    private final TokenBlacklist blacklist;

    public LoginInterceptor(TokenBlacklist blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (token == null || !JwtUtil.validate(token)) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            response.getWriter().write("{\"code\":401,\"message\":\"请先登录\"}");
            return false;
        }
        if (blacklist.isBlacklisted(token)) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            response.getWriter().write("{\"code\":401,\"message\":\"token已失效，请重新登录\"}");
            return false;
        }
        Claims claims = JwtUtil.parseToken(token);
        request.setAttribute("username", claims.getSubject());
        String newToken = JwtUtil.generateToken(claims.getSubject());
        response.setHeader("X-Refresh-Token", newToken);
        return true;
    }
}
