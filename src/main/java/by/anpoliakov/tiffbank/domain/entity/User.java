package by.anpoliakov.tiffbank.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    private String fullName;

    @Column(name = "birthday")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Email> emails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;

    public User(String login, String password, String fullName, LocalDate birthday) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.birthday = birthday;
    }
}
