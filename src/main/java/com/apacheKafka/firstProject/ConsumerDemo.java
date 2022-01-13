package com.apacheKafka.firstProject;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemo {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);
		
		String group_id = "my-javaapplication";
		String topic = "first_topic";
		
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group_id);
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		
		//Create consumer
		KafkaConsumer<String,String>  consumer = new KafkaConsumer<String,String>(properties);
		
		
		//Subscribe out consumer
		consumer.subscribe(Collections.singleton(topic));
		
		//Poll the new data
		while(true) {
			ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100));
			for(ConsumerRecord<String, String> record:  records) {
				logger.info("Key : " + record.key() + " Value: "+ record.value());
				logger.info("Partition : " + record.partition() + " Offset: "+ record.offset());
			}
		}
		
	}
}
