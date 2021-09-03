package me.tvhook.tvwebhook.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public User findById(Long id) {
        User foundUser = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no user"));

//        UserDto foundUserDto = modelMapper.map(foundUser, UserDto.class);
//        log.info("Found User Dto : {}", foundUserDto);

        return foundUser;
    }
}
