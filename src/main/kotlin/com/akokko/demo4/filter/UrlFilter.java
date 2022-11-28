package com.akokko.filter;

public class UrlFilter {

    //所有需要传递令牌的地址
    public static String filterPath="/assets/**,/css/**,/js/**,/fonts/**,/font-awesome-4.7.0/**,/img/**,/images/**,/packages/**,/plugins/**,/login,/regist,/verify,/checkEmail,/forget,/retrievePassword,/changePassword,/forget,/change,/back,/backlogin,/demo";

    public static boolean hasAuthorize(String url){

        String[] split = filterPath.replace("**", "").split(",");

        for (String value : split) {

            if (url.startsWith(value)) {
                return true;
            }

            if (url.equals("/")){
                return true; //代表当前的访问地址是需要传递令牌的
            }
        }

        return false; //代表当前的访问地址是不需要传递令牌的
    }
}
