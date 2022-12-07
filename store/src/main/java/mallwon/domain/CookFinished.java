package mallwon.domain;

import mallwon.domain.*;
import mallwon.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class CookFinished extends AbstractEvent {

    private Long id;
    private Long oderId;
    private Integer orderAmount;
    private Date orderTime;
    private Integer orderQty;
    private String orderSubject;
    private Long custId;
    private String custEmail;
    private Date approveTime;
    private String status;

    public CookFinished(FoodCooking aggregate){
        super(aggregate);
    }
    public CookFinished(){
        super();
    }
}
