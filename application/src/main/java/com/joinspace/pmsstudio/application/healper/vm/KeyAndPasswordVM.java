package com.joinspace.pmsstudio.application.healper.vm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * View Model object for storing the user's key and password.
 */
@ToString
@Getter
@Setter
public class KeyAndPasswordVM {

    private String key;

    private String newPassword;

}
