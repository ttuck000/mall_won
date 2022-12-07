package mallwon.domain;

import mallwon.domain.*;
import mallwon.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class OederCanceled extends AbstractEvent {

    private Long id;
    private Long orderId;
    private Date orderTime;
    private Integer orderAmount;
    private Integer orderQty;
    private String orderSubject;
    private Long custId;
    private Date orederTime;
    private String adress;
    private Date cancelTime;
    private String stroreId;
}


