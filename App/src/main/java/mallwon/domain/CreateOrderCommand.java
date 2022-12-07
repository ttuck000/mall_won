package mallwon.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;

@Data
public class CreateOrderCommand {

        private String adress;
        private Integer orderQty;
        private Long orderId;
        private String orderSubject;


}
