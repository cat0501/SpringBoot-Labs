package cn.iocoder.springboot.lab18.shardingdatasource.mapper;

import cn.iocoder.springboot.lab18.shardingdatasource.Application;
import cn.iocoder.springboot.lab18.shardingdatasource.dataobject.OrderDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;


    // 我们来测试一下简单的 OrderMapper 的每个操作。

    // 明明只有一条 Logic SQL 操作，却发起了 8 条 Actual SQL 操作。这是为什么呢？
    // 我们使用 id = ? 作为查询条件，因为 Sharding-JDBC 解析不到我们配置的 user_id 片键（分库分表字段），作为查询字段，所以只好 全库表路由 ，
    // 查询所有对应的数据节点，也就是配置的所有数据库的数据表。这样，在获得所有查询结果后，通过 归并引擎 合并返回最终结果。

    // 那么，一次性发起这么多条 Actual SQL 是不是会顺序执行，导致很慢呢？
    // 实际上，Sharding-JDBC 有 执行引擎 ，会并行执行这多条 Actual SQL 操作。所以呢，最终操作时长，由最慢的 Actual SQL 所决定。
    // 虽然说，执行引擎 提供了并行执行 Actual SQL 操作的能力，我们还是推荐尽可能查询的时候，带有片键（分库分表字段）。对 Sharding-JDBC 性能感兴趣的胖友，可以看看 《Sharding-JDBC 性能测试报告》 。
    @Test
    public void testSelectById() {
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
    }


    // 一条 Logic SQL 操作，发起了 1 条 Actual SQL 操作。这是为什么呢？
    // 我们使用 user_id = ? 作为查询条件，因为 Sharding-JDBC 解析到我们配置的 user_id 片键（分库分表字段），作为查询字段，所以可以 标准路由 ，仅查询一个数据节点。
    // 这种，是 Sharding-JDBC 最为推荐使用的分片方式。
        //分库：user_id % 2 = 1 % 2 = 1 ，所以路由到 ds-orders-1 数据源。
        //分表：user_id % 8 = 1 % 8 = 1 ，所以路由到 orders_1 数据表。
        //两者一结合，只查询 ds-orders-1.orders_1 数据节点。
    @Test
    public void testSelectListByUserId() {
        List<OrderDO> orders = orderMapper.selectListByUserId(1);
        System.out.println(orders.size());
    }


    // 不考虑 广播表 的情况下，插入语句必须带有片键（分库分表字段），否则 执行引擎 不知道插入到哪个数据库的哪个数据表中。毕竟，插入操作必然是单库单表。
    // 我们会发现，Actual SQL 相比 Logic SQL 来说，增加了主键 id 为 400772257330233345 。
    // 这是为什么呢？我们配置 orders 逻辑表，使用 SNOWFLAKE 算法生成分布式主键，而 改写引擎 在发现我们的 Logic SQL 并未设置插入的 id 主键编号，它会自动生成主键，改写 Logic SQL ，附加 id 成 Logic SQL 。

    /**
     2022-03-22 16:32:44.976  INFO 16761 --- [           main] ShardingSphere-SQL                       : Rule Type: sharding
     2022-03-22 16:32:44.976  INFO 16761 --- [           main] ShardingSphere-SQL                       : Logic SQL: INSERT INTO orders (
        user_id
     ) VALUES (
        ?
     )
     2022-03-22 16:32:44.977  INFO 16761 --- [           main] ShardingSphere-SQL                       : Actual SQL: ds-orders-1 :::
     INSERT INTO orders_1 (
            user_id
     , id) VALUES (?, ?) ::: [1, 713066764895780865]
     */
    @Test
    public void testInsert() {
        OrderDO order = new OrderDO();
        order.setUserId(1);
        orderMapper.insert(order);
    }

    // 至此，我们已经完成了一个 Sharding-JDBC 的简单的分库分表的示例。艿艿建议的话，如果准备应用到项目之前，通读 《ShardingSphere 文档》 。
    // 学习不全面，线上两行泪。
}
