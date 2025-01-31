package io.zipcoder.tc_spring_poll_application.Controller;

import io.zipcoder.tc_spring_poll_application.Domain.Poll;
import io.zipcoder.tc_spring_poll_application.Exception.ResourceNotFoundException;
import io.zipcoder.tc_spring_poll_application.Repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class PollController {
    private PollRepository pollRepository;

    @Autowired
    public PollController(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Valid
    @RequestMapping(value = "/polls", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @Valid
    @RequestMapping(value = "/polls", method = RequestMethod.POST)
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
        poll = pollRepository.save(poll);
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        HttpHeaders httpResult = new HttpHeaders();
        httpResult.setLocation(newPollUri);
        return new ResponseEntity<>(httpResult, HttpStatus.CREATED);
    }

    @Valid
    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        Poll p = pollRepository.findOne(pollId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @Valid
    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        // Save the entity
        Poll p = pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Valid
    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        pollRepository.delete(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void verifyPoll(Long pollId) throws ResourceNotFoundException {
        Poll poll = pollRepository.findOne(pollId);
        if (poll == null) {
            throw new ResourceNotFoundException();
        }
    }

}