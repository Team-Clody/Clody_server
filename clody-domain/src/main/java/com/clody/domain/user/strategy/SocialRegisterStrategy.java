package com.clody.domain.user.strategy;

import com.clody.domain.user.dto.UserDomainInfo;

public interface SocialRegisterStrategy {

  UserSocialInfo getUserInfo(UserDomainInfo userSignUpRequest);
}
