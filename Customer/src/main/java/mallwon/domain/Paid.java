package mallwon.domain;

import mallwon.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class Paid extends AbstractEvent {

    private Long Id;
    private Long Oder_id;
    private Integer Order_Amount;
    private Date Order_time;
    private Integer Order_qty;
    private String Order_subject;
    private Long Cust_Id;
    private String Cust_Email;
    private Date ApproveTime;
    private String status;
}
