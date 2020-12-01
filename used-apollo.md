使用 自定义 配置中心加载配置

# Apollo
## 一、配置
在 resources资源目录 META-INF/services 下新建文件 com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration

增加一行配置代码
> com.whosly.rapid.lang.mask.internals.tacitly.ApolloWatchServiceConfiguration

## 二、配置中心配置
### 2.1 读取  namespace
ApolloWatchServiceConfiguration 中定义的 namespace 为 FX.app。

在 FX.app 下 如果存在key：namespace.lang 的定义，则重定向读取 该 namespace.lang 所指向的 namespce.
```$xslt
    Config config = ConfigService.getConfig("FX.app");
    String ns = config.getProperty("namespace.lang", "");
    if(StringUtils.isNotEmpty(ns)){
        config = ConfigService.getConfig(ns);
    }
```

### 2.2 读取配置
在2.1 配置的  namespace 中，进行key的一系列配置。

包含如下key:

|  key   | 描述  |  |
|  ----  | ----  | ----  |
| mask.to.string.enable.key  | 是否启用配置中心配置。on 启用。off不启用。 该配置用于输出 isBizMonitor 值。 | key 不存在则取默认值 |
| mask.email.fields.key  |  默认打马赛克的email字段 | key 不存在则取默认值 |
| mask.fields.key  | 默认打马赛克的字段 | key 不存在则取默认值 |
|   |  |  |


# Diamond
请参考 ApolloWatchServiceConfiguration 自行定义

