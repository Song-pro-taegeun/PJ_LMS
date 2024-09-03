package com.pro.lms.auth.dto;

import com.pro.lms.entity.LmsUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;
    private String pmName;
    private String pmPhone;

    public static MemberResponseDto of(LmsUser member) {
        return new MemberResponseDto(member.getPmUserId(), member.getPmName(), member.getPmPhone());
    }
}
