package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = "contracts")
@Entity
@Table (name = "Car")
@SequenceGenerator(name = "SEQ_CAR_ID", sequenceName = "SEQ_CAR_ID", allocationSize = 1)
public class Car {
    @Id
    @GeneratedValue(generator = "SEQ_CAR_ID")
    private long id;

    @Column (name = "reg_number", length = 9, nullable = false)
    private String regNumber;

    @Column (name = "brand", length = 30, nullable = false)
    private String brand;

    @Column (name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column (name = "fio_owner", length = 50, nullable = false)
    private String fioOwner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car", cascade = CascadeType.ALL)
    private List<Contract> contracts;

    public Car(String regNumber, String brand, LocalDate releaseDate, String fioOwner) {
        this.regNumber = regNumber;
        this.brand = brand;
        this.releaseDate = releaseDate;
        this.fioOwner = fioOwner;
    }
}
