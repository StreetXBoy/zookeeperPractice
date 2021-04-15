import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import sun.text.resources.cldr.ii.FormatData_ii;

import java.util.List;

/**
 * @author 重新做人idea基础学习
 * @date 2021-4-12
 */
public class zoo2 {
    public static void main(String[] args) throws InterruptedException {
        String address= "47.101.176.203:2181";
        ZkClient zkClient = new ZkClient(address);

        //监听节点数值变化为当前节点！！！！
        zkClient.subscribeDataChanges("/zkbook/zoo2", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("data change "+ s+"     "+o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("date delete"+s);
            }
        });

      //监听子节点变化为上一级变化!!!!!!
        zkClient.subscribeChildChanges("/zkbook", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("child node changed"+s);
                list.forEach(f->{
                    System.out.println("content: "+f);
                });
            }
        });


        List<String> list = zkClient.getChildren("/");
        list.forEach(System.out::println);

        for (int i = 0; i <10 ; i++) {
            zkClient.create("/zkbook/zoo2","fuck", CreateMode.PERSISTENT);
            zkClient.writeData("/zkbook/zoo2","rinima");
            Thread.sleep(100);
            zkClient.delete("/zkbook/zoo2");
            Thread.sleep(100);
        }

    }
}
