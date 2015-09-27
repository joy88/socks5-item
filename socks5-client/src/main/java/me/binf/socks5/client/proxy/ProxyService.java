package me.binf.socks5.client.proxy;

/**
 * Created by wangbin on 2015/9/24.
 */
public interface ProxyService {

    public void start(Integer localPort,String remoteIp,Integer remotePort);

     default void stop(){
         HexDumpProxy hexDumpProxy = HexDumpProxy.getInstance();
         hexDumpProxy.bossGroup.shutdownGracefully();
         hexDumpProxy.workerGroup.shutdownGracefully();
     }



}
