package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import ch.unisg.tapasroster.roster.domain.RosterNotFoundError;

public interface LoadRosterAssignmentPort {

    RosterAssignment loadRoster(RosterAssignment.RosterId rosterId) throws RosterNotFoundError;

}
