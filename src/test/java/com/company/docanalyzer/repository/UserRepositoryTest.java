package com.company.docanalyzer.repository;

import com.company.docanalyzer.model.Team;
import com.company.docanalyzer.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findFirstByEmail_shouldFindUser_WhenAdded() {
        // given
        Team team = Team.builder().name("team4").build();
        User user = User.builder().email("user4@gmail.com").teams(Set.of(team)).build();
        user = userRepository.save(user);

        // when
        User user2 = userRepository.findFirstByEmail("user4@gmail.com");

        // then
        assertEquals(entityManager.getId(user), user2.getId());

    }

    @Test
    void findBy_shouldFind5Users_when2MoreAdded() {
        // given
        Team team = Team.builder().name("team4").build();
        User user = User.builder().email("user4@gmail.com").teams(Set.of(team)).build();
        userRepository.save(user);
        user = User.builder().email("user5@gmail.com").teams(Set.of(team)).build();
        userRepository.save(user);

        // when
        List<User> users = userRepository.findBy();

        // then
        assertEquals(users.size(), 5);
        assertEquals(users.get(0).getEmail(), "user1@gmail.com");
        assertEquals(users.get(1).getEmail(), "user2@gmail.com");
        assertEquals(users.get(2).getEmail(), "user3@gmail.com");
        assertEquals(users.get(3).getEmail(), "user4@gmail.com");
        assertEquals(users.get(4).getEmail(), "user5@gmail.com");
    }

    @Test
    void findAllByDateBefore_shouldFindOnly2Users_whenDateBefore2DaysAgo() {
        // given
        Team team = Team.builder().name("team4").build();
        User user = User.builder().email("user4@gmail.com").teams(Set.of(team)).date(new Date()).build();
        userRepository.save(user);

        // when
        List<User> users = userRepository.findAllByDateBefore(Date.from(Instant.now().minus(Duration.ofDays(2))));

        // then
        assertEquals(users.size(), 2);
        assertEquals(users.get(0).getEmail(), "user1@gmail.com");
        assertEquals(users.get(1).getEmail(), "user2@gmail.com");
    }
}