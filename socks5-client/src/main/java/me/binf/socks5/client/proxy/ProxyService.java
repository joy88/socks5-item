package me.binf.socks5.client.proxy;

/**
 * Created by wangbin on 2015/9/24.
 */
public interface ProxyService {

    default  void start(Integer localPort,String remoteIp,Integer remotePort){
        try {
            HexDumpProxy hexDumpProxy = HexDumpProxy.getInstance();
            hexDumpProxy.start(localPort,remoteIp,remotePort);
        }catch (Exception e){
            e.printStackTrace();
        }

     }

     default void stop(){
         HexDumpProxy hexDumpProxy = HexDumpProxy.getInstance();
         hexDumpProxy.bossGroup.shutdownGracefully();
         hexDumpProxy.workerGroup.shutdownGracefully();
     }

}
