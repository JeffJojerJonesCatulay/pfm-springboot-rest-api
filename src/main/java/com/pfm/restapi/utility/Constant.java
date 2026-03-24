package com.pfm.restapi.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Constant {
    public static final String GEN_ERR_MSG = "Something went wrong!";
    public static final String USER_NOT_FOUND_MSG = "User not found";
    public static final String SUCCESS = "Success";
    public static final String AUTH_TYPE = "Bearer";
    public static Date EXPIRES_IN_DATE = new Date(System.currentTimeMillis() + 1000 * 60 * 15); // 15 mins
    public static String EXPIRES_IN = String.valueOf(EXPIRES_IN_DATE);
    public static Integer MAX_SIZE_ALLOWED = 300;
}
