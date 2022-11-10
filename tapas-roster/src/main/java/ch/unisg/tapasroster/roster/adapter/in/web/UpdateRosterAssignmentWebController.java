package ch.unisg.tapasroster.roster.adapter.in.web;

import ch.unisg.tapasroster.roster.adapter.in.formats.UpdateRosterAssignmentJsonRepresentation;
import ch.unisg.tapasroster.roster.application.port.in.UpdateRosterAssignmentCommand;
import ch.unisg.tapasroster.roster.application.port.in.UpdateRosterAssignmentUseCase;
import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UpdateRosterAssignmentWebController {

    private final UpdateRosterAssignmentUseCase updateRosterAssignmentUseCase;

    // Used to retrieve properties from application.properties
    @Autowired
    private Environment environment;

    @PostMapping(path = "/roster/updatetask/{id}", consumes = "application/json")
    public ResponseEntity<HttpStatus> updateTaskStatus(@PathVariable String id, @RequestBody UpdateRosterAssignmentJsonRepresentation payload) {
        try {
            RosterAssignment.AssignmentId assignmentId = new RosterAssignment.AssignmentId(id);
            RosterAssignment.AssignmentStatus newAssignmentStatus = new RosterAssignment.AssignmentStatus(payload.isSuccess() ? "SUCCESS" : "FAILED");
            Optional<RosterAssignment.AssignmentStatus> newStatus = Optional.of(newAssignmentStatus);
            Optional<String> outputData = Optional.ofNullable(payload.getOutputData());


            UpdateRosterAssignmentCommand command = new UpdateRosterAssignmentCommand(assignmentId, newStatus, outputData);

            if(updateRosterAssignmentUseCase.updateAssignment(command)){
                return new ResponseEntity<>(null, HttpStatus.OK);
            }

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);


        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("failed");
        }
    }

}
