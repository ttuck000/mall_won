package mallwon.domain;

import mallwon.domain.*;
import mallwon.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class Picked extends AbstractEvent {

    private Long id;
    private Long oderId;
    private Integer orderAmount;
    private Date orderTime;
    private Integer orderQty;
    private String orderSubject;
    private Long custId;
    private Date approveTime;
    private String adress;
    private String status;

    public Picked(DeliveryOrderProcessing aggregate){
        super(aggregate);
    }
    public Picked(){
        super();
    }
}
