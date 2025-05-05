package com.autonext.code.autonext_server.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.dto.UserDto;
import com.autonext.code.autonext_server.dto.UserForAdminDTO;
import com.autonext.code.autonext_server.models.User;

@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getJobPosition(),
                user.getWorkCenter() != null ? user.getWorkCenter().getName() : "---"
        // user.getStrikes()
        );
    }

    public UserForAdminDTO convertToUserForAdminDTO(User user) {
        return new UserForAdminDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getStrikes().size(),
                user.getRole(),
                user.getJobPosition(),
                user.getWorkCenter() != null ? user.getWorkCenter().getName() : null);
    }

    public List<UserForAdminDTO> convertToListUserForAdminDTO(List<User> users) {
        List<UserForAdminDTO> usersDtos = new ArrayList<>();

        for (User user : users) {
            usersDtos.add(convertToUserForAdminDTO(user));
        }

        return usersDtos;
    }

    public List<UserForAdminDTO> convertToIterableUserForAdminDTO(Iterable<User> users) {
        List<UserForAdminDTO> usersDtos = new ArrayList<>();

        for (User user : users) {
            usersDtos.add(convertToUserForAdminDTO(user));
        }

        return usersDtos;
    }
}
