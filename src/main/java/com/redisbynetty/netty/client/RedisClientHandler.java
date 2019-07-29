package com.redisbynetty.netty.client;

import com.redisbynetty.commands.BaseCommand;
import com.redisbynetty.commands.CommandProcessor;
import com.redisbynetty.commands.CommandRegistry;
import com.redisbynetty.utils.RedisConstants;
import com.redisbynetty.runnable.MonitorMasterTask;
import com.redisbynetty.utils.RedisUtils;
import io.netty.channel.*;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.redis.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 2018/11/19.
 */
@Slf4j
public class RedisClientHandler extends ChannelDuplexHandler {


    // 发送 redis 命令
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
//        String[] commands = ((String) msg).split("\\s+");
//        List<RedisMessage> children = new ArrayList<>(commands.length);
//        for (String cmdString : commands) {
//            children.add(new FullBulkStringRedisMessage(ByteBufUtil.writeUtf8(ctx.alloc(), cmdString)));
//        }
//        RedisMessage request = new ArrayRedisMessage(children);
//        ctx.write(request, promise);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MonitorMasterTask task = new MonitorMasterTask(ctx);
//        ThreadPoolExecutorFactoryUtils.THREAD_POOL_EXECUTOR_MONITOR_MASTER.scheduleAtFixedRate(task,10,10, TimeUnit.SECONDS);
        ctx.executor().scheduleAtFixedRate(task,10,10, TimeUnit.SECONDS);
    }

    // 接收 redis 响应数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        RedisMessage redisMessage = (RedisMessage) msg;

        processRedisMsg(ctx,redisMessage);

        // 是否资源
        ReferenceCountUtil.release(redisMessage);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = "";
            if (event.state() == IdleState.READER_IDLE) {
                type = "read idle";
            }

            System.out.println( ctx.channel().remoteAddress()+"超时类型：" + type);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.print("exceptionCaught: ");
        cause.printStackTrace(System.err);
        ctx.close();
    }

    /**
     * 这里收到的都是响应，需要返回给server那边的channel
     * @param ctx
     * @param msg
     */
    private static void processRedisMsg(ChannelHandlerContext ctx, RedisMessage msg) {
        if (msg instanceof SimpleStringRedisMessage) {
            log.info("{} processRedisMsg:{}",ctx.channel().remoteAddress(),((SimpleStringRedisMessage) msg).content());
        } else if (msg instanceof ErrorRedisMessage) {
            System.out.println(((ErrorRedisMessage) msg).content());
        } else if (msg instanceof IntegerRedisMessage) {
            System.out.println(((IntegerRedisMessage) msg).value());
        } else if (msg instanceof FullBulkStringRedisMessage) {
            BaseCommand command = RedisUtils.getCmdFromMsg(msg);
            CommandProcessor processor = CommandRegistry.getProcessorByCmd(command);
            processor.process(ctx,command);
        } else if (msg instanceof ArrayRedisMessage) {
            BaseCommand command = RedisUtils.getCmdFromMsg(msg);
            CommandProcessor processor = CommandRegistry.getProcessorByCmd(command);
            processor.process(ctx,command);
        } else {
            throw new CodecException("unknown message type: " + msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        NettyClient iNettyClient = ctx.channel().attr(RedisConstants.NETTY_CLIENT_ATTRIBUTE_KEY).get();
        Boolean b = iNettyClient.getIsMaster();
        if (b){
            log.info("I am the master . Im dead.{}",ctx.channel());

            // 这里需要触发重连
            log.info("master channel dead. will reconnect ...");
            ReConnectListenerWhenMasterDown connectListenerWhenMasterDown = new ReConnectListenerWhenMasterDown(iNettyClient);
            iNettyClient.setGenericFutureListener(connectListenerWhenMasterDown);
            iNettyClient.connect();
        }else {
            // 这里需要触发重连
            log.info("slave channel dead. will reconnect ...");
            iNettyClient.connect();
        }
    }


}
