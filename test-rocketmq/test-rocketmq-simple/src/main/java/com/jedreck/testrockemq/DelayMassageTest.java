package com.jedreck.testrockemq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DelayMassageTest {

    public static final String TOPIC = "JedreckSimpleTest";
    public static final String TAG_A = "tagA";
    public static final String TAG_B = "tagB";
    public static final String KEYS_A = "AKey";

    public static final String NAME_SRV_ADDR = "222.2.2.2:9876";
    public static final String PRODUCER_GROUP = "JEDRECK_PRODUCER_GROUP";
    public static final String CONSUMER_GROUP = "JEDRECK_CONSUMER_GROUP";

    /**
     * 1s   5s  10s 30s 1m  2m  3m  4m  5m  6m  7m  8m  9m  10m 20m 30m 1h  2h
     * 1    2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18
     * 现在RocketMq并不支持任意时间的延时，需要设置几个固定的延时等级，从1s到2h分别对应着等级1到18
     */

    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        producer();

//        consumer();
    }

    public static void producer() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // 实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        // 启动生产者
        producer.start();
        int totalMessagesToSend = 10;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message(TOPIC, ("Hello scheduled message " + i).getBytes());
            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(3);

            // 发送消息
            producer.send(message);

            System.out.println("发送第" + i + "条消息");
            TimeUnit.SECONDS.sleep(2L);
        }
        // 关闭生产者
        producer.shutdown();

    }

    public static void consumer() throws MQClientException {
        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        // 订阅Topics
        consumer.subscribe(TOPIC, "*");
        // 注册消息监听者
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                for (MessageExt message : messages) {
                    // Print approximate delay time period
                    System.out.println("Receive message[msgId=" + message.getMsgId() + "] Store " +
                            (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms later; Born " +
                            (System.currentTimeMillis() - message.getBornTimestamp()) + "ms later"
                    );
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者
        consumer.start();
    }
}
