package pe.edu.vallegrande.Ubigeo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("ubigeo")
public class Ubigeo {

    @Id
    private Long id;

    private String country;

    private String department;

    private String province;

    private String district;

    private String status = "A";
}