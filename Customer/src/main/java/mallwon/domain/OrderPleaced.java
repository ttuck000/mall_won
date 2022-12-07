package mallwon.domain;

import mallwon.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class OrderPleaced extends AbstractEvent {

    private Long Id;
    private Long Oder_id;
    private Date Order_time;
    private Integer Order_qty;
    private String Order_subject;
    private Long Cust_Id;
    private String Cust_Email;
}
