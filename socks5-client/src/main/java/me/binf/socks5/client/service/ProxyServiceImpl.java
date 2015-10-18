package me.binf.socks5.client.service;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import me.binf.socks5.client.proxy.HexDumpProxy;
import me.binf.socks5.client.view.BaseController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangbin on 2015/9/24.
 */
public class ProxyServiceImpl implements ProxyService {


    private BaseController baseController;
    private static  ProxyServiceImpl instance= new ProxyServiceImpl();

    private  static ExecutorService executorService ;


    HexDumpProxy hexDumpProxy ;

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

        stop();
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    hexDumpProxy = new HexDumpProxy();
                    hexDumpProxy.start(localPort, remoteIp, remotePort);
                } catch (Exception e) {
                    noticeView("本地"+localPort+"端口启动失败!");
                }
            }
        });

    }


    @Override
    public void stop() {
        if(hexDumpProxy!=null&&executorService!=null){
            hexDumpProxy.stop();
            executorService.shutdownNow();
        }
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
