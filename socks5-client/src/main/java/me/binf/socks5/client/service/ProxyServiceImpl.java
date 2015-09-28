package me.binf.socks5.client.service;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import me.binf.socks5.client.proxy.HexDumpProxy;
import me.binf.socks5.client.view.BaseController;

/**
 * Created by wangbin on 2015/9/24.
 */
public class ProxyServiceImpl implements ProxyService {


    private BaseController baseController;
    private static  ProxyServiceImpl instance= new ProxyServiceImpl();

    private ProxyServiceImpl(){};

    public static ProxyServiceImpl getInstance(BaseController  baseController){
        instance.baseController = baseController;
        return instance;
    }

    public static ProxyServiceImpl getInstance(){
        return instance;
    }



    @Override
    public void start(Integer localPort, String remoteIp, Integer remotePort) {

        HexDumpProxy hexDumpProxy = HexDumpProxy.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    hexDumpProxy.start(localPort,remoteIp,remotePort);
                }catch (Exception e){
                    stop();
                    e.printStackTrace();
                    noticeView("error in application!");
                }
            }
        }).start();
    }


    @Override
    public void stop() {
        HexDumpProxy hexDumpProxy = HexDumpProxy.getInstance();
        hexDumpProxy.stop();
    }


    /**
     * 通知界面label更新
     * @param message
     */
    @Override
    public void noticeView(String message) {
        try {
            baseController.notification(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
