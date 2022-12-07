package mallwon.domain;

import mallwon.domain.*;
import mallwon.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderableArea extends AbstractEvent {

    private Long id;
    private Long oderId;
    private String adress;
    private Long custId;
    private String status;
    private String stroreId;

    public OrderableArea(CheckOederAdress aggregate){
        super(aggregate);
    }
    public OrderableArea(){
        super();
    }
}
