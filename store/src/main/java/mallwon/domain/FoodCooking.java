package mallwon.domain;

import mallwon.domain.CookFinished;
import mallwon.domain.CookStarted;
import mallwon.domain.OrderAccepted;
import mallwon.domain.OrderRejected;
import mallwon.StoreApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="FoodCooking_table")
@Data

public class FoodCooking  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private Date orderTime;
    
    
    
    
    
    private Long orderId;
    
    
    
    
    
    private Integer orderAmout;
    
    
    
    
    
    private Integer orderQty;
    
    
    
    
    
    private String orderSubject;
    
    
    
    
    
    private Date approveTime;
    
    
    
    
    
    private Long custId;
    
    
    
    
    
    private String adress;

    @PostPersist
    public void onPostPersist(){


        CookFinished cookFinished = new CookFinished(this);
        cookFinished.publishAfterCommit();



        CookStarted cookStarted = new CookStarted(this);
        cookStarted.publishAfterCommit();



        OrderAccepted orderAccepted = new OrderAccepted(this);
        orderAccepted.publishAfterCommit();



        OrderRejected orderRejected = new OrderRejected(this);
        orderRejected.publishAfterCommit();

    }

    public static FoodCookingRepository repository(){
        FoodCookingRepository foodCookingRepository = StoreApplication.applicationContext.getBean(FoodCookingRepository.class);
        return foodCookingRepository;
    }



    public void acceptOrder(AcceptOrderCommand acceptOrderCommand){
    }

    public static void copyOrderInfo(OrderPleaced orderPleaced){

        /** Example 1:  new item 
        FoodCooking foodCooking = new FoodCooking();
        repository().save(foodCooking);

        OrderAccepted orderAccepted = new OrderAccepted(foodCooking);
        orderAccepted.publishAfterCommit();
        OrderRejected orderRejected = new OrderRejected(foodCooking);
        orderRejected.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(orderPleaced.get???()).ifPresent(foodCooking->{
            
            foodCooking // do something
            repository().save(foodCooking);

            OrderAccepted orderAccepted = new OrderAccepted(foodCooking);
            orderAccepted.publishAfterCommit();
            OrderRejected orderRejected = new OrderRejected(foodCooking);
            orderRejected.publishAfterCommit();

         });
        */

        
    }
    public static void updateStatus(Paid paid){

        /** Example 1:  new item 
        FoodCooking foodCooking = new FoodCooking();
        repository().save(foodCooking);

        */

        /** Example 2:  finding and process
        
        repository().findById(paid.get???()).ifPresent(foodCooking->{
            
            foodCooking // do something
            repository().save(foodCooking);


         });
        */

        
    }


}
