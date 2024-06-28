package ru.michaelthecircle.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.michaelthecircle.spring.entities.User;
import ru.michaelthecircle.spring.repositories.RoleRepository;
import ru.michaelthecircle.spring.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("Пользователь " + username + " не найден!")); //String.format можно использовать
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
    public void createNewUser(User user) {
        //TODO::проверить что такого пользователя не существует и кидать исключение
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER").get())); //еще проверить что такая роль существует
        userRepository.save(user);
    }
}
