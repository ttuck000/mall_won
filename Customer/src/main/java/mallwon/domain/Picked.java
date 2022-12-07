package mallwon.domain;

import mallwon.domain.*;
import mallwon.infra.AbstractEvent;
import lombok.*;
import java.util.*;
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
}


