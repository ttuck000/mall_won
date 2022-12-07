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
    @Autowired PaymentRepository paymentRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='UnavailableArea'")
    public void wheneverUnavailableArea_PaymentCancel(@Payload UnavailableArea unavailableArea){

        UnavailableArea event = unavailableArea;
        System.out.println("\n\n##### listener PaymentCancel : " + unavailableArea + "\n\n");


        // Comments // 
		//결재취소함

        // Sample Logic //
        Payment.paymentCancel(event);
        

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OederCanceled'")
    public void wheneverOederCanceled_PaymentCancel(@Payload OederCanceled oederCanceled){

        OederCanceled event = oederCanceled;
        System.out.println("\n\n##### listener PaymentCancel : " + oederCanceled + "\n\n");


        // Comments // 
		//결재취소함

        // Sample Logic //
        Payment.paymentCancel(event);
        

        

    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='Deliverytimeout'")
    public void wheneverDeliverytimeout_Paymentrefund(@Payload Deliverytimeout deliverytimeout){

        Deliverytimeout event = deliverytimeout;
        System.out.println("\n\n##### listener Paymentrefund : " + deliverytimeout + "\n\n");


        // Comments // 
		//결재취소함

        // Sample Logic //
        Payment.paymentrefund(event);
        

        

    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderableArea'")
    public void wheneverOrderableArea_Pay(@Payload OrderableArea orderableArea){

        OrderableArea event = orderableArea;
        System.out.println("\n\n##### listener Pay : " + orderableArea + "\n\n");


        

        // Sample Logic //
        Payment.pay(event);
        

        

    }

}


