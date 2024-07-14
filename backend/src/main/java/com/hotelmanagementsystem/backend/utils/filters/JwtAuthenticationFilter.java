package com.hotelmanagementsystem.backend.utils.filters;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.*;
import com.hotelmanagementsystem.backend.utils.JwtUtil;
import com.hotelmanagementsystem.backend.utils.user_details.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final Map<String, Function<String, UserDetails>> dutyUserDetailsResolverMap = new HashMap<>();
    
    @Autowired
    public JwtAuthenticationFilter(OnlineUserMapper onlineUserMapper, FrontDeskStaffMapper frontDeskStaffMapper, FinancialStaffMapper financialStaffMapper, HrStaffMapper hrStaffMapper, RestaurantStaffMapper restaurantStaffMapper, PurchaseStaffMapper purchaseStaffMapper, SuperuserMapper superuserMapper) {
        dutyUserDetailsResolverMap.put("线上用户", username -> new OnlineUserDetails(onlineUserMapper.selectOne(new QueryWrapper<OnlineUser>().eq("username", username)), "线上用户"));
        dutyUserDetailsResolverMap.put("酒店前台工作人员", username -> new FrontDeskStaffUserDetails(frontDeskStaffMapper.selectOne(new QueryWrapper<FrontDeskStaffUser>().eq("username", username)), "酒店前台工作人员"));
        dutyUserDetailsResolverMap.put("餐厅前台工作人员", username -> new RestaurantStaffUserDetails(restaurantStaffMapper.selectOne(new QueryWrapper<RestaurantStaffUser>().eq("username", username)), "餐厅前台工作人员"));
        dutyUserDetailsResolverMap.put("财务管理员", username -> new FinancialStaffUserDetails(financialStaffMapper.selectOne(new QueryWrapper<FinancialStaffUser>().eq("username", username)), "财务管理员"));
        dutyUserDetailsResolverMap.put("人事管理员", username -> new HrStaffUserDetails(hrStaffMapper.selectOne(new QueryWrapper<HrStaffUser>().eq("username", username)), "人事管理员"));
        dutyUserDetailsResolverMap.put("采购人员", username -> new PurchaseStaffUserDetails(purchaseStaffMapper.selectOne(new QueryWrapper<PurchaseStaffUser>().eq("username", username)), "采购人员"));
        dutyUserDetailsResolverMap.put("超级用户", username -> new SuperUserDetails(superuserMapper.selectOne(new QueryWrapper<Superuser>().eq("username", username)), "超级用户"));
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取token
        String token = request.getHeader("Authorization");
        
        // 如果没有token或格式不正确，则直接放行
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 跳过 "Bearer "
        token = token.substring(7);
        
        // token中的username和duty
        String username;
        String duty;
        
        try {
            // 从token中解析出claims
            Claims claims = JwtUtil.parseJWT(token);
            // 解析出username
            username = claims.getSubject();
            // 解析出duty，如果没有duty信息则返回null
            duty = claims.get("duty", String.class) == null ? "线上用户" : claims.get("duty", String.class);
        } catch (JwtException e) {
            responseUnauthorized(response, e.getMessage());
            return;
        }
        
        // 根据duty在map中取出对应的userDetailsResolver
        Function<String, UserDetails> userDetailsResolver = dutyUserDetailsResolverMap.get(duty);
        if (userDetailsResolver == null) {
            responseUnauthorized(response, "错误的token！");
            return;
        }
        // 执行userDetailsResolver，获取userDetails
        UserDetails userDetails = userDetailsResolver.apply(username);
        if (userDetails == null) {
            responseUnauthorized(response, "未找到token的所有者！");
            return;
        }
        
        // 新建一个认证成功的身份信息（而不是认证请求了），并将其存入SecurityContextHolder中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // spring对于用户的每个HTTP请求都会新建一个线程来处理它，新线程会包含一个新的SecurityContextHolder，所以每个线程的安全上下文都是不同的！
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 继续执行其他过滤器
        filterChain.doFilter(request, response);
    }
    
    /*
     * 响应401，并附带错误信息
     */
    private void responseUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
    }
    
}