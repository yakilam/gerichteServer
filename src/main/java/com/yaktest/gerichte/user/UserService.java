package com.yaktest.gerichte.user;

import com.yaktest.gerichte.gerichte.Gericht;
import com.yaktest.gerichte.gerichte.GerichtZutat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public Optional<User> singleUser(String userName){
        return userRepository.findUserByUserName(userName);
    }

    public void updateUserZutaten(String userName, List<GerichtZutat> gerichtZutaten){
        userRepository.updateUserZutaten(userName, gerichtZutaten);
    }

    public void updateUserPlan(String userName, List<Gericht> userPlan){
        userRepository.updateUserPlan(userName, userPlan);
    }

}
