package com.joinspace.pmsstudio.application.api;

import com.joinspace.pmsstudio.application.healper.dto.ProjectDTO;
import com.joinspace.pmsstudio.application.healper.exception.BadRequestAlertException;
import com.joinspace.pmsstudio.application.healper.util.HeaderUtil;
import com.joinspace.pmsstudio.application.healper.util.PaginationUtil;
import com.joinspace.pmsstudio.application.healper.util.ResponseUtil;
import com.joinspace.pmsstudio.application.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/api")
public class ProjectApi {

    private final Logger log = LoggerFactory.getLogger(ProjectApi.class);

    private static final String ENTITY_NAME = "project";

    private final ProjectService projectService;

    public ProjectApi(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * POST  /projects : Create a new project.
     *
     * @param projectDTO the projectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectDTO, or with status 400 (Bad Request) if the project has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) throws URISyntaxException {
        log.debug("REST request to save Project : {}", projectDTO);
        if (projectDTO.getId() != null) {
            throw new BadRequestAlertException("A new project cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectDTO result = projectService.save(projectDTO);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projects : Updates an existing project.
     *
     * @param projectDTO the projectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectDTO,
     * or with status 400 (Bad Request) if the projectDTO is not valid,
     * or with status 500 (Internal Server Error) if the projectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projects")
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectDTO projectDTO) throws URISyntaxException {
        log.debug("REST request to update Project : {}", projectDTO);
        if (projectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectDTO result = projectService.save(projectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projects : get all the projects.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of projects in body
     */
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getAllProjects(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Projects");
        Page<ProjectDTO> page;
        if (eagerload) {
            page = projectService.findAllWithEagerRelationships(pageable);
        } else {
            page = projectService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/projects?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projects/:id : get the "id" project.
     *
     * @param id the id of the projectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id) {
        log.debug("REST request to get Project : {}", id);
        Optional<ProjectDTO> projectDTO = projectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectDTO);
    }

    /**
     * DELETE  /projects/:id : delete the "id" project.
     *
     * @param id the id of the projectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.debug("REST request to delete Project : {}", id);
        projectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
