package com.jungtin.exception.handler;

import com.jungtin.common.ViewName;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ExceptionHandlerController", urlPatterns = "/error")
public class ExceptionHandlerController extends HttpServlet {
    
    // Method to handle GET method request.
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Map<String, String> errorInfo = getErrorInfo(request, response);
        
        request.setAttribute("message", errorInfo.get("message"));
        request.setAttribute("requestUri", errorInfo.get("requestUri"));
        request.getRequestDispatcher(ViewName.ERROR).forward(request, response);
    }
    
    private static Map<String, String> getErrorInfo(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Throwable throwable = (Throwable)
            request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer)
            request.getAttribute("javax.servlet.error.status_code");
        String message = (String)
            request.getAttribute("javax.servlet.error.message");
    
        String servletName = (String)
            request.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
    
        String requestUri = (String)
            request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
    
        HashMap<String, String> errors = new HashMap<>();
        errors.put("throwable", throwable.toString());
        errors.put("statusCode", statusCode.toString());
        errors.put("message", message);
        errors.put("servletName", servletName);
        errors.put("requestUri", requestUri);
        return errors;
    }
    
    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        doGet(request, response);
    }
}
