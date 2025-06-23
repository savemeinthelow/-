畅行通-高并发在线售票平台
本项目实现了仿12306理念的本地在线票务系统的核心功能模块，包括车票查询、订单管理、支付结算、分布式锁、缓存优化、分库分表等。系统日均支持千万级用户访问，峰值QPS达10万+，具备高并发、高可用、高扩展性等特点。通过引入分布式架构、缓存优化、消息队列等技术，解决了高并发场景下的性能瓶颈与数据一致性问题，确保系统稳定运行。

- 后端技术： SpringBoot、SpringCloud、MyBatis-Plus、RocketMQ、XXL-JOB、Hystrix/Sentinel、ShardingSphere
- 数据库： MySQL、Redis、Elasticsearch
- 缓存与消息队列： Redis、RocketMQ
- 分布式技术： 分布式ID（雪花算法）、分布式锁（Redisson）、分布式事务（Seata）
- 监控与运维： SkyWalking、Prometheus、Grafana
- 其他工具： Hutool、Lombok、Swagger
