package me.binf.socks5.core;

import me.binf.socks5.utils.ConfigUtil;

/**
 * Created by wangbin on 2015/9/9.
 */
public class Configue {

    public static String getServerPort(){
        return ConfigUtil.getString("server.port");
    }
}
