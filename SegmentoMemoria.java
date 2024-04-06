/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciador;

/**
 *
 * @author Jo�oPaulo
 */
public class SegmentoMemoria {
    
    private boolean ocupado;
    private int posicaoInicial;
    private int comprimento;

    public SegmentoMemoria(boolean ocupado, int posicaoInicial, int comprimento) {
        this.ocupado = ocupado;
        this.posicaoInicial = posicaoInicial;
        this.comprimento = comprimento;
    }
    
    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public int getPosicaoInicial() {
        return posicaoInicial;
    }

    public void setPosicaoInicial(int posicaoInicial) {
        this.posicaoInicial = posicaoInicial;
    }

    public int getComprimento() {
        return comprimento;
    }

    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }
       
    @Override
    public String toString() {
        return String.format("[%s | %d | %d]", Boolean.toString(isOcupado()),
                getPosicaoInicial(), getComprimento());
    }        
}
