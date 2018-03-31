package ga.myparser.backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "my_products_id_to_free_run")
public class MyProductIdToFreeRunId implements Serializable {

    @Id
    @Column(name = "sneakers_id")
    private int myProductId;

    @Id
    @Column(name = "free_run_id")
    private int freRunProductId;
}
