package org.fuqinqin.code.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class ZookeeperTest {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 30000, new TestWatcher());
        String node = "/node2";
        Stat stat = zooKeeper.exists(node, false);
        if(stat == null){
            //创建节点
            String createResult = zooKeeper.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
        }

        byte[] b = zooKeeper.getData(node, false, stat);
        System.out.println(new String(b));
        zooKeeper.close();

    }

}
