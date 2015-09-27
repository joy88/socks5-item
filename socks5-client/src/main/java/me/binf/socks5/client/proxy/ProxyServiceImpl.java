package me.binf.socks5.client.proxy;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created by wangbin on 2015/9/24.
 */
public class ProxyServiceImpl implements ProxyService {

    @Override
    public void start(Integer localPort, String remoteIp, Integer remotePort) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        HexDumpProxy  hexDumpProxy = HexDumpProxy.getInstance();
        hexDumpProxy.bossGroup =    bossGroup;
        hexDumpProxy.workerGroup =  workerGroup;
        try {
            hexDumpProxy.start(localPort,remoteIp,remotePort);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
