package com.atom.personnel.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DepartmentResponseProjection {

    String getDepartmentName();
    LocalDate getCreatedTime();
    String getManagerName();
    Long getEmployeeCount();
}
