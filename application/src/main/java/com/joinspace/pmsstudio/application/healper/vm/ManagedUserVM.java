package com.joinspace.pmsstudio.application.healper.vm;

import com.joinspace.pmsstudio.application.healper.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
@ToString
@Getter
@Setter
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

}
