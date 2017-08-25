# rabbitmq_wrap

一、	Rabbitmq  wrap 使用手册
1.	技术选型
RabbitMQ是使用Erlang语言开发的开源消息队列系统，基于AMQP协议来实现。AMQP的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。AMQP协议更多用在企业系统内，对数据一致性、稳定性和可靠性要求很高的场景，对性能和吞吐量的要求还在其次。但高配下，每秒能达到过万的消息消费。Rocketmq的作者说目前业界诞生了两大消息系统，一个是rabbitmq，一个是kafka。RocketMQ思路起源于Kafka，但并不是Kafka的一个Copy，它对消息的可靠传输及事务性做了优化，它是纯Java开发，具有高吞吐量、高可用性、适合大规模分布式系统应用的特点。目前在阿里集团被广泛应用于交易、充值、流计算、消息推送、日志流式处理、binglog分发等场景。Rabbitmq已经能满足公司发展的需求，阿里开发rocketmq是因为他们的业务请求量实在太大了，我们的业务场景并不像阿里，而且为了保证性能和吞吐量，rocketmq对数据一致性、稳定性和可靠性的处理并不一定就比rabbitmq做得好，消息重发等情况都要额外写代码做特殊处理，而且对事务消息和消息消费链路图等，apache上目前并未开源。
没有绝对的好与坏，只是哪个相对更合适。我对rabbitmq的封装，代码层面不用关心你使用的是什么消息系统，消息系统对开发来说透明。并且后续优化对以前版本全部兼容。
注：
构建高可用rabbitmq的运维手册也会近期提供。

2.	消费者开发
直接贴代码：
@Queuename(name="lbb.common.mq.test1")
public class MessageProcessTest implements IMessageProcess<Person> {

	@Override
	public void process(Person person) {
		System.out.println(person.getName());
		System.out.println(person.getBirth());		
        System.out.println("1_Received <" + person + ">");
	}

}
只要这点代码即可，下面详细讲述下：
1)	实现ImessageProcess接口，在process方法体内做消息的业务处理。
2)	加上@Queuename注解，name中写明对应的key(可理解为队列名)，如果程序要消费多个不同的队列，那就再写多个这样的类，实现ImessageProcess<E>。
注: 
	队列名字一定要符合lbb.*.*.*样式 第一个*是应用名，第二个*是模块名,第三个*是方法名,如lbb.erp.contract.create，消息一旦多时，能很好地进行查找或者排序。如果不按这种写法，启动会提示相关错误，不会成功。@Queuename改动要重启程序。
3.	提供者开发
@Autowired
MessageProducer messageProducer;
@Test
public void SendTextTest() {
try {
		messageProducer.sendMessage("lbb.common.mq.test2", person);
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
}
只要这点代码即可，下面详细讲述下：
messageProducer注入，调用sendMessage方法即可。第一个参数队列名(这个队列名跟@Queuename后的名字是一一对应的)，第二个是封闭好后的消息实体。

4.	后续
	Exchange使用了默认的lbb_common_exchange。
目前exchange类型使用的是Direct，即一对一的消息队列，能满足不同系统间异步消息传输的需求。如果在使用过程中，有Topic或者Fanout的使用需求，请联系我持续改进。
	vhost使用了lbb_common_host，作为默认vhost。
	运维事先建立好公用vhost，分配原消息系统用户权限。


