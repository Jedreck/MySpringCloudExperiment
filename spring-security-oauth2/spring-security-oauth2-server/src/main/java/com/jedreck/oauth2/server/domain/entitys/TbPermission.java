package com.jedreck.oauth2.server.domain.entitys;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * tb_permission
 *
 * @author
 */
@Table(name = "tb_permission")
@Data
public class TbPermission implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 父权限
     */
    private Long parentId;

    /**
     * 权限名称
     */
    @NotEmpty
    private String name;

    /**
     * 权限英文名称
     */
    @NotEmpty
    private String enname;

    /**
     * 授权路径
     */
    @NotEmpty
    private String url;

    /**
     * 备注
     */
    private String description;

    @NotEmpty
    private Date created;

    @NotEmpty
    private Date updated;

    private static final long serialVersionUID = 1L;
}