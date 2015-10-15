package co.gov.mme.oare.ws.entities;

public class RegistroVenta {

    private int id;
    private Double subsidio;
    private Double valorTotal;

    public RegistroVenta(int id, Double subsidio, Double valorTotal){
        this.id = id;
        this.subsidio = subsidio;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }

    public Double getSubsidio() {
        return subsidio;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

}