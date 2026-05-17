package Pck_Control;

import Pck_Model.ProdutoModel;
import Pck_Persistencia.ReservaPersistencia;
import java.util.ArrayList;

public class ReservaControl {

    public boolean realizarReserva(int idUsuario, double totalPedido, String metodoPagamento, ArrayList<ProdutoModel> carrinho){
        ReservaPersistencia persistencia = new Pck_Persistencia.ReservaPersistencia();
        return persistencia.finalizarPedido(idUsuario, totalPedido, metodoPagamento, carrinho);
    }
}
