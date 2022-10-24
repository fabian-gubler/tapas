package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.Executor;
import ch.unisg.tapasroster.roster.domain.Roster;
import ch.unisg.tapasroster.roster.domain.RosterNotFoundError;

public interface LoadRosterPort {

    Roster loadRoster(Roster.RosterId rosterId) throws RosterNotFoundError;

}
