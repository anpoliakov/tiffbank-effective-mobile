package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.domain.dto.PhoneNumberRequest;
import by.anpoliakov.tiffbank.domain.entity.PhoneNumber;
import by.anpoliakov.tiffbank.domain.entity.User;
import by.anpoliakov.tiffbank.repository.EmailRepository;
import by.anpoliakov.tiffbank.repository.PhoneNumberRepository;
import by.anpoliakov.tiffbank.repository.UserRepository;
import by.anpoliakov.tiffbank.util.exception.ContactException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhoneNumberService {
    private final AuthService authService;
    private final UserRepository userRepo;
    private final PhoneNumberRepository phoneNumberRepo;
    private final EmailRepository emailRepo;

    public boolean existsByPhoneNumber(String phoneNumber){
        return phoneNumberRepo.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    public void addPhoneNumber(PhoneNumberRequest phoneNumberRequest) {
        String login = authService.getAuthInfo().getUsername();
        User user = userRepo.findByLogin(login).get();
        user.getPhoneNumbers().add(new PhoneNumber(user, phoneNumberRequest.getPhoneNumber()));
    }

    @Transactional
    public void deletePhoneNumberById(int id) {
        String login = authService.getAuthInfo().getUsername();
        BigInteger deletePhoneId = new BigInteger(String.valueOf(id));

        if (phoneNumberRepo.existsByUserLoginAndId(login, deletePhoneId)) {
            if (phoneNumberRepo.countByUserLogin(login) > 1) {
                phoneNumberRepo.removeById(deletePhoneId);
            } else {
                throw new ContactException("You cannot delete the last phone number!");
            }
        } else {
            throw new ContactException("You can delete only yours number!");
        }
    }

    @Transactional
    public void updatePhoneNumber(int id, PhoneNumberRequest newPhoneNumber){
        String login = authService.getAuthInfo().getUsername();
        BigInteger phoneNumberId = new BigInteger(String.valueOf(id));

        if (phoneNumberRepo.existsByUserLoginAndId(login, phoneNumberId)) {
            PhoneNumber phoneNumber = phoneNumberRepo.findById(phoneNumberId).get();
            phoneNumber.setPhoneNumber(newPhoneNumber.getPhoneNumber());
        } else {
            throw new ContactException("You can update only yours number!");
        }
    }
}
