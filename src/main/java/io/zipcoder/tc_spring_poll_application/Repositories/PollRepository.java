package io.zipcoder.tc_spring_poll_application.Repositories;

import io.zipcoder.tc_spring_poll_application.Domain.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {
}
