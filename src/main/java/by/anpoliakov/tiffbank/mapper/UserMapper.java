package by.anpoliakov.tiffbank.mapper;

import by.anpoliakov.tiffbank.domain.dto.UserDetailsResponseDto;
import by.anpoliakov.tiffbank.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    List<UserDetailsResponseDto> toListDto(List<User> user);

    UserDetailsResponseDto toUserDto(User user);
}
