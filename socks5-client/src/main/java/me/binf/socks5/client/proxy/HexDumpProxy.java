package me.binf.socks5.client.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import javafx.scene.control.TextField;
import me.binf.socks5.client.service.ProxyService;
import me.binf.socks5.client.service.ProxyServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbin on 2015/9/17.
 */
public class HexDumpProxy {

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;

    private EventLoopGroup lastBossGroup = null;
    private EventLoopGroup lastWorkerGroup = null;

    private List<EventLoopGroup> loopPool  = new ArrayList<>();

    private static  HexDumpProxy instance= new HexDumpProxy();

    private HexDumpProxy(){};

    public static HexDumpProxy getInstance(){
        return instance;
    }

    public  void start(Integer localPort,String remoteIp,Integer remotePort)throws Exception{

        // Configure the bootstrap.
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new HexDumpProxyInitializer(remoteIp, remotePort))
                .childOption(ChannelOption.AUTO_READ, false);


        ChannelFuture f = b.bind(localPort).sync();
        f.addListener(new ChannelFutureListener(){
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                ProxyService proxyService = ProxyServiceImpl.getInstance();
                if(future.isSuccess()){
                    if(lastBossGroup!=null&&lastWorkerGroup!=null){
                        lastBossGroup.shutdownGracefully();
                        lastWorkerGroup.shutdownGracefully();
                    }
                    lastBossGroup =   bossGroup;
                    lastWorkerGroup = workerGroup;
                    proxyService.noticeView("local port:\""+localPort+"\"connect success!");
                }
            }
        });
        f.channel().closeFuture().sync();

    }


    public void stop(){
        if(bossGroup!=null&&workerGroup!=null){
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
