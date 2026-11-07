package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "specialties")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String office;

    @Column(name = "h_open")
    private Integer openHour;

    @Column(name = "h_close")
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
