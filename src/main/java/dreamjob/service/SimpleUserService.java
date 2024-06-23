package dreamjob.service;
import dreamjob.model.User;
import dreamjob.repository.UserRepository;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
@ThreadSafe
@Service
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;

    private SimpleUserService(UserRepository sql2oUserRepository) {
        this.userRepository = sql2oUserRepository;
    }

    @Override
    public Optional<User> save(User user) {
       return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public boolean deleteById(int id) {
        return userRepository.deleteById(id);
         }

}