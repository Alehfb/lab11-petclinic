package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialtyDTO {

    private Integer id;
    private String name;
    private String office;
    private Integer openHour;
    private Integer closeHour;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOffice() { return office; }
    public void setOffice(String office) { this.office = office; }

    public Integer getOpenHour() { return openHour; }
    public void setOpenHour(Integer openHour) { this.openHour = openHour; }

    public Integer getCloseHour() { return closeHour; }
    public void setCloseHour(Integer closeHour) { this.closeHour = closeHour; }
}
