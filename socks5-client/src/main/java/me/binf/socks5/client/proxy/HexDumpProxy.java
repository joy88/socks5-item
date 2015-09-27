package me.binf.socks5.client.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import javafx.scene.control.TextField;

/**
 * Created by wangbin on 2015/9/17.
 */
public class HexDumpProxy {


    public EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    public EventLoopGroup workerGroup = new NioEventLoopGroup();

    private static  HexDumpProxy instance= new HexDumpProxy();

    private HexDumpProxy(){};

    public static HexDumpProxy getInstance(){
        return instance;
    }



    public  void start(Integer localPort,String remoteIp,Integer remotePort)throws Exception{
        // Configure the bootstrap.
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HexDumpProxyInitializer(remoteIp, remotePort))
                    .childOption(ChannelOption.AUTO_READ, false);
//
//           b.bind(localPort).sync().addListener(new ChannelFutureListener() {
//               @Override
//               public void operationComplete(ChannelFuture future) throws Exception {
//                   System.out.println(future.isSuccess());
//               }
//           });

            b.bind(localPort).addListener(new ChannelFutureListener(){

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println(future.isSuccess());
                }
            });


        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }

    }



}
