package me.binf.socks5.client.service;

import me.binf.socks5.client.proxy.HexDumpProxy;

/**
 * Created by wangbin on 2015/9/24.
 */
public interface ProxyService {

    public void start(Integer localPort,String remoteIp,Integer remotePort);

    public void noticeView(String message);

    public void stop();


}
