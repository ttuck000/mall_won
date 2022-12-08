# 예제 - 음식배달 프로세스
참고 템플릿: https://github.com/msa-ez/example-food-delivery

# 서비스 시나리오

기능적 요구사항
1. 고객이 메뉴를 선택하여 주문한다.
1. 고객이 선택한 메뉴에 대해 결제한다.
1. 주문이 되면 주문 내역이 입점상점주인에게 주문정보가 전달된다
1. 상점주는 주문을 수락하거나 거절할 수 있다
1. 상점주는 요리시작때와 완료 시점에 시스템에 상태를 입력한다
1. 고객은 아직 요리가 시작되지 않은 주문은 취소할 수 있다
1. 요리가 완료되면 고객의 지역 인근의 라이더들에 의해 배송건 조회가 가능하다
1. 라이더가 해당 요리를 Pick한 후, 앱을 통해 통보한다.
1. 고객이 주문상태를 중간중간 조회한다
1. 주문상태가 바뀔 때 마다 카톡으로 알림을 보낸다
1. 고객이 요리를 배달 받으면 배송확인 버튼을 탭하여, 모든 거래가 완료된다

비기능적 요구사항
1. 장애격리
    1. 상점관리 기능이 수행되지 않더라도 주문은 365일 24시간 받을 수 있어야 한다  Async (event-driven), Eventual Consistency
    1. 결제시스템이 과중되면 사용자를 잠시동안 받지 않고 결제를 잠시후에 하도록 유도한다  Circuit breaker, fallback
1. 성능
    1. 고객이 자주 상점관리에서 확인할 수 있는 배달상태를 주문시스템(프론트엔드)에서 확인할 수 있어야 한다  CQRS
    1. 배달상태가 바뀔때마다 카톡 등으로 알림을 줄 수 있어야 한다  Event driven


## Model
![image](https://user-images.githubusercontent.com/119825867/206178277-b7a13e5a-b6c4-4baf-9dda-06938f2b34ee.png)


요구사항을 커버하는지 검증

![image](https://user-images.githubusercontent.com/53729857/205814704-19cd22d1-88d2-4860-bc4c-5ad7ab0b02ee.png)
    
    - 고객이 메뉴를 선택하여 주문한다. (ok)
    - 고객이 선택한 메뉴에 대해 결제한다. (ok)
    - 주문이 되면 주문 내역이 입점상점주인에게 주문정보가 전달된다 (ok)
    
![image](https://user-images.githubusercontent.com/53729857/205815658-a7e27991-af7b-44a4-9c76-d732b456a40a.png)

    - 상점주는 주문을 수락하거나 거절할 수 있다 (ok)
    - 상점주는 요리시작때와 완료 시점에 시스템에 상태를 입력한다 (ok)
    - 요리가 완료되면 고객의 지역 인근의 라이더들에 의해 배송건 조회가 가능하다 (ok)
    - 주문상태가 바뀔 때 마다 카톡으로 알림을 보낸다 (ok)
    
![image](https://user-images.githubusercontent.com/53729857/205816589-5c4d93b4-0503-46f9-98bf-d08db232451b.png)

    - 라이더가 해당 요리를 Pick한 후, 앱을 통해 통보한다. (ok)
    
![image](https://user-images.githubusercontent.com/53729857/205816875-45de8307-24b5-4cd8-a969-d7189fb85c7a.png) 
    
    - 고객은 아직 요리가 시작되지 않은 주문은 취소할 수 있다 (ok)
    - 고객이 주문상태를 중간중간 조회한다 (ok)
    - 고객이 요리를 배달 받으면 배송확인 버튼을 탭하여, 모든 거래가 완료된다 (ok)
  

# 체크포인트
## 1. Saga(Pub/Sub)
 
![image](https://user-images.githubusercontent.com/119825867/206378008-7c744ebd-4c12-4f35-9094-b2dfdd71e1d6.png)
cd App 이동 후 하기 명령 실행

http POST http://localhost:8081/orders Order_id=1111 Order_qty=2 Order_subject="치킨" Adress="대림"   실행 

![image](https://user-images.githubusercontent.com/119825867/206378167-effb395b-5c2e-4cc3-96c0-38e390aeab0d.png)

Kafka listener
![image](https://user-images.githubusercontent.com/119825867/206378210-ec26af0b-1c02-4e11-ba6f-e766c82e8d4f.png)


## 2. CQRS 

CQRS를 통해 주문 상태가 변경되는 이벤트가 발생할 때마다 view의 주문 상태를 변경하도록 한다.

![image](https://user-images.githubusercontent.com/119825867/206370057-c324cdbd-8e8f-473f-9d7d-500a4a00c699.png)
![image](https://user-images.githubusercontent.com/119825867/206370076-c660367b-456d-4761-b821-03abd0ad85a1.png)
![image](https://user-images.githubusercontent.com/119825867/206370114-35877283-d6c7-43e3-82e6-6129f710f817.png)

•	Customer -> OrderStatusViewHandler.java에서 이벤트에 따라 Real Model 저장, 업데이트, 삭제를 정의한다.

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
            // view 레퍼지토리에 save
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
            Optional<Orderstatus> orderstatusOptional = orderstatusRepository.findById(orderableArea.getOder_id());

            if( orderstatusOptional.isPresent()) {
                 Orderstatus orderstatus = orderstatusOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                orderstatus.setId(orderableArea.getId());    
                orderstatus.setStatus("OrderableArea");
                // view 레퍼지토리에 save
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
            Optional<Orderstatus> orderstatusOptional = orderstatusRepository.findById(paid.getOder_id());

            if( orderstatusOptional.isPresent()) {
                 Orderstatus orderstatus = orderstatusOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                orderstatus.setId(paid.getId());    
                orderstatus.setStatus("Complete payment");    
                // view 레퍼지토리에 save
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
            // view 레퍼지토리에 삭제 쿼리
            orderstatusRepository.deleteById(unavailableArea.getOder_id());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}



## 3. Compensation / Correlation
- 작성예정

## 4. Request / Response

![image](https://user-images.githubusercontent.com/119825867/206370368-291075ce-44c8-4582-b8b1-fb86b91a312f.png)
![image](https://user-images.githubusercontent.com/119825867/206370406-cbdab36d-34bf-4baf-946b-60334c0cdc1d.png)

## 5. Circuit Breaker
 

## 6. Gateway / Ingress
Application.yml 파일

![image](https://user-images.githubusercontent.com/119825867/206379145-c85bcbae-c29f-4553-aed4-6ce69507a1b8.png)
![image](https://user-images.githubusercontent.com/119825867/206379197-eead2cbe-10ed-4ba2-9909-7908b0f79f4c.png)

------------------------------------------------------------------------------------------------------------

Gateway 서비스 실행 
![image](https://user-images.githubusercontent.com/119825867/206370695-d7ea73d9-741d-4fcb-8711-ba3dd3ee3709.png)
![image](https://user-images.githubusercontent.com/119825867/206370727-d01fa3ef-c9bc-4196-b5e4-18e744dd1f39.png)
![image](https://user-images.githubusercontent.com/119825867/206379287-acbfcff1-a8c4-4269-9a13-36842b3cc498.png)
![image](https://user-images.githubusercontent.com/119825867/206379362-b55b7f0a-a39b-4f52-b53e-ced72bdeb9e3.png)

Gw 기능 확인
![image](https://user-images.githubusercontent.com/119825867/206379425-166eb473-0a65-4ca2-93f9-da95b172ca52.png)


