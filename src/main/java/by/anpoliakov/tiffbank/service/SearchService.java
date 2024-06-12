package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.domain.dto.UserDetailsResponseDto;
import by.anpoliakov.tiffbank.domain.entity.Email;
import by.anpoliakov.tiffbank.domain.entity.PhoneNumber;
import by.anpoliakov.tiffbank.domain.entity.User;
import by.anpoliakov.tiffbank.mapper.UserMapper;
import by.anpoliakov.tiffbank.repository.EmailRepository;
import by.anpoliakov.tiffbank.repository.PhoneNumberRepository;
import by.anpoliakov.tiffbank.repository.UserRepository;
import by.anpoliakov.tiffbank.util.exception.UserDataException;
import by.anpoliakov.tiffbank.util.exception.SearchException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {
    private final UserRepository userRepo;
    private final PhoneNumberRepository phoneNumberRepo;
    private final EmailRepository emailRepo;
    private final UserMapper userMapper;

    public List<UserDetailsResponseDto> findByBirthday(String birthday) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(birthday, formatter);

        List<User> users = userRepo.findAllByBirthdayGreaterThanOrderByBirthday(date);
        if (users == null)
            throw new UserDataException("No users with birthday greater than the specified value!");

        return userMapper.toListDto(users);
    }

    public List<UserDetailsResponseDto> findByBirthdayWithPagination(String birthday, Integer page, Integer usersPerPage) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(birthday, formatter);

        List<User> users = userRepo.findAllByBirthdayGreaterThan(date, PageRequest.of(page, usersPerPage, Sort.by("birthday")));
        return userMapper.toListDto(users);
    }

    public UserDetailsResponseDto findByPhoneNumber(String phoneNumber) {
        String correctFormatPhoneNumber = "+".concat(phoneNumber);
        PhoneNumber phoneNumberFromDB = phoneNumberRepo.findByPhoneNumber(correctFormatPhoneNumber)
                .orElseThrow(() -> new SearchException("The phone number you are looking for was not found!"));

        return userMapper.toUserDto(phoneNumberFromDB.getUser());
    }

    public List<UserDetailsResponseDto> findByFullName(String fullName) {
        List<User> users = userRepo.findByFullNameStartingWith(fullName);
        if(users == null || users.isEmpty()){
            throw new SearchException("full name not found!");
        }

        return userMapper.toListDto(users);
    }

    public UserDetailsResponseDto findByEmail(String email) {
        Email foundEmail = emailRepo.findByEmail(email).orElseThrow(() -> new SearchException("email not found!"));
        return userMapper.toUserDto(foundEmail.getUser());
    }
}
