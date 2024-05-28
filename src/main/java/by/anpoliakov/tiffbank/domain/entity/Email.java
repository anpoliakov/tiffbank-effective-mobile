package by.anpoliakov.tiffbank.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "emails")
@Setter
@Getter
@NoArgsConstructor
public class Email {
    @Id
    @Column(name = "email_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    private String email;

    public Email(User user, String email) {
        this.user = user;
        this.email = email;
    }
}
