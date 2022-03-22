package cn.iocoder.springboot.lab18.shardingdatasource.mapper;

import cn.iocoder.springboot.lab18.shardingdatasource.Application;
import cn.iocoder.springboot.lab18.shardingdatasource.dataobject.OrderConfigDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderConfigMapperTest {

    @Autowired
    private OrderConfigMapper orderConfigMapper;

    // 我们来测试一下简单的 OrderConfigMapper 的每个操作。
    @Test
    public void testSelectById() {
        OrderConfigDO orderConfig = orderConfigMapper.selectById(1);
        System.out.println(orderConfig);
    }

    /** 执行日志如下：
     * Logic SQL ：逻辑 SQL 日志，就是我们编写的。
     * Actual SQL ：物理 SQL 日志，实际 Sharding-JDBC 向数据库真正发起的日志。
     * - 在这里，我们可以看到 ds-orders-0 ，表名该物理 SQL ，是路由到 ds-orders-0 数据源执行。
     * - 同时，查询的是 order_config 表。
     * - 符合我们配置的 order_config 逻辑表，不使用分库分表，对应的数据节点仅有 ds-orders-0.order_config

     2022-03-22 16:21:06.696  INFO 16281 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT

     id, pay_timeout

     FROM order_config
     WHERE id = ?

     2022-03-22 16:21:06.696  INFO 16281 --- [           main] ShardingSphere-SQL                       : Actual SQL: ds-orders-0 ::: SELECT

     id, pay_timeout

     FROM order_config
     WHERE id = ? ::: [1]
     null
     */

}
