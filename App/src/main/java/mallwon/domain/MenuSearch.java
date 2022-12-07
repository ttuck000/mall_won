package mallwon.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;

@Entity
@Table(name="MenuSearch_table")
@Data
public class MenuSearch {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;


}