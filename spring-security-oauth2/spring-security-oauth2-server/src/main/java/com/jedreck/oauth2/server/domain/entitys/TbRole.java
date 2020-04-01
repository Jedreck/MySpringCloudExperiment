package com.jedreck.oauth2.server.domain.entitys;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * tb_role
 *
 * @author
 */
@Table(name = "tb_role")
@Data
public class TbRole implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 父角色
     */
    private Long parentId;

    /**
     * 角色名称
     */
    @NotEmpty
    private String name;

    /**
     * 角色英文名称
     */
    @NotEmpty
    private String enname;

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