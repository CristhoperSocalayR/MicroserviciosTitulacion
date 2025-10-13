package pe.edu.vallegrande.TypeSupplier.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Table("type_supplier")
public class TypeSupplier {

    @Id
    private Long id;

    private String address;

    private String name;

    private String status = "A";

    @Column("ubigeo_id") // Relación con la tabla `ubigeo`
    private Long ubigeoId;
}

