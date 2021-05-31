package com.jungtin.common;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class Validator {
    
    public void checkRequired(String paramName,
        HttpServletRequest request,
        HashMap<String, String> errors) {
        String value = request.getParameter(paramName);
        if(value != null && !value.isBlank())
            return;
        
        if(errors.containsKey(paramName)) {
            String err = errors.get(paramName);
            err += "<br>";
            err += String.format("%s can't be leave blank", paramName);
            System.out.println(err);
            errors.put(paramName, err);
        } else
            errors.put(paramName, String.format("%s can't be leave blank", paramName));
    }
    
    public void checkInteger(String paramName,
        HttpServletRequest request,
        HashMap<String, String> errors) {
        try {
            if(request.getParameter(paramName) == null || request.getParameter(paramName).isBlank())
                return;
            
            Integer.parseInt(request.getParameter(paramName));
        } catch (NumberFormatException exc) {
            if(errors.containsKey(paramName)) {
                String err = errors.get(paramName);
                err += "<br>";
                err += String.format("%s must be number without fraction. Ex: 25", paramName);
                System.out.println(err);
                errors.put(paramName, err);
            } else
                errors.put(paramName, String.format("%s must be number without fraction. Ex: 25", paramName));
        }
    }
    
    public void checkDouble(String paramName,
        HttpServletRequest request,
        HashMap<String, String> errors) {
        try {
            if(request.getParameter(paramName) == null || request.getParameter(paramName).isBlank())
                return;
            
            Double.parseDouble(request.getParameter(paramName));
        } catch (NumberFormatException exc) {
            if(errors.containsKey(paramName)) {
                String err = errors.get(paramName);
                err += "<br>";
                err += String.format("%s format is wrong, must be like : 12.00 or 12.50", paramName);
                System.out.println(err);
                errors.put(paramName, err);
            } else
                errors.put(paramName, String.format("%s format is wrong, must be like : 12.00 or 12.50", paramName));
        }
    }
    
    public void checkUrl(String paramName,
        HttpServletRequest request,
        HashMap<String, String> errors) {
        if(request.getParameter(paramName) == null || request.getParameter(paramName).isBlank())
            return;
        
        final String regex = "^(?:http(s):\\/\\/)[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-\\._~:/?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=.]+$";
        if(!request.getParameter(paramName).matches(regex)) {
            if(errors.containsKey(paramName)) {
                String err = errors.get(paramName);
                err += "<br>";
                err += String.format("%s is not url, must start with http(s)://", paramName);
                System.out.println(err);
                errors.put(paramName, err);
            } else
                errors.put(paramName, String.format("%s is not url, must start with http(s)://", paramName));
        }
        
    }
}
