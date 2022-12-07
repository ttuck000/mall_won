package mallwon.domain;

import mallwon.domain.*;
import mallwon.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderPleaced extends AbstractEvent {

    private Long id;
    private Long oderId;
    private Date orderTime;
    private Integer orderAmount;
    private Integer orderQty;
    private String orderSubject;
    private Long custId;
    private String status;
    private String stroreId;

    public OrderPleaced(Order aggregate){
        super(aggregate);
    }
    public OrderPleaced(){
        super();
    }
}
