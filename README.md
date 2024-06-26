描述：根据公积金和银行项目开发，由服务编排（physical composition service）控制业务逻辑类（physical basic service）的执行顺序，采用责任链设计模式，分别控制PCS,PBS的前处理和后处理。使用编程式事务控制本地事务，采用saga将PCS长活分解为PBS本地事务，同时实现补偿逻辑。使用SAF（save and forward）实现外呼系统超时，失败时的重发、补偿与恢复。通过JAXB实现xml与java对象的转化，实现flow文件读取。

# modle

## annotation

​ **@AtomicChainStep**:基础服务责任链注解，用于定义调用基础服务时，在服务调用需要运行的类，就是将用该注解注解的类加入基础服务的责任链中​ **@ComposeChainStep**:组合服务责任链注解，用于定义调用组合服务时，在服务调用需要运行的类，就是将用该注解注解的类加入组合服务的责任链中​ **@Field**：输入时添加在类（输入）属性上面，配合数据字典进行数据的校验​ **@PBS**：定义基础服务，一般在接口上注解​ **@PCS**：定义组合服务。暂时未使用，目前交易流程是由PBS组合而成​ **@RouteKey**：定义路由要素属性。暂时不再使用，没想好，不会实现了

## chain

​ 基础服务和组合服务在运行前运行后和出现异常时需要执行的操作采用责任链的设计模式实现，适用@AtomicChainStep控制基础服务责任链节点，@ComposeChainStep控制组合服务责任链节点，实现解耦，后续添加，删除，修改责任节点时只需操作指定类即可，对其他程序无影响。

## configuration

​ 项目的一些配置信息。

## context

​ 请求报文的不同域，如报文头，共享部分。将独属于该请的的信息放入上下文中，采用ThreadLocal实现。

## custinfo

​ 准备用于实现分库分表，目前未实现

## dao

​ 用与基础流水日志，或为后续的开发存储初始信息，基本存储数据库映射类。

## dto

​ 输入DTO,输出DTO，交换区DTO-用于不同类（PBS-基础服务）直接的数据传输

## enumtype

​ 基础枚举类

## filter

​ 通过实现dubbo的Filter接口，使用dubbo的过滤器实现初始请求过滤和初始逻辑处理，在请求正式到达交易入口是对请求进行一些基本处理。

## flow

​ 主要功能是实现flow文件的解析，将flow文件交易的执行顺序和不同的节点解析成实体类存放到Map中，key是请求的交易码，value就是解析好的实体类，flow解析成实体类采用JAXB实现，不同节点对应不同的节点类。

### flowconfig

        项目启动时用来加载flow文件解析相关数据。

### flownode

        flow流程中每个节点对应一个节点类，用来存储flow在解析是解析的不同节点信息，实际上flow就是顺序执行，只不过不同分支节点被一个大的case节点包裹。交易按顺序执行，当执行到这个case节点时，又有case节点处理器来一条一条分支判断解析走哪条。

### handler

        节点处理器。

### init

        初始化配置，比如加载字典文件。

### lock

        锁。

### log

        日志。

### mapper

        数据映射。

### message

        请求报文，响应报文组成。

### process

        服务处理，将服务调用前责任链，服务调用后责任链，服务异常责任链，组合服务调用，基本服务调用整合，使其按顺序执行。

### register

        服务注册中心管理。

### registry

        服务注册管理。

### restservice

        服务入口，对外开放接口。

### route

        路由信息。

### threadpool

        线程池加载一些常用参数，启动时将数据库参数表中的信息加载到不同Map中。

### transaction

        事务管理。基础服务，就是没有和其他系统交互的服务采用编程式事务管理，自己控制提交。外呼服务，就是与其他系统有交互的服务，采用事务拦截器和切面实现，如doTx开头方法，使用REQUIRED事务级别；doNewTx开头方法，使用REQUIRED_NEW事务级别

### unknown

        弃用

### until

        工具类

# PSBC

        业务逻辑存放，数据库交互采用Mybatis，基础服务类中doService方法里实现正常业务逻辑，compensate实现不同类回滚时的业务逻辑，比如A服务成功，B服务失败，这时B的因为本地事务控制，事务回滚，但是A已经提交，这是程序会调用A的compensate，因此在此方法中实现回滚操作。后续考虑SAGA结合TCC使用。
