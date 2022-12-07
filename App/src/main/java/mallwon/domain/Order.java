package mallwon.domain;

import mallwon.domain.OederCanceled;
import mallwon.domain.OrderPleaced;
import mallwon.domain.OrderPleaced;
import mallwon.AppApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Order_table")
@Data

public class Order  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long orderId;
    
    
    
    
    
    private Date orderTime;
    
    
    
    
    
    private Integer orderQty;
    
    
    
    
    
    private String orderSubject;
    
    
    
    
    
    private String status;
    
    
    
    
    
    private Long custId;
    
    
    
    
    
    private String storeId;

    @PostPersist
    public void onPostPersist(){


        OederCanceled oederCanceled = new OederCanceled(this);
        oederCanceled.publishAfterCommit();



        OrderPleaced orderPleaced = new OrderPleaced(this);
        orderPleaced.publishAfterCommit();



        OrderPleaced orderPleaced = new OrderPleaced(this);
        orderPleaced.publishAfterCommit();

    }

    public static OrderRepository repository(){
        OrderRepository orderRepository = AppApplication.applicationContext.getBean(OrderRepository.class);
        return orderRepository;
    }






}
