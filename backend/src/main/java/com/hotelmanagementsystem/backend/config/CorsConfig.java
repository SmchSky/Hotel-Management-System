// package com.hotelmanagementsystem.backend.config;
//
// import org.springframework.context.annotation.Configuration;
//
// import javax.servlet.*;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
//
// @Configuration
// public class CorsConfig implements Filter {
//     @Override
//     public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//         HttpServletResponse response = (HttpServletResponse) res;
//         HttpServletRequest request = (HttpServletRequest) req;
//
//         // 获取请求的来源
//         String origin = request.getHeader("Origin");
//
//         // 检查来源是否允许
//         if (origin != null && origin.equals("http://localhost:8081")) {
//             response.setHeader("Access-Control-Allow-Origin", origin);
//         }
//
//         // 设置其他CORS相关的头信息
//         String headers = request.getHeader("Access-Control-Request-Headers");
//         if (headers != null) {
//             response.setHeader("Access-Control-Allow-Headers", headers);
//             response.setHeader("Access-Control-Expose-Headers", headers);
//         }
//
//         response.setHeader("Access-Control-Allow-Methods", "*");
//         response.setHeader("Access-Control-Max-Age", "3600");
//         response.setHeader("Access-Control-Allow-Credentials", "true");
//
//         // 处理OPTIONS预检请求
//         if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//             response.setStatus(HttpServletResponse.SC_OK);
//         } else {
//             chain.doFilter(request, response);
//         }
//     }
//
//     @Override
//     public void init(FilterConfig filterConfig) {
//
//     }
//
//     @Override
//     public void destroy() {
//         Filter.super.destroy();
//     }
// }
