package com.aak1247.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * @author C aak12 on 2017/5/10.
 */
@Document(collection = "roles")
public class Role {
    @Id
    private String roleId;
    private String roleName;
    private Set<String> ResourceList;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<String> getResourceList() {
        return ResourceList;
    }

    public void setResourceList(Set<String> permissionList) {
        ResourceList = permissionList;
    }
}
