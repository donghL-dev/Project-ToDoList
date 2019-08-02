package com.donghun.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author dongh9508
 * @since 2019-06-14
 */
@Getter
@Setter
public class Mail {

    private String from;

    private String to;

    private String subject;

    private Map<String, Object> model;
}
