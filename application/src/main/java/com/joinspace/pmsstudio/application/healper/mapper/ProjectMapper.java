package com.joinspace.pmsstudio.application.healper.mapper;

import com.joinspace.pmsstudio.application.healper.dto.ProjectDTO;
import com.joinspace.pmsstudio.domain.Project;
import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, TeamMapper.class})
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {

    @Mapping(source = "project.company.id", target = "companyId")
    ProjectDTO toDto(Project project);

    @Mapping(source = "projectDTO.companyId", target = "company.id")
    Project toEntity(ProjectDTO projectDTO);

    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }
}
