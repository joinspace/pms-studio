package com.joinspace.pmsstudio.application.service;

import com.joinspace.pmsstudio.application.healper.dto.UserDTO;
import com.joinspace.pmsstudio.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Activate Registration
     *
     * @param key
     */
    Optional<User> activateRegistration(String key);

    /**
     * Reset password
     *
     * @param key
     */
    Optional<User> completePasswordReset(String newPassword, String key);

    /**
     * Password reset request process
     *
     * @param mail
     */
    Optional<User> requestPasswordReset(String mail);

    /**
     * Register user
     *
     * @param userDTO
     * @param password
     */
    User registerUser(UserDTO userDTO, String password);

    /**
     * Create user
     *
     * @param userDTO
     */
    User createUser(UserDTO userDTO);

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl);

    /**
     * update user
     *
     * @param userDTO
     */
    Optional<UserDTO> updateUser(UserDTO userDTO);

    /**
     * Delete user
     *
     * @param username
     */
    void deleteUser(String username);

    /**
     * Reset password
     *
     * @param currentClearTextPassword
     * @param newPassword
     */
    void changePassword(String currentClearTextPassword, String newPassword);

    /**
     * Get all managed users
     *
     * @param pageable
     */
    Page<UserDTO> getAllManagedUsers(Pageable pageable);

    /**
     * Fetch user with authorities using username
     *
     * @param username of user
     */
    Optional<User> getUserWithAuthoritiesByLogin(String username);

    /**
     * Fetch user with authorities using id
     *
     * @param id ,id of user
     */
    Optional<User> getUserWithAuthorities(Long id);

    /**
     * Fetch current user with authorities
     *
     */
    Optional<User> getUserWithAuthorities();

    /**
     * Removed user which are not actives
     **/
    void removeNotActivatedUsers();

    /**
     * Get Authorities
     */
    List<String> getAuthorities();


}
