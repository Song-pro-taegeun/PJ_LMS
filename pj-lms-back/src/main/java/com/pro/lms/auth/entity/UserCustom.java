package com.pro.lms.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

@Getter
@Setter
@ToString
public class UserCustom extends User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private int pmUserNo;
    private String pmUserId;
    private String pmName;
    private String pmPhone;


    public UserCustom(String username, String password, boolean enabled, boolean accountNonExpired,
                      boolean credentialsNonExpired, boolean accountNonLocked, Collection authorities,
                      int pmUserNo, String pmUserId, String pmName, String pmPhone) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                authorities);
        this.pmUserNo = pmUserNo;
        this.pmUserId = pmUserId;
        this.pmName = pmName;
        this.pmPhone = pmPhone;
    }
}