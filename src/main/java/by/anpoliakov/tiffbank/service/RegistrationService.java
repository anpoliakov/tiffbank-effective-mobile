package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.domain.dto.UserDetailsRequestDto;
import by.anpoliakov.tiffbank.domain.entity.Account;
import by.anpoliakov.tiffbank.domain.entity.Email;
import by.anpoliakov.tiffbank.domain.entity.PhoneNumber;
import by.anpoliakov.tiffbank.domain.entity.User;
import by.anpoliakov.tiffbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveUserData(UserDetailsRequestDto dto){
        User user = new User(
                dto.getLogin(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getFullName(),
                dto.getBirthday(),
                dto.getBalance());

        user.setEmails(Collections.singletonList(new Email(user, dto.getEmail())));
        user.setPhoneNumbers(Collections.singletonList(new PhoneNumber(user, dto.getPhoneNumber())));
        user.setAccount(new Account(user, dto.getBalance()));

        userRepo.save(user);
    }
}
