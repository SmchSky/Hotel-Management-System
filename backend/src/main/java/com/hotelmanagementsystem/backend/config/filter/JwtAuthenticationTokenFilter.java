package com.hotelmanagementsystem.backend.config.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.*;
import com.hotelmanagementsystem.backend.service.impl.utils.*;
import com.hotelmanagementsystem.backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PipedInputStream;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private OnlineUserMapper onlineUserMapper;
    @Autowired
    private FrontDeskStaffMapper frontDeskStaffMapper;
    @Autowired
    private FinancialStaffMapper financialStaffMapper;
    @Autowired
    private HrStaffMapper hrStaffMapper;
    @Autowired
    private RestaurantStaffMapper restaurantStaffMapper;
    @Autowired
    private PurchaseStaffMapper purchaseStaffMapper;
    @Autowired
    private SuperuserMapper superuserMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);  //跳过"Bearer "

        String username;
        String duty;

        try {
            Claims claims = JwtUtil.parseJWT(token);
            username = claims.getSubject();
            duty = claims.get("duty", String.class);  //解析出duty，如果没有duty信息则返回null
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (duty == null) {  //说明登录者为OnlineUser
            QueryWrapper<OnlineUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            OnlineUser onlineUser = onlineUserMapper.selectOne(queryWrapper);

            if (onlineUser == null) {
                throw new RuntimeException("token找不到对应的OnlineUser用户！");
            }

            OnlineUserDetailsImpl loginUser = new OnlineUserDetailsImpl(onlineUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } else if ("酒店前台工作人员".equals(duty)) {
            QueryWrapper<FrontDeskStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            FrontDeskStaff loginUser = frontDeskStaffMapper.selectOne(queryWrapper);

            if (loginUser == null) {
                throw new RuntimeException("token找不到对应的StaffUser用户！");
            }

            FrontDeskStaffStaffUserDetailsImpl loginUserDetails = new FrontDeskStaffStaffUserDetailsImpl(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserDetails, null, null);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } else if ("餐厅前台工作人员".equals(duty)) {
            QueryWrapper<RestaurantStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            RestaurantStaff loginUser = restaurantStaffMapper.selectOne(queryWrapper);

            if (loginUser == null) {
                throw new RuntimeException("token找不到对应的StaffeUser用户！");
            }

            RestaurantStaffStaffUserDetailsImpl loginUserDetails = new RestaurantStaffStaffUserDetailsImpl(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserDetails, null, null);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } else if ("财务管理员".equals(duty)) {
            QueryWrapper<FinancialStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            FinancialStaff loginUser = financialStaffMapper.selectOne(queryWrapper);

            if (loginUser == null) {
                throw new RuntimeException("token找不到对应的StaffUser用户！");
            }

            FinancialStaffStaffUserDetailsImpl loginUserDetails = new FinancialStaffStaffUserDetailsImpl(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserDetails, null, null);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);

        } else if ("人事管理员".equals(duty)) {
            QueryWrapper<HrStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            HrStaff loginUser = hrStaffMapper.selectOne(queryWrapper);

            if (loginUser == null) {
                throw new RuntimeException("token找不到对应的StaffUser用户！");
            }

            HrStaffStaffUserDetailsImpl loginUserDetails = new HrStaffStaffUserDetailsImpl(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserDetails, null, null);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);

        } else if (("采购人员".equals(duty))) {
            QueryWrapper<PurchaseStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            PurchaseStaff loginUser = purchaseStaffMapper.selectOne(queryWrapper);

            if (loginUser == null) {
                throw new RuntimeException("token找不到对应的StaffUser用户！");
            }

            PurchaseStaffStaffUserDetailsImpl loginUserDetails = new PurchaseStaffStaffUserDetailsImpl(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserDetails, null, null);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);

        } else if ("超级用户".equals(duty)) {
            QueryWrapper<Superuser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            Superuser loginUser = superuserMapper.selectOne(queryWrapper);

            if (loginUser == null) {
                throw new RuntimeException("token找不到对应的SuperUser用户！");
            }

            SuperUserDetailsImpl loginUserDetails = new SuperUserDetailsImpl(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserDetails, null, null);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);

        }


    }
}