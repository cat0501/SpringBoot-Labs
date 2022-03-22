package cn.iocoder.springboot.lab18.shardingdatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 本小节，我们会使用 Sharding-JDBC 实现分库分表的功能。

/**  我们会将 orders 订单表，拆分到 2 个库，每个库 4 个订单表，一共 8 个表。库表的情况如下：
 * 偶数后缀的表，在 lab18_orders_0 库下。
 * 奇数后缀的表，在 lab18_orders_1 库下。

我们使用订单表上的 user_id 用户编号，进行分库分表的规则：

首先，按照 index = user_id % 2 计算，将记录路由到 lab18_orders_${index} 库。
然后，按照 index = user_id % 8 计算，将记录路由到 orders_${index} 表。

 lab18_orders_0 库
 ├── orders_0
 └── orders_2
 └── orders_4
 └── orders_6
 lab18_orders_1 库
 ├── orders_1
 └── orders_3
 └── orders_5
 └── orders_7

 考虑到部分表不需要分库分表，例如说 order_config 订单配置表，所以我们会配置路由到 lab18_orders_0 库下。

 具体 orders 和 order_config 两个表的创建语句，我们在 TODO 提供。

 因为本文重心在于提供示例。胖友可以碰到不理解的地方，看看如下文档：

 《ShardingSphere > 概念 & 功能 > 数据分片》
 《ShardingSphere > 用户手册 > Sharding-JDBC > 使用手册 > 数据分片》
 《ShardingSphere > 用户手册 > Sharding-JDBC > 配置手册》
 */


//添加 @MapperScan 注解，cn.iocoder.springboot.lab18.shardingdatasource.mapper 包路径下，就是我们 Mapper 接口所在的包路径。
@SpringBootApplication
@MapperScan(basePackages = "cn.iocoder.springboot.lab18.shardingdatasource.mapper")
public class Application {
}
