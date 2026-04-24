package Pck_Control;
import Pck_Model.AgendaModel;

public class AgendaControl {
    AgendaModel agendaModel = new AgendaModel();


    public void insertAgenda(String texto){
        agendaModel.setTexto(texto);
    }
}

