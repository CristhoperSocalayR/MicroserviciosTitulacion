package pe.edu.vallegrande.Supplier.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("supplier")
public class Supplier {

    @Id
    private Long id;

    @Column("RUC")
    private String ruc;

    private String company;

    private String name;

    private String lastname;

    private String email;

    private String cellphone;

    private String notes;

    @Column("register_date")
    private LocalDate registerDate;

    private String status = "A";

    @Column("type_supplier_id") // Relación con la tabla `type_supplier`
    private Long typeSupplierId;
}
