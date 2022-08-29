package com.expressbank.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleEnum {

    NONE("none"),
    ROLE_SUPER_ADMIN("Super admin"),
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");

    private String roleName;
}
