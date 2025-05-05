package com.autonext.code.autonext_server.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    User,
    Admin,
    Penalized,
    Banned;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
