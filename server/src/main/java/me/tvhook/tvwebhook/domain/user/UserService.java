package me.tvhook.tvwebhook.domain.user;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    public User findById(Long id) {
        User foundUser = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no user"));

//        UserDto foundUserDto = modelMapper.map(foundUser, UserDto.class);
//        log.info("Found User Dto : {}", foundUserDto);

        return foundUser;
    }

    public List<UserDto> findAll() {
        List<User> results = userRepository.findAll();
        List<UserDto> users = new ArrayList<>();
        results.forEach(user -> {
            users.add(modelMapper.map(user, UserDto.class));
        });

        return users;
    }

    public UserDto createUser(CreateUserRequestDto createUserDto) {
        User user = modelMapper.map(createUserDto, User.class);
        user.setEncryptedPwd(encoder.encode(createUserDto.getPwd()));
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);

    }

    public UserDto getUserDetailsByEmail(String email) {
        User findUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email));

        System.out.println("findUser = " + findUser);

        return modelMapper.map(findUser, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email));

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getEncryptedPwd(),
            true,
            true,
            true,
            true,
            new ArrayList<>()
        );


    }
}
