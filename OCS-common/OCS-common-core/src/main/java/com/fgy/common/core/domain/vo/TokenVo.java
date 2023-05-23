package com.fgy.common.core.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author fgy
 * description
 * date 2023/5/23 15:26
 */
@Data
public class TokenVo {
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiresTime;
}
