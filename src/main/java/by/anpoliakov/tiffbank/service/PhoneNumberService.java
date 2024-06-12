package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.domain.dto.PhoneNumberDto;
import by.anpoliakov.tiffbank.domain.entity.PhoneNumber;
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
public class PhoneNumberService {
    private final AuthService authService;
    private final UserRepository userRepo;
    private final PhoneNumberRepository phoneNumberRepo;
    private final EmailRepository emailRepo;

    public boolean existsByPhoneNumber(String phoneNumber){
        return phoneNumberRepo.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    public void addPhoneNumber(PhoneNumberDto phoneNumberDto) {
        String login = authService.getAuthInfo().getUsername();
        User user = userRepo.findByLogin(login).get();
        user.getPhoneNumbers().add(new PhoneNumber(user, phoneNumberDto.getPhoneNumber()));
    }

    @Transactional
    public void deletePhoneNumberById(int id) {
        String login = authService.getAuthInfo().getUsername();
        BigInteger deletePhoneId = new BigInteger(String.valueOf(id));

        if (phoneNumberRepo.existsByUserLoginAndId(login, deletePhoneId)) {
            if (phoneNumberRepo.countByUserLogin(login) > 1) {
                phoneNumberRepo.removeById(deletePhoneId);
            } else {
                throw new UserDataException("You cannot delete the last phone number!");
            }
        } else {
            throw new UserDataException("You can delete only yours number!");
        }
    }

    @Transactional
    public void updatePhoneNumber(int id, PhoneNumberDto newPhoneNumber){
        String login = authService.getAuthInfo().getUsername();
        BigInteger phoneNumberId = new BigInteger(String.valueOf(id));

        if (phoneNumberRepo.existsByUserLoginAndId(login, phoneNumberId)) {
            PhoneNumber phoneNumber = phoneNumberRepo.findById(phoneNumberId).get();
            phoneNumber.setPhoneNumber(newPhoneNumber.getPhoneNumber());
            phoneNumberRepo.save(phoneNumber);

        } else {
            throw new UserDataException("You can update only yours number!");
        }
    }
}
