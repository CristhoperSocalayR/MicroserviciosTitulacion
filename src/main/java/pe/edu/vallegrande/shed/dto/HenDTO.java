package pe.edu.vallegrande.shed.dto;

public class HenDTO {
    private Long henId;

    public Long gethenId(){
        return henId;
    }

    public void setHenId(Long henId){
        this.henId = henId;
    }

    @Override
    public String toString() {
        return "HenDTO{" +
                "henId=" + henId +
                '}';
    }
}