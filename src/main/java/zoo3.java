import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author 重新做人idea基础学习
 * @date 2021-4-12
 */
public class zoo3 {
    public static void main(String[] args) throws Exception {
        String address= "47.101.176.203:2181";
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(address)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(100,3))
                .build();
        curatorFramework.start();

        PathChildrenCache childrenCache =new PathChildrenCache(curatorFramework,"/zkbook",true);
        childrenCache.start();
        childrenCache.rebuild();
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("node changed "+pathChildrenCacheEvent.getType());
            }
        });


        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/zkbook/zoo3","nmsl".getBytes());
        System.out.println("data --------->"+new String(curatorFramework.getData().forPath("/zkbook/zoo3")));
        curatorFramework.setData().forPath("/zkbook/zoo3","cpdd".getBytes());
        System.out.println("data --------->"+new String(curatorFramework.getData().forPath("/zkbook/zoo3")));











    }
}
