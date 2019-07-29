package com.redisbynetty.netty.server;

import com.redisbynetty.utils.NamedThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisServerBootStrap {
	private final static Logger logger = LoggerFactory.getLogger(RedisServerBootStrap.class);

	private final int port;

    private static final NamedThreadFactory namedThreadFactoryForAcceptor = new NamedThreadFactory("netty_acceptor_eventloop");

    private static final NamedThreadFactory namedThreadFactoryForClientChannel = new NamedThreadFactory("netty_client_channel_eventloop");

	public RedisServerBootStrap(int port) {
		this.port = port;
	}


    public void run() {

		ServerBootstrap server = new ServerBootstrap();
        NioEventLoopGroup parentGroup = new NioEventLoopGroup(1,namedThreadFactoryForAcceptor);
        NioEventLoopGroup childGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(),namedThreadFactoryForClientChannel);
		try {
			server.group(parentGroup, childGroup)
					.channel(NioServerSocketChannel.class)
					.localAddress(port)
					.childHandler(new RedisSentinelServerChannelInitializer());

			logger.info("Netty server has started on port : " + port);

			server.bind().sync().channel().closeFuture().sync();
		}catch (Exception e){
		    logger.error("exception:{}",e);

        }
		finally {
            parentGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new RedisServerBootStrap(port).run();
	}
}
