package com.hd.hdaisdk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 开发者对话请求
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevChatRequest implements Serializable {

    /**
     * 模型id
     */
    private String appId;

    /**
     * 消息
     */
    private String message;

    private static final long serialVersionUID = 1L;

}