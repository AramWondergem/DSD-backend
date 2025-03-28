package com.example.demo.mappers;

import com.example.demo.dto.UserDTO;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.util.enums.RoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User maptoUser(UserDTO dto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleSetToRoleTypeList")
    UserDTO mapToUserDTO(User entity);


    @Named("roleSetToRoleTypeList")
    static List<RoleType> map(Set<Role> source){

        return source.stream().map(Role::getName).toList();
    }
}
