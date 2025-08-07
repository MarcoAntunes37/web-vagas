package com.flashvagas.api.admin_api.domain.entity.role;

import com.flashvagas.api.admin_api.domain.value_object.RoleAttributes;
import com.flashvagas.api.admin_api.domain.value_object.RoleDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    private RoleDetails details;
    private RoleAttributes attributes;
}
