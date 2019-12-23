package com.example.LibraryManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.example.LibraryManagement.error.*;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    List<User> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/users")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    User newBook(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Find
    @GetMapping("/users/{id}")
    User findOne(@PathVariable int id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // Save or update
    @PutMapping("/users/{id}")
    User saveOrUpdate(@RequestBody User newUser, @PathVariable int id) {

        return repository.findById(id)
                .map(x -> {
                    x.setName(newUser.getName());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    // update author only
    @PatchMapping("/users/{id}")
    User patch(@RequestBody Map<String, String> update, @PathVariable int id) {

        return repository.findById(id)
                .map(x -> {

                    String name = update.get("name");
                    if (!StringUtils.isEmpty(name)) {
                        x.setName(name);

                        // better create a custom method to update a value = :newValue where id = :id
                        return repository.save(x);
                    } else {
                        throw new UserUnSupportedFieldPatchException(update.keySet());
                    }

                })
                .orElseGet(() -> {
                    throw new UserNotFoundException(id);
                });

    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }


}
