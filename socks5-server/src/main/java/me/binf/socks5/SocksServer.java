package me.binf.socks5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import me.binf.socks5.core.Configue;
import me.binf.socks5.utils.ConfigUtil;

/**
 * Created by wangbin on 2015/9/8.
 */
public class SocksServer {

    public static void main(String[] args) {
        try {
            Integer port = Integer.valueOf(Configue.getServerPort());
            serverBootstrap(port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static void serverBootstrap(int port)throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new SocksServerInitializer());
            b.bind(port).sync().channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }



}
