package com.autonext.code.autonext_server.mapper;

import com.autonext.code.autonext_server.dto.UserDto;
import com.autonext.code.autonext_server.models.User;

public class UserMapper {
    
    public static UserDto toUserDto(User user) {
        return new UserDto(
            user.getName() + " " + user.getSurname(),
            user.getEmail(),
            user.getJobPosition(),
            user.getWorkCenter() != null ? user.getWorkCenter().getName() : "---"
            // user.getStrikes()
        );
    }
}
