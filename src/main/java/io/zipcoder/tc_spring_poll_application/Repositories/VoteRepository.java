package io.zipcoder.tc_spring_poll_application.Repositories;

import io.zipcoder.tc_spring_poll_application.Domain.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    @Query(value = "SELECT v.* " +
            "FROM Option o, Vote v " +
            "WHERE o.POLL_ID = ?1 " +
            "AND v.OPTION_ID = o.OPTION_ID", nativeQuery = true)
    public Iterable<Vote> findVotesByPoll(Long pollId);

}
