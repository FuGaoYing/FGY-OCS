package com.fgy.common.core.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fgy
 * description
 * date 2023/5/30 15:23
 */
@Data
public class OcsMessage<E> implements Serializable {
    private Long messageId;
    private Long dateTime;
    private E message;
}
