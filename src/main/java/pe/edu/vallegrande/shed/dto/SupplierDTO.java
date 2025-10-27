package pe.edu.vallegrande.shed.dto;

public class SupplierDTO {
    private Long supplierId;  // Campo para el shedId

    // Getter y Setter para shedId
    public Long getsupplierId() {
        return supplierId;
    }

    public void setsupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    // Método toString para depuración
    @Override
    public String toString() {
        return "SupplierDTO{" +
                "supplierId=" + supplierId +
                '}';
    }

}
