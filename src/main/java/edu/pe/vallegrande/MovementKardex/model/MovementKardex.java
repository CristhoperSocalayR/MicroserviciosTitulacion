package edu.pe.vallegrande.MovementKardex.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("movement_kardex")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementKardex {

    @Id
    @Column("kardex_id")
    private Long kardexId;

    @Column("issue_date")
    private LocalDate issueDate;

    @Column("concept")
    private String concept;

    @Column("document_type")
    private String documentType;

    @Column("document_number")
    private String documentNumber;
    // Datos de Entradas
    @Column("cantidad_entrada")
    private BigDecimal cantidadEntrada;

    @Column("costo_unitario_entrada")
    private BigDecimal costoUnitarioEntrada;

    @Column("valor_total_entrada")
    private BigDecimal valorTotalEntrada;

    // Datos de Salidas
    @Column("cantidad_salida")
    private BigDecimal cantidadSalida;

    @Column("costo_unitario_salida")
    private BigDecimal costoUnitarioSalida;

    @Column("valor_total_salida")
    private BigDecimal valorTotalSalida;

    // Datos de Saldos
    @Column("cantidad_saldo")
    private BigDecimal cantidadSaldo;

    @Column("costo_unitario_saldo")
    private BigDecimal costoUnitarioSaldo;

    @Column("valor_total_saldo")
    private BigDecimal valorTotalSaldo;

    // Datos del Movimiento
    @Column("observation")
    private String observation;

    @Column("type_kardex_id")
    private Integer typeKardexId;

}