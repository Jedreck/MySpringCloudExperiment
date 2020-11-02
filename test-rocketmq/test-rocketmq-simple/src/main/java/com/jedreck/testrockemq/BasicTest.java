package com.jedreck.testrockemq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class BasicTest {
    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException,
            MQBrokerException {

        // 发送同步消息
        syncProducer();

        // 发送异步消息
//        asyncProducer();

        // 单向发送
        onewayProducer();

        // 负载均衡接收
//        basicConsumer();

        // 广播接收
//        broadcastConsumer();
    }

    public static final String TOPIC = "JedreckSimpleTest";
    public static final String TAG_A = "tagA";
    public static final String TAG_B = "tagB";
    public static final String KEYS_A = "AKey";

    public static final String NAME_SRV_ADDR = "222.2.2.2:9876";
    public static final String PRODUCER_GROUP = "JEDRECK_PRODUCER_GROUP";
    public static final String CONSUMER_GROUP = "JEDRECK_CONSUMER_GROUP";

    /**
     * 发送同步消息
     * <p>
     * 这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
     */
    public static void syncProducer() throws MQClientException, RemotingException,
            InterruptedException, MQBrokerException {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        // 设置NameServer的地址
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 10; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message(TOPIC, TAG_A, ("Hello RocketMQ TagAAAAAA " + i).getBytes(StandardCharsets.UTF_8));
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);

            TimeUnit.SECONDS.sleep(2L);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    /**
     * 发送异步消息
     * <p>
     * 异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。
     */

    public static void asyncProducer() throws MQClientException, RemotingException, InterruptedException {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        // 设置NameServer的地址
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        // 启动Producer实例
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 1; i++) {
            final int index = i;
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message(TOPIC, TAG_A, KEYS_A, "Hello world".getBytes(StandardCharsets.UTF_8));
            // SendCallback接收异步返回结果的回调
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    /**
     * 单向发送消息
     * <p>
     * 这种方式主要用在不特别关心发送结果的场景，例如日志发送。
     */
    public static void onewayProducer() throws MQClientException, RemotingException, InterruptedException {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        // 设置NameServer的地址
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 10; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message(TOPIC, TAG_B, ("Hello RocketMQ TagBBBBBB " + i).getBytes(StandardCharsets.UTF_8));
            // 发送单向消息，没有任何返回结果
            producer.sendOneway(msg);

            TimeUnit.SECONDS.sleep(1L);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    /**
     * 负载均衡模式
     * <p>
     * 消费者采用负载均衡方式消费消息，多个消费者共同消费队列消息，每个消费者处理的消息不同
     */
    public static void basicConsumer() throws MQClientException {
        // 实例化消息生产者,指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        // 指定Namesrv地址信息.
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        // 订阅Topic,subExpression 可 “TagA || TagC || TagD”
        consumer.subscribe(TOPIC, TAG_B);

        // ！！！！负载均衡模式消费，默认，消费后其他的消费者无法消费
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 注册回调函数，处理消息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
//            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
            for (MessageExt messageExt : msgs) {
                String messageBody = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                System.out.printf("收到消息：%s %n", messageBody);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //启动消息者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

    /**
     * 广播模式
     * <p>
     * 消费者采用广播的方式消费消息，每个消费者消费的消息都是相同的
     */
    public static void broadcastConsumer() throws MQClientException {
        // 实例化消息生产者,指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        // 指定Namesrv地址信息.
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        // 订阅Topic
        consumer.subscribe(TOPIC, TAG_A + "||" + TAG_B);

        // ###### 广播模式消费,需指定
        consumer.setMessageModel(MessageModel.BROADCASTING);
        // 注册回调函数，处理消息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
//            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
            for (MessageExt messageExt : msgs) {
                String messageBody = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                System.out.printf("收到消息：%s %n", messageBody);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //启动消息者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

}
