import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author 重新做人idea基础学习
 * @date 2021-4-12
 */
public class zoo1 {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String str="47.101.176.203:2181";
        final CountDownLatch countDownLatch =new CountDownLatch(1);
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState()== Event.KeeperState.SyncConnected){
                    System.out.println("eventype"+watchedEvent.getType());
                    if(watchedEvent.getType()==Event.EventType.None){
                        countDownLatch.countDown();
                    }else if(watchedEvent.getType()==Event.EventType.NodeCreated){
                        System.out.println("node create");
                    }else if(watchedEvent.getType()==Event.EventType.NodeChildrenChanged){
                        System.out.println("child node changed");
                    }
                }
            }
        };

        ZooKeeper zooKeeper =new ZooKeeper(str,5000,watcher);

        zooKeeper.exists("/zkbook",watcher);
//
//        String result = zooKeeper.create("/zkbook/zoo1","SB".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
//
//        System.out.println(result);

        Thread.sleep(10);

        byte[] bs = zooKeeper.getData("/zkbook/zoo1",true,null);
        String result = new String(bs);

        System.out.println(result);
    }
}
