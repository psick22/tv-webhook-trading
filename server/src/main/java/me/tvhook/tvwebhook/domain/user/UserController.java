package me.tvhook.tvwebhook.domain.user;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody CreateUserRequestDto user) {

        log.info("create user request body : {}", user);

        UserDto userDto = userService.createUser(user);
        UserResponseDto res = modelMapper.map(userDto, UserResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {

        List<User> results = userRepository.findAll();
        List<UserResponseDto> users = new ArrayList<>();

        for (User user : results) {
            users.add(modelMapper.map(user, UserResponseDto.class));
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable("userId") Long userId
    ) {
        User foundUser = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));

        UserResponseDto result = modelMapper.map(foundUser, UserResponseDto.class);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> update(@PathVariable("userId") Long userId,
        @RequestBody UpdateUserRequestDto condition) {
        User foundUser = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));
        if (condition.getAllocatedKrw() != null) {
            foundUser.setAllocatedKrw(condition.getAllocatedKrw());
        }
        if (condition.getUpbitApiKey() != null) {
            foundUser.setUpbitApiKey(condition.getUpbitApiKey());
        }
        if (condition.getUpbitSecretKey() != null) {
            foundUser.setUpbitSecretKey(condition.getUpbitSecretKey());
        }
        userRepository.save(foundUser);

        UserResponseDto result = modelMapper.map(foundUser, UserResponseDto.class);
        return ResponseEntity.ok(result);
    }

}
