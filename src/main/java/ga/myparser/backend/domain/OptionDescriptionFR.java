package ga.myparser.backend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "oc_option_description")
public class OptionDescriptionFR {

    @Id
    @NonNull
    @Column(name = "option_id")
    private int optionId;

    @NonNull
    @Column(name = "name")
    private String name;
}
