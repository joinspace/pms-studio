package com.joinspace.pmsstudio.application.healper.mapper;

import com.joinspace.pmsstudio.application.healper.dto.TeamDTO;
import com.joinspace.pmsstudio.domain.Team;
import org.mapstruct.*;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {

    @Mapping(target = "projects", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    default Team fromId(Long id) {
        if (id == null) {
            return null;
        }
        Team team = new Team();
        team.setId(id);
        return team;
    }
}
