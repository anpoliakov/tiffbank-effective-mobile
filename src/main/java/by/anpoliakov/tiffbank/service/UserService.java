package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;

    public boolean existsByLogin(@NonNull String login){
        return userRepo.existsByLogin(login);
    }
}
