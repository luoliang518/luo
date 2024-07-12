package com.luo.auth.infrastructure.converter;

import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.repository.po.UserPO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-12T15:44:19+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public User userPoToUser(UserPO userPO) {
        if ( userPO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userId( userPO.getId() );
        user.username( userPO.getUsername() );
        user.account( userPO.getAccount() );
        user.password( userPO.getPassword() );
        user.avatar( userPO.getAvatar() );
        user.email( userPO.getEmail() );
        user.phone( userPO.getPhone() );

        return user.build();
    }

    @Override
    public UserPO userToUserPo(User user) {
        if ( user == null ) {
            return null;
        }

        UserPO.UserPOBuilder userPO = UserPO.builder();

        userPO.username( user.getUsername() );
        userPO.account( user.getAccount() );
        userPO.password( user.getPassword() );
        userPO.avatar( user.getAvatar() );
        userPO.email( user.getEmail() );
        userPO.phone( user.getPhone() );

        return userPO.build();
    }
}
