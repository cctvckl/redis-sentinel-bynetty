# redis-sentinel-bynetty
use java netty to implement redis sentinel

本项目的目的是使用java netty框架，实现redis sentinel的主要功能。如果了解redis sentinel的同学可能知道，redis sentinel其实是一个特殊的redis 服务器，只是其能支持的命令和redis server服务不同，举个例子就是，redis sentinel在检测到 master server挂掉后，判定为主观宕机，需要向其他redis sentinel发消息确认，此时发送的命令就是 is-master-down-by-addr,而普通的redis server是不支持这个命令的。

当前提交的版本，已经完成了简略的redis server的功能。

如何使用：
1、启动服务端
com.redisbynetty.netty.server.RedisServerBootStrap 启动即可
此时，会在本机的8080端口进行监听，当然，端口自己可改

2、使用默认安装redis时的redis-cli工具，cmd中连接我们的服务即可：
redis-cli.exe -h localhost -p 8080

目前支持2个命令：

1、set key value

2、get key
