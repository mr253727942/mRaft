import com.mrraft.remoting.netty.NettyServer;

/**
 * Created by Ma.Rong on 2017/6/28.
 */
public class ServerBootStrap {


    public void init(){
         NettyServer nettyServer = new NettyServer();
         nettyServer.start();

    }


}
