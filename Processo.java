/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciador;

/**
 *
 * @author Jo�oPaulo
 */
public class Processo {
    
    boolean onExecution;
    int comprimento;
    private SegmentoMemoria segmento; //segmento de mem�ria onde este processo est� alocado

    public Processo(int comprimento) {
        this.comprimento = comprimento;
    }

    public boolean isOnExecution() {
        return onExecution;
    }

    public void setOnExecution(boolean onExecution) {
        this.onExecution = onExecution;
        
        if(!onExecution){
            segmento.setOcupado(false);
        }
    }

    public int getComprimento() {
        return comprimento;
    }

    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }    

    public SegmentoMemoria getSegmento() {
        return segmento;
    }

    public void setSegmento(SegmentoMemoria segmento) {
        this.segmento = segmento;
    }  
}
