package com.company.docanalyzer.service;

import com.company.docanalyzer.exception.UserNotFoundException;
import com.company.docanalyzer.model.Document;
import com.company.docanalyzer.model.User;
import com.company.docanalyzer.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentService documentService;

    public List<User> getUsers() {
        return userRepository.findBy();
    }

    public User findByEmail(String email) throws UserNotFoundException {
        Assert.notNull(email, "email should not be null");

        User user =  userRepository.findFirstByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email " + email);
        }
        return user;
    }

    public List<User> filterUsers(Date fromDate, Date toDate, boolean upload) {
        Assert.notNull(fromDate, "fromDate should not be null");
        Assert.notNull(toDate, "toDate should not be null");

        List<User> users = userRepository.findAllByDateBefore(toDate);
        List<Document> docs = documentService.getDocumentsByDate(fromDate, toDate);

        List<User> userUpload = docs.stream()
                .filter(d -> users.contains(d.getUser()))
                .map(d -> d.getUser())
                .distinct()
                .collect(Collectors.toList());

        if (upload) {
            return userUpload;
        } else {
           users.removeAll(userUpload);
           return users;
        }
    }

}
