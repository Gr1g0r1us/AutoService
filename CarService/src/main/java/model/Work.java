package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@ToString(exclude = "listWorks")
@Entity
@Table (name = "Work")
@SequenceGenerator(name = "SEQ_WORK_ID", sequenceName = "SEQ_WORK_ID", allocationSize = 1)
public class Work {
    @Id
    @GeneratedValue(generator = "SEQ_WORK_ID")
    private long id;

    @Column(name = "work_name", length = 50, unique = true, nullable = false)
    private String workName;

    @Column (name = "price", nullable = false, length = 20)
    private String price;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "works", cascade = CascadeType.ALL)
    private List<Contract> listWorks;

    public Work(String workName, String price) {
        this.workName = workName;
        this.price = price;
    }

    public Work(String workName, String price, List<Contract> listWorks) {
        this.workName = workName;
        this.price = price;
        this.listWorks = listWorks;
    }
}
