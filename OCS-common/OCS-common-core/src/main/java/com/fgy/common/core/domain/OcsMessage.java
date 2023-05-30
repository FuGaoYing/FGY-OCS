package com.fgy.common.core.domain;

import lombok.Data;

/**
 * @author fgy
 * description
 * date 2023/5/30 15:23
 */
@Data
public class OcsMessage<E> {
    private Long messageId;
    private Long dateTime;
    private E message;
}
