package ch.unisg.tapasroster.roster.application.port.out;


import ch.unisg.tapasroster.roster.domain.RosterAssignment;

public interface AddRosterAssignmentPort {

    void addRosterAssignment(RosterAssignment rosterAssignment);
}
