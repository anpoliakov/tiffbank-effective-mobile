package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.domain.dto.EmailDto;
import by.anpoliakov.tiffbank.domain.entity.Email;
import by.anpoliakov.tiffbank.domain.entity.User;
import by.anpoliakov.tiffbank.repository.EmailRepository;
import by.anpoliakov.tiffbank.repository.PhoneNumberRepository;
import by.anpoliakov.tiffbank.repository.UserRepository;
import by.anpoliakov.tiffbank.util.exception.UserDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {
    private final AuthService authService;
    private final UserRepository userRepo;
    private final PhoneNumberRepository phoneNumberRepo;
    private final EmailRepository emailRepo;

    public boolean existsByEmail(String email){
        return emailRepo.existsByEmail(email);
    }

    @Transactional
    public void addEmail(EmailDto emailDto) {
        String login = authService.getAuthInfo().getUsername();
        User user = userRepo.findByLogin(login).get();
        user.getEmails().add(new Email(user, emailDto.getEmail()));
    }

    @Transactional
    public void deleteEmailById(int id) {
        String login = authService.getAuthInfo().getUsername();
        BigInteger deleteEmailId = new BigInteger(String.valueOf(id));

        if (emailRepo.existsByUserLoginAndId(login, deleteEmailId)) {
            if (emailRepo.countByUserLogin(login) > 1) {
                emailRepo.removeById(deleteEmailId);
            } else {
                throw new UserDataException("You cannot delete the last email!");
            }
        } else {
            throw new UserDataException("You can delete only yours email!");
        }
    }

    @Transactional
    public void updateEmail(int id, EmailDto newEmail){
        String login = authService.getAuthInfo().getUsername();
        BigInteger emailId = new BigInteger(String.valueOf(id));

        if (emailRepo.existsByUserLoginAndId(login, emailId)) {
            Email email = emailRepo.findById(emailId).get();
            email.setEmail(newEmail.getEmail());
        } else {
            throw new UserDataException("You can update only yours email!");
        }
    }
}
