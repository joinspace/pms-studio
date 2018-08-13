package com.joinspace.pmsstudio.application.healper.mapper;

import com.joinspace.pmsstudio.application.healper.dto.CompanyDTO;
import com.joinspace.pmsstudio.application.healper.dto.ProjectDTO;
import com.joinspace.pmsstudio.domain.Company;
import com.joinspace.pmsstudio.domain.Project;
import org.mapstruct.*;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {

    @Mapping(target = "projects", ignore = true)
    CompanyDTO toDto(Company company);

    @Mapping(target = "projects", ignore = true)
    Company toEntity(CompanyDTO companyDTO);

    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
