package com.apacheKafka.firstProject;

import java.util.Properties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemo {

	public static void main(String[] args) {
		
	    final Logger logger = LoggerFactory.getLogger(ProducerDemo.class);
	    
		//Create producer properties
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		
		//Declaring Variables
		String topic = "first_topic";
		
		
		//Create producer
		KafkaProducer<String,String> producer = new KafkaProducer<String,String>(properties);
				
		for(int i=0;i<10;i++) {
			String message = "M " + Integer.toString(i);
			String Key = "id_"+ Integer.toString(i);
			
			//Create a Producer record
			ProducerRecord<String,String> record = new ProducerRecord<String,String>(topic,Key,message);
			
			//Send data to kafka - this is a asynchrous 
			logger.info("Key is : " + Key);
			
			producer.send(record, new Callback() {
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if(exception == null) {
							logger.info("Received new Metadata. \n" +
									"Topic:"+ metadata.topic() +"\n"+
									"Partition:"+ metadata.partition() + "\n"+
									"Offsets:"+ metadata.offset() + "\n" + 
									"Timestamp: "+ metadata.timestamp());
					}else {
						logger.error("Error while producing: "+ exception);
					}
				}
	
					
				});
		}
		
		//Closing the Object
		producer.close();
	}

}
