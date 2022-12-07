package mallwon.infra;

import mallwon.domain.*;
import mallwon.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderstatusViewHandler {


    @Autowired
    private OrderstatusRepository orderstatusRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPleaced_then_CREATE_1 (@Payload OrderPleaced orderPleaced) {
        try {

            if (!orderPleaced.validate()) return;

            // view 객체 생성
            Orderstatus orderstatus = new Orderstatus();
            // view 객체에 이벤트의 Value 를 set 함
            orderstatus.setId(orderPleaced.getId());
            // view 레파지 토리에 save
            orderstatusRepository.save(orderstatus);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderableArea_then_UPDATE_1(@Payload OrderableArea orderableArea) {
        try {
            if (!orderableArea.validate()) return;
                // view 객체 조회
            Optional<Orderstatus> orderstatusOptional = orderstatusRepository.findById(orderableArea.getOderId());

            if( orderstatusOptional.isPresent()) {
                 Orderstatus orderstatus = orderstatusOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                orderstatus.setId(orderableArea.getId());    
                orderstatus.setStatus("OrderableArea"");    
                // view 레파지 토리에 save
                 orderstatusRepository.save(orderstatus);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaid_then_UPDATE_2(@Payload Paid paid) {
        try {
            if (!paid.validate()) return;
                // view 객체 조회
            Optional<Orderstatus> orderstatusOptional = orderstatusRepository.findById(paid.getOderId());

            if( orderstatusOptional.isPresent()) {
                 Orderstatus orderstatus = orderstatusOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                orderstatus.setId(paid.getId());    
                orderstatus.setStatus("Complete payment");    
                // view 레파지 토리에 save
                 orderstatusRepository.save(orderstatus);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenUnavailableArea_then_DELETE_1(@Payload UnavailableArea unavailableArea) {
        try {
            if (!unavailableArea.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            orderstatusRepository.deleteById(unavailableArea.getOderId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

