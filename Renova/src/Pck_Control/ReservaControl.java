package Pck_Control;

import Pck_Model.ProdutoModel;
import Pck_Persistencia.ReservaPersistencia;
import Pck_Model.ReservaModel;
import java.util.ArrayList;


public class ReservaControl {

    public boolean realizarReserva(int idUsuario, double totalPedido, String metodoPagamento, ArrayList<ProdutoModel> carrinho){
        ReservaPersistencia persistencia = new Pck_Persistencia.ReservaPersistencia();
        return persistencia.finalizarPedido(idUsuario, totalPedido, metodoPagamento, carrinho);
    }

    public ArrayList<ReservaModel> listarReservas(int idUsuario){
        ReservaPersistencia persistencia = new ReservaPersistencia();
        return persistencia.listarReservasPorUsuario(idUsuario);
    }

    public ArrayList<ProdutoModel> listarProdutosPorReserva(int idReserva){
        ReservaPersistencia persistencia = new ReservaPersistencia();
        return persistencia.listarProdutosPorReserva(idReserva);
    }

    public ReservaModel buscarReservaPorId(int idReserva, int idUsuario) {
        ReservaPersistencia persistencia = new ReservaPersistencia();
        return persistencia.buscarReservaPorId(idReserva, idUsuario);
    }
}
