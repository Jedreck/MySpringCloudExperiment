package com.jedreck.oauth2.server.domain.entitys;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * tb_user_role
 *
 * @author
 */
@Table(name = "tb_user_role")
@Data
public class TbUserRole implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户 ID
     */
    @NotEmpty
    private Long userId;

    /**
     * 角色 ID
     */
    @NotEmpty
    private Long roleId;

    private static final long serialVersionUID = 1L;
}