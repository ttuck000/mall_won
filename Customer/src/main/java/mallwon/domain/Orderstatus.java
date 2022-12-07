package mallwon.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;

@Entity
@Table(name="Orderstatus_table")
@Data
public class Orderstatus {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private String status;


}