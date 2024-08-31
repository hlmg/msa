package hlmg.users.shared.mapper;

import hlmg.users.data.UserEntity;
import hlmg.users.shared.UserDto;
import hlmg.users.ui.model.CreateUserRequest;
import hlmg.users.ui.model.CreateUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    UserEntity dtoToEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto entityToDto(UserEntity userEntity);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "encryptedPassword", ignore = true)
    UserDto creatRequestToDto(CreateUserRequest createRequest);

    CreateUserResponse dtoToCreateResponse(UserDto userDto);
}
