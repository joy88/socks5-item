package me.binf.socks5;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.socksx.SocksMessage;
import io.netty.handler.codec.socksx.v5.DefaultSocks5CommandResponse;
import io.netty.handler.codec.socksx.v5.Socks5CommandRequest;
import io.netty.handler.codec.socksx.v5.Socks5CommandStatus;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import me.binf.socks5.utils.SocksServerUtils;

/**
 * Created by wangbin on 2015/9/9.
 */
public final class SocksServerConnectHandler extends SimpleChannelInboundHandler<SocksMessage> {

    private final Bootstrap b = new Bootstrap();

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, SocksMessage message) throws Exception {
        final Socks5CommandRequest request = (Socks5CommandRequest) message;
        Promise<Channel> promise = ctx.executor().newPromise();

        promise.addListener(new FutureListener<Channel>() {
            public void operationComplete(Future<Channel> future) throws Exception {
                final Channel outboundChannel = future.getNow();

                if (future.isSuccess()) {
                    ChannelFuture responseFuture = ctx.channel().writeAndFlush(new DefaultSocks5CommandResponse(
                            Socks5CommandStatus.SUCCESS, request.dstAddrType()));

                    responseFuture.addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) throws Exception {
                            ctx.pipeline().remove(SocksServerConnectHandler.this);
                            outboundChannel.pipeline().addLast(new RelayHandler(ctx.channel()));
                            ctx.pipeline().addLast(new RelayHandler(outboundChannel));
                        }
                    });

                } else {
                    ctx.channel().writeAndFlush(new DefaultSocks5CommandResponse(
                            Socks5CommandStatus.FAILURE, request.dstAddrType()));
                    SocksServerUtils.closeOnFlush(ctx.channel());
                }
            }
        });

        final Channel inboundChannel = ctx.channel();

        b.group(inboundChannel.eventLoop())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new DirectClientHandler(promise));

        b.connect(request.dstAddr(),request.dstPort()).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    // Connection established use handler provided results
                } else {
                    // Close the connection if the connection attempt has failed.
                    ctx.channel().writeAndFlush(
                            new DefaultSocks5CommandResponse(Socks5CommandStatus.FAILURE, request.dstAddrType()));
                    SocksServerUtils.closeOnFlush(ctx.channel());
                }
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SocksServerUtils.closeOnFlush(ctx.channel());
    }


}
