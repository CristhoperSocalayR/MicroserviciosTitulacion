package edu.pe.vallegrande.TypeKardex.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Table("type_kardex")
public class TypeKardex {

    @Id
    private Long id;

    private String name;

    @Column("maximum_amount")
    private Integer maximumAmount;

    @Column("minimum_quantity")
    private Integer minimumQuantity;

    @Column("supplier_id")
    private Integer supplierId;

    @Column("product_id")
    private Long productId;

    @Column("shed_id")
    private Long shedId;

    private String description;

    private String status = "A";

}