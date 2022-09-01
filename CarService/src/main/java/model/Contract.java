package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Console;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@ToString(exclude = "listWorks")
@Entity
@Table (name = "Contract")
@SequenceGenerator(name = "SEQ_CONTRACT_ID", sequenceName = "SEQ_CONTRACT_ID", allocationSize = 1)
public class Contract {
    @Id
    @GeneratedValue(generator = "SEQ_CONTRACT_ID")
    private long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column (name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column (name = "fio_customer", nullable = false)
    private String fioCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Work_list",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "work_id")
    )
    private List<Work> works;

    public Contract(LocalDate startDate, LocalDate endDate, String fioCustomer, Car car) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.fioCustomer = fioCustomer;
        this.car = car;
    }

    public Contract(LocalDate startDate, LocalDate endDate, String fioCustomer, Car car, List<Work> works) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.fioCustomer = fioCustomer;
        this.car = car;
        this.works = works;
    }

    public Contract(long id, LocalDate startDate, LocalDate endDate, String fioCustomer, Car car) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fioCustomer = fioCustomer;
        this.car = car;
    }

    public String printWorks() {
        return getWorks().stream().map(p -> p.getWorkName()).collect(Collectors.joining("; "));
    }
}
