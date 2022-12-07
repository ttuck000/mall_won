package mallwon.domain;

import mallwon.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class OrderableArea extends AbstractEvent {

    private Long Id;
    private Long Oder_id;
    private String Adress;
    private Long Cust_Id;
    private String status;
    private String StroreId;
}
