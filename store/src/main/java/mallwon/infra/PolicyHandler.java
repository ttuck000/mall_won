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
    @Autowired FoodCookingRepository foodCookingRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderPleaced'")
    public void wheneverOrderPleaced_CopyOrderInfo(@Payload OrderPleaced orderPleaced){

        OrderPleaced event = orderPleaced;
        System.out.println("\n\n##### listener CopyOrderInfo : " + orderPleaced + "\n\n");


        

        // Sample Logic //
        FoodCooking.copyOrderInfo(event);
        

        

    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='Paid'")
    public void wheneverPaid_UpdateStatus(@Payload Paid paid){

        Paid event = paid;
        System.out.println("\n\n##### listener UpdateStatus : " + paid + "\n\n");


        

        // Sample Logic //
        FoodCooking.updateStatus(event);
        

        

    }

}


