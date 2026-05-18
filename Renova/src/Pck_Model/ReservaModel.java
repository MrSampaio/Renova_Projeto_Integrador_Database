package Pck_Model;

public class ReservaModel {
    private int idReserva;
    private double totalReserva;
    private String metodoPagamento;
    private String dataReserva;
    private String dataValidade;
    private String statusReserva;

    public int getIdReserva() {
        return idReserva;
    }

    public double getTotalReserva() {
        return totalReserva;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public String getDataReserva() {
        return dataReserva;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public String getStatusReserva() {
        return statusReserva;
    }

    public void setIdReserva(int idReserva) {
        if(idReserva <= 0){
            throw new IllegalArgumentException("Erro ao atribuir o ID da reserva.");
        } else{
            this.idReserva = idReserva;
        }
    }

    public void setTotalReserva(double totalReserva) {
        if(totalReserva <= 0){
            throw new IllegalArgumentException("Erro ao atribuir o valor da reserva.");
        } else{
            this.totalReserva = totalReserva;
        }
    }

    public void setMetodoPagamento(String metodoPagamento) {
        if(!metodoPagamento.toUpperCase().equals("DÉBITO") && !metodoPagamento.toUpperCase().equals("DINHEIRO") && !metodoPagamento.toUpperCase().equals("PIX")){
            throw new IllegalArgumentException("Erro ao atribuir o método de pagamento da reserva.");
        } else{
            this.metodoPagamento = metodoPagamento;
        }

    }

    public void setDataReserva(String dataReserva) {
        if(dataReserva.isEmpty()){
            throw new IllegalArgumentException("Erro ao atribuir a data de reserva.");
        } else{
            this.dataReserva = dataReserva;
        }

    }

    public void setDataValidade(String dataValidade) {
        if(dataValidade.isEmpty()){
            throw new IllegalArgumentException("Erro ao atribuir a data de validade.");
        } else{
            this.dataValidade = dataValidade;
        }
    }

    public void setStatusReserva(String statusReserva) {
        if(!statusReserva.toUpperCase().equals("AGUARDANDO PAGAMENTO") && !statusReserva.toUpperCase().equals("CONCLUÍDA") && !statusReserva.toUpperCase().equals("CANCELADA")){
            throw new IllegalArgumentException("Erro ao atribuir status da reserva.");
        } else{
            this.statusReserva = statusReserva;
        }
    }
}
