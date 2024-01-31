package in.techcamp.pictweet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUser(UserEntity userEntity){
        String password = userEntity.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity user = new UserEntity();
        user.setUsername(userEntity.getUsername());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}