[![License](https://img.shields.io/badge/license-MIT-blue.svg)](http://opensource.org/licenses/MIT)

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/aoheng/aoheng-shardingjdbc)

[![GitHub stars](https://img.shields.io/github/stars/aoheng/PlantUML.svg?style=social&label=Stars)](https://github.com/aoheng/aoheng-shardingjdbc)

[![GitHub forks](https://img.shields.io/github/forks/aoheng/PlantUML.svg?style=social&label=Fork)](https://github.com/aoheng/aoheng-shardingjdbc)

# aoheng-shardingjdbc

- 参考snowalker-shardingjdbc分库分表实战

- 复合分片规则：user_id,order_id

- 分片策略：复合分片策略-ComplexKeysShardingAlgorithm

- 整合mybatis-plus



## 了解Sharding-JDBC的数据分片策略

Sharding-JDBC的分片策略包含了分片键和分片算法。由于分片算法与业务实现紧密相关，因此Sharding-JDBC没有提供内置的分片算法，而是通过分片策略将各种场景提炼出来，提供了高层级的抽象，通过提供接口让开发者自行实现分片算法。

以下内容引用自官方文档。[官方文档](https://shardingsphere.apache.org/document/legacy/3.x/document/cn/features/sharding/concept/sharding/)

## **四种分片算法**

1. 通过分片算法将数据分片，支持通过=、BETWEEN和IN分片。 分片算法需要应用方开发者自行实现，可实现的灵活度非常高。
2. 目前提供4种分片算法。由于分片算法和业务实现紧密相关， 因此并未提供内置分片算法，而是通过分片策略将各种场景提炼出来， 提供更高层级的抽象，并提供接口让应用开发者自行实现分片算法。

### 精确分片算法–PreciseShardingAlgorithm

用于处理使用单一键作为分片键的=与IN进行分片的场景。需要配合StandardShardingStrategy使用。

### 范围分片算法–RangeShardingAlgorithm

用于处理使用单一键作为分片键的BETWEEN AND进行分片的场景。需要配合StandardShardingStrategy使用。

### 复合分片算法–ComplexKeysShardingAlgorithm

用于处理使用多键作为分片键进行分片的场景，包含多个分片键的逻辑较复杂，需要应用开发者自行处理其中的复杂度。需要配合ComplexShardingStrategy使用。

注 ： 我们在业务开发中，经常有根据用户id
查询某用户的记录列表，又有根据某个业务主键查询该用户的某记录的需求，这就需要用到复合分片算法。比如，订单表中，我们既需要查询某个userId的某时间段内的订单列表数据，又需要根据orderId查询某条订单数据。这里，orderId与userId就属于复合分片键。

### Hint分片算法–HintShardingAlgorithm

Hint分片指的是对于分片字段非SQL决定，而由其他外置条件决定的场景，可以通过使用SQL Hint灵活注入分片字段。

Hint分片策略是绕过SQL解析的，因此能够通过实现该算法来实现Sharding-JDBC不支持的语法限制。

用于处理使用Hint行分片的场景。需要配合HintShardingStrategy使用。

## 五种分片策略

### 标准分片策略–StandardShardingStrategy

提供对SQL语句中的=, IN和BETWEEN
AND的分片操作支持。StandardShardingStrategy只支持单分片键，提供PreciseShardingAlgorithm和RangeShardingAlgorithm两个分片算法。PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。RangeShardingAlgorithm是可选的，用于处理BETWEEN
AND分片，如果不配置RangeShardingAlgorithm，SQL中的BETWEEN AND将按照全库路由处理。

### 复合分片策略–ComplexShardingStrategy

提供对SQL语句中的=, IN和BETWEEN
AND的分片操作支持。ComplexShardingStrategy支持多分片键，由于多分片键之间的关系复杂，因此并未进行过多的封装，而是直接将分片键值组合以及分片操作符透传至分片算法，完全由应用开发者实现，提供最大的灵活度。

这里体现出框架设计者对设计原则的透彻理解，将变更点暴露给用户，将不变的封装在内部，明确的划分了抽象和实现的界限，这是值得我们学习的。

### 行表达式分片策略–InlineShardingStrategy

使用Groovy的表达式，提供对SQL语句中的=和IN的分片操作支持，只支持单分片键。对于简单的分片算法，可以通过简单的配置使用，从而避免繁琐的Java代码开发，如: tuser$->{u_id % 8}
表示t_user表根据u_id模8，而分成8张表，表名称为t_user_0到t_user_7。

上一篇文章中，我就是使用这个方式进行了demo的开发和讲解，对于快速体验Sharding-JDBC的魅力是很有意义的，但是这种方式对于复杂的业务支持程度就差一些，因此实际的业务开发中还是推荐使用复合分片策略–ComplexShardingStrategy。

### Hint分片策略–HintShardingStrategy

通过Hint而非SQL解析的方式分片的策略。

### 不分片策略–NoneShardingStrategy

该策略为不分片的策略。

## 关联介绍

> shardingjdbc3.x 整合，实现自定义主键生成，自定义分库分表策略，可扩展，可读性更好

| 项目关联文章列表                                             |
| :----------------------------------------------------------- |
| [我说分布式之分库分表](http://wuwenliang.net/2019/03/11/%E6%88%91%E8%AF%B4%E5%88%86%E5%B8%83%E5%BC%8F%E4%B9%8B%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8/) |
| [跟我学shardingjdbc之shardingjdbc入门](http://wuwenliang.net/2019/03/12/%E8%B7%9F%E6%88%91%E5%AD%A6shardingjdbc%E4%B9%8Bshardingjdbc%E5%85%A5%E9%97%A8/) |
| [跟我学shardingjdbc之使用jasypt加密数据库连接密码](http://wuwenliang.net/2019/03/14/%E8%B7%9F%E6%88%91%E5%AD%A6shardingjdbc%E4%B9%8B%E4%BD%BF%E7%94%A8jasypt%E5%8A%A0%E5%AF%86%E6%95%B0%E6%8D%AE%E5%BA%93%E8%BF%9E%E6%8E%A5%E5%AF%86%E7%A0%81/) |
| [跟我学shardingjdbc之分布式主键及其自定义](http://wuwenliang.net/2019/03/25/%E8%B7%9F%E6%88%91%E5%AD%A6shardingjdbc%E4%B9%8B%E5%88%86%E5%B8%83%E5%BC%8F%E4%B8%BB%E9%94%AE%E5%8F%8A%E5%85%B6%E8%87%AA%E5%AE%9A%E4%B9%89/#qrcode) |
| [跟我学shardingjdbc之自定义分库分表策略-复合分片算法自定义实现](http://wuwenliang.net/2019/03/26/%E8%B7%9F%E6%88%91%E5%AD%A6shardingjdbc%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8%E7%AD%96%E7%95%A5-%E5%A4%8D%E5%90%88%E5%88%86%E7%89%87%E7%AE%97%E6%B3%95%E8%87%AA%E5%AE%9A%E4%B9%89%E5%AE%9E%E7%8E%B0/#qrcode) |

官方参考资料：https://github.com/TaXueWWL/snowalker-shardingjdbc-demo
