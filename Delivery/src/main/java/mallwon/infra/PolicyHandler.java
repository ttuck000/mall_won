package mallwon.infra;

import javax.naming.NameParser;

import javax.naming.NameParser;
import javax.transaction.Transactional;

import mallwon.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import mallwon.domain.*;


@Service
@Transactional
public class PolicyHandler{
    @Autowired DeliveryOrderProcessingRepository deliveryOrderProcessingRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderPleaced'")
    public void wheneverOrderPleaced_CopyOrderInfo(@Payload OrderPleaced orderPleaced){

        OrderPleaced event = orderPleaced;
        System.out.println("\n\n##### listener CopyOrderInfo : " + orderPleaced + "\n\n");


        

        // Sample Logic //
        DeliveryOrderProcessing.copyOrderInfo(event);
        

        

    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='CookFinished'")
    public void wheneverCookFinished_UpdateStatus(@Payload CookFinished cookFinished){

        CookFinished event = cookFinished;
        System.out.println("\n\n##### listener UpdateStatus : " + cookFinished + "\n\n");


        

        // Sample Logic //
        DeliveryOrderProcessing.updateStatus(event);
        

        

    }

}


