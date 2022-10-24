package ch.unisg.tapasroster.roster.application.port.out;


import ch.unisg.tapasroster.roster.domain.Roster;

public interface AddRosterPort {

    void addRoster(Roster roster);
}
