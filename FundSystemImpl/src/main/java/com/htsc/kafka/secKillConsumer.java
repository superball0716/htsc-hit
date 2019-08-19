//package com.htsc.kafka;
//
//import java.util.Optional;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//import com.alibaba.fastjson.JSON;
//import com.htsc.entity.Fund;
//import com.htsc.entity.secKillResult;
//import com.htsc.mapper.FundMapper;
//import com.htsc.utils.CacheUtils;
//
//
//
//@Component
//public class secKillConsumer {
//	private static final Logger log = LoggerFactory.getLogger(secKillConsumer.class);
//	
//	@Autowired
//	FundMapper pm;
//    
//    @KafkaListener(topics = {"secKill"})
//    public void listen(ConsumerRecord<?, ?> record) {
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//        if (kafkaMessage.isPresent()) {
//            Object message = kafkaMessage.get();
//            long offset = record.offset();
//            log.info("num:{}",record.serializedKeySize());
//            int partitionId = record.partition();
//            
//            log.info("message class:{}",message.getClass());
//            secKillResult skm = JSON.parseObject((String)message, secKillResult.class);
//            int pId = skm.getpId();
//            
//            //TODO 判断缓存中是否还有库存
//            Fund p = pm.getProductBypId(pId);
//            if (p.getProductnum() > 0) {
//            	int version_id = pm.getVersionId(pId);
//            	boolean updated = pm.decreseProductByLock(pId, version_id);
//            	if(updated) {
//            		//TODO 加入交易记录表
//            		
//                    CacheUtils.saveToSet(String.valueOf(skm.getpId()), String.valueOf(skm.getuId()));
//                    
//                    //TODO 更新缓存中库存
//            	}
//            	
//            }
//            //log.info("----------------- record =topic："  + topic+ ", " + record);
//            log.info("message={}, offset={}, partitionId={}", message, offset, partitionId);
//        }
//    }
//
//
//}
