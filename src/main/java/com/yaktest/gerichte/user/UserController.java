package com.yaktest.gerichte.user;

import com.yaktest.gerichte.gerichte.Gericht;
import com.yaktest.gerichte.gerichte.GerichtZutat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

    @GetMapping("/api/v1/users/{userName}")
    public ResponseEntity<Optional<User>> getSingleUser(@PathVariable String userName){
        return new ResponseEntity<Optional<User>>(userService.singleUser(userName), HttpStatus.OK);
    }

    @GetMapping("/my/my")
    public ResponseEntity<Optional<UserResponse>> getMyUser(Principal principal){
        String userName = principal.getName();
        Optional<User> user = userService.singleUser(userName);
        UserResponse userResponse = new UserResponse(user.get().getUsername(), user.get().getRole().toString());
        return new ResponseEntity<Optional<UserResponse>>(Optional.of(userResponse), HttpStatus.OK);
    }

    @GetMapping("/my/myZutaten")
    public ResponseEntity<Optional<List<GerichtZutat>>> getMyZutaten(Principal principal){
        String userName = principal.getName();
        Optional<User> user = userService.singleUser(userName);
        return new ResponseEntity<Optional<List<GerichtZutat>>>(Optional.of(user.get().getZutaten()), HttpStatus.OK);
    }

    @PostMapping("/my/myZutaten")
    public ResponseEntity<Optional<List<GerichtZutat>>> updateMyZutaten(@RequestBody List<GerichtZutat> gerichtZutaten, Principal principal){
        String userName = principal.getName();
        userService.updateUserZutaten(userName, gerichtZutaten);
        Optional<User> user = userService.singleUser(userName);
        return new ResponseEntity<Optional<List<GerichtZutat>>>(Optional.of(user.get().getZutaten()), HttpStatus.OK);
    }

    @GetMapping("/my/myPlan")
    public ResponseEntity<Optional<List<Gericht>>> getMyPlan(Principal principal){
        String userName = principal.getName();
        Optional<User> user = userService.singleUser(userName);
        return new ResponseEntity<Optional<List<Gericht>>>(Optional.of(user.get().getUserPlan()), HttpStatus.OK);
    }

    @PostMapping("/my/myPlan")
    public ResponseEntity<Optional<List<Gericht>>> updateMyPlan(@RequestBody List<Gericht> userPlan, Principal principal){
        String userName = principal.getName();
        userService.updateUserPlan(userName, userPlan);
        Optional<User> user = userService.singleUser(userName);
        return new ResponseEntity<Optional<List<Gericht>>>(Optional.of(user.get().getUserPlan()), HttpStatus.OK);
    }

    @PostMapping("/my/myPlan/add")
    public ResponseEntity<Optional<List<Gericht>>> addToMyPlan(@RequestBody Gericht gericht, Principal principal){
        String userName = principal.getName();
        Optional<User> user = userService.singleUser(userName);
        List<Gericht> userPlan = user.get().getUserPlan();
        if(!userPlan.contains(gericht)){
            userPlan.add(gericht);
            userService.updateUserPlan(userName, userPlan);
        }
        return new ResponseEntity<Optional<List<Gericht>>>(Optional.of(user.get().getUserPlan()), HttpStatus.OK);
    }



}
