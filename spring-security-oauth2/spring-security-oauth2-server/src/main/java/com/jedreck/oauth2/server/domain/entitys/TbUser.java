package com.jedreck.oauth2.server.domain.entitys;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * tb_user
 *
 * @author
 */
@Table(name = "tb_user")
@Data
public class TbUser implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户名
     */
    @NotEmpty
    private String username;

    /**
     * 密码，加密存储
     */
    @NotEmpty
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 注册邮箱
     */
    private String email;

    @NotEmpty
    private Date created;

    @NotEmpty
    private Date updated;

    private static final long serialVersionUID = 1L;
}