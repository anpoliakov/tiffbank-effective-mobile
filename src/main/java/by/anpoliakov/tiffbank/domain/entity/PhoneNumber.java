package by.anpoliakov.tiffbank.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "phone_numbers")
@Setter
@Getter
@NoArgsConstructor
public class PhoneNumber {
    @Id
    @Column(name = "phone_number_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "phone_number")
    private String phoneNumber;

    public PhoneNumber(User user, String phoneNumber) {
        this.user = user;
        this.phoneNumber = phoneNumber;
    }
}
