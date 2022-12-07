package mallwon.domain;

import mallwon.domain.Delivered;
import mallwon.domain.Deliverytimeout;
import mallwon.domain.OederConfirmOrder;
import mallwon.domain.Picked;
import mallwon.DeliveryApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="DeliveryOrderProcessing_table")
@Data

public class DeliveryOrderProcessing  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private Date orderTime;
    
    
    
    
    
    private Long orderId;
    
    
    
    
    
    private Integer orderAmout;
    
    
    
    
    
    private Integer orderQty;
    
    
    
    
    
    private String orderSubject;
    
    
    
    
    
    private String adress;

    @PostPersist
    public void onPostPersist(){


        Delivered delivered = new Delivered(this);
        delivered.publishAfterCommit();



        Deliverytimeout deliverytimeout = new Deliverytimeout(this);
        deliverytimeout.publishAfterCommit();



        OederConfirmOrder oederConfirmOrder = new OederConfirmOrder(this);
        oederConfirmOrder.publishAfterCommit();



        Picked picked = new Picked(this);
        picked.publishAfterCommit();

    }

    public static DeliveryOrderProcessingRepository repository(){
        DeliveryOrderProcessingRepository deliveryOrderProcessingRepository = DeliveryApplication.applicationContext.getBean(DeliveryOrderProcessingRepository.class);
        return deliveryOrderProcessingRepository;
    }




    public static void copyOrderInfo(OrderPleaced orderPleaced){

        /** Example 1:  new item 
        DeliveryOrderProcessing deliveryOrderProcessing = new DeliveryOrderProcessing();
        repository().save(deliveryOrderProcessing);

        */

        /** Example 2:  finding and process
        
        repository().findById(orderPleaced.get???()).ifPresent(deliveryOrderProcessing->{
            
            deliveryOrderProcessing // do something
            repository().save(deliveryOrderProcessing);


         });
        */

        
    }
    public static void updateStatus(CookFinished cookFinished){

        /** Example 1:  new item 
        DeliveryOrderProcessing deliveryOrderProcessing = new DeliveryOrderProcessing();
        repository().save(deliveryOrderProcessing);

        */

        /** Example 2:  finding and process
        
        repository().findById(cookFinished.get???()).ifPresent(deliveryOrderProcessing->{
            
            deliveryOrderProcessing // do something
            repository().save(deliveryOrderProcessing);


         });
        */

        
    }


}
