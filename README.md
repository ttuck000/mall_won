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

![image](https://user-images.githubusercontent.com/53729857/205790895-04938551-3ad8-471c-b8c6-676527a106a7.png)

Reqeust / Response : Pay쪽 서버가 올라오지 않을경우 에러발생.


![image](https://user-images.githubusercontent.com/53729857/205791167-d9b4359b-e816-482a-90bc-3ba348ab5a67.png)

Reqeust / Response : Pay쪽 서버가 정상일 경우만 정상 작동

Pub / Sub : Store쪽 서버가 올라오지 않을 경우에도 올라온 서버안에서 정상작동


## 5. Circuit Breaker
- pay -> Payment.java에 추가
```
    @PrePersist
    public void onPrePersist(){
        if(action.equals("canceled")){
            PaymentCanceled paymentCanceled = new PaymentCanceled();
            BeanUtils.copyProperties(this, paymentCanceled);
            paymentCanceled.publish();
        }else if(action.equals("progress")){
            PaymentApproved paymentApproved = new PaymentApproved();
            BeanUtils.copyProperties(this, paymentApproved);

            // 주문 정보가 커밋된 후에 이벤트 발생시켜야 한다.
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void beforeCommit(boolean readOnly) {
                    paymentApproved.publish();
                }
            });
            
            // 강제 지연
            try {
                Thread.currentThread().sleep((long) (1000 + Math.random() * 220));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else{
            System.out.println("알 수 없는 action");
        }

    }
```
- app -> pom.xml에 추가
```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
		</dependency>
```
- app -> application.yml에 추가
```
feign:
  hystrix:
    enabled: true

hystrix:
  command:
    # 전역설정
    default:
      execution.isolation.thread.timeoutInMilliseconds: 10
```
- app -> PaymentServiceFallBack.java 
```
package mall.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class PaymentServiceFallBack implements PaymentService {
    private static Logger logger = LoggerFactory.getLogger(PaymentServiceFallBack.class);

    @Override
    public void pay(Payment payment) {
        logger.error("Circuit breaker has been opened. Fallback returned instead.");
    }
}
```
- 실행 시 아래의 에러 로그를 app쪽 로그에서 확인가능
![image](https://user-images.githubusercontent.com/53729857/205817063-abf4008f-a96b-4f6f-8c28-b493878baf36.png)

- 과부하 테스트 명령어
![image](https://user-images.githubusercontent.com/53729857/205828449-8838f7cb-3342-48d6-b1e2-de4d36ccc191.png)

- 초기상태
![image](https://user-images.githubusercontent.com/53729857/205828846-d13262ad-8136-46d6-be86-53db3531fde9.png)

- 요청이 점점 밀리는 것을 확인 가능
![image](https://user-images.githubusercontent.com/53729857/205829022-646faa3b-1707-4a8e-9987-85f6d08a9cd3.png)

- 종료
![image](https://user-images.githubusercontent.com/53729857/205829348-11600e6b-6307-4586-9939-695e9e5f56d3.png)

- WAS 로그 - 중간중간 FallBack 로그가 있음
![image](https://user-images.githubusercontent.com/53729857/205829830-ea1edeac-a025-41fe-9d50-92d15ed502d7.png)

## 6. Gateway / Ingress
gateway의 라우터 설정으로 :8081/orders 요청과 :8088/orders 요청이 같은 서비스를 제공한다.
```
spring:
  profiles: default
  cloud:
    gateway:
      routes:
        - id: app
          uri: http://localhost:8081
          predicates:
            - Path=/orders/**, /menus/**, /orderStates/**
```
![image](https://user-images.githubusercontent.com/53729857/205836882-b940a8ac-e567-43c0-b569-4b6adc0aa981.png)
![image](https://user-images.githubusercontent.com/53729857/205836894-5858d3c0-08d5-4bd7-b8b0-7dba84c23b8a.png)



# 기타
## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd kafka
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- app
- store
- customer
- pay
- rider


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- app
```
 http :8088/orders id="id" item="item" qty="qty" price="price" state="state" 
```
- store
```
 http :8088/orderManagements id="id" orderId="orderId" address="address" foodType="foodType" state="state" 
```
- customer
```
```
- pay
```
 http :8088/payments id="id" orderId="orderId" amount="amount" action="action" 
```
- rider
```
 http :8088/deliveries id="id" orderId="orderId" state="state" address="address" 
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
