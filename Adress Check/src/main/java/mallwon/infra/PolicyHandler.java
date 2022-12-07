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
    @Autowired CheckOederAdressRepository checkOederAdressRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderPleaced'")
    public void wheneverOrderPleaced_InputAdress(@Payload OrderPleaced orderPleaced){

        OrderPleaced event = orderPleaced;
        System.out.println("\n\n##### listener InputAdress : " + orderPleaced + "\n\n");


        // Comments // 
		//주무시간 1시간 초과 시 금액 환불
		// 

        // Sample Logic //
        CheckOederAdress.inputAdress(event);
        

        

    }

}


