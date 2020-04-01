package com.jedreck.oauth2.server.domain.entitys;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * tb_role_permission
 *
 * @author
 */
@Table(name = "tb_role_permission")
@Data
public class TbRolePermission implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 角色 ID
     */
    @NotEmpty
    private Long roleId;

    /**
     * 权限 ID
     */
    @NotEmpty
    private Long permissionId;

    private static final long serialVersionUID = 1L;
}