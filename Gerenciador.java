/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciador;

import java.util.LinkedList;

/**
 *
 * @author Jo�oPaulo
 */
public class Gerenciador {

    private int tamanhoUnidadeAlocacao;
    private int tamanhoMemoria;
    private int comprimentoMemoria; //tamanho da mem�ria em unidades de aloca��o, ou seja, � a quantidade de unidades de aloca��o que essa mem�ria possui.
    LinkedList<SegmentoMemoria> listaEncadeada;

    public Gerenciador(int tamanhoUnidadeAlocacao, int tamanhoMemoria) {
        this.listaEncadeada = new LinkedList();
        this.tamanhoMemoria = tamanhoMemoria;
        this.tamanhoUnidadeAlocacao = tamanhoUnidadeAlocacao;

        this.comprimentoMemoria = tamanhoMemoria / tamanhoUnidadeAlocacao;

        //cria o primeiro segmento de mem�ria, indicando que a mem�ria est� livre
        listaEncadeada.add(new SegmentoMemoria(false, 0, comprimentoMemoria));
    }

    public int getUnidadeAlocacao() {
        return tamanhoUnidadeAlocacao;
    }

    public void setUnidadeAlocacao(int unidadeAlocacao) {
        this.tamanhoUnidadeAlocacao = unidadeAlocacao;
    }

    public int getTamanhoMemoria() {
        return tamanhoMemoria;
    }

    public void setTamanhoMemoria(int tamanhoMemoria) {
        this.tamanhoMemoria = tamanhoMemoria;
    }

    /* A aloca��o est� utilizando o algorimo First Fit */
    public void alocaProcesso(Processo p) {

        //Segmento de mem�ria para alocar o processo
        SegmentoMemoria seg = new SegmentoMemoria(true, 0, p.getComprimento());
        p.setSegmento(seg);

        //encontra a posi��o adequada para alocar o processo
        int indice = buscaSegmento(seg.getComprimento());

        if (indice == -1) {
            System.out.println("N�o existe mem�ria suficiente para alocar este processo de comprimento " + p.getComprimento());
            System.out.println("\n");
        } else {
            atualizaLista(indice, seg);
        }
    }

    //Toda vez que um segmento � inserido na lista, o �ndice dos segmentos armazenados na lista precisa ser atualizada  
    public void atualizaLista(int indice, SegmentoMemoria seg) {

        //pega o comprimento do segmento que ser� utilizado para alocar o processo
        int comprimento = listaEncadeada.get(indice).getComprimento();

        //se o comprimento do segmento � igual ao necess�rio para alocar o processo, nenhuma atualiza��o � necess�ria
        if (comprimento == seg.getComprimento()) {
            //atualiza a posi��o inicial do novo semento
            seg.setPosicaoInicial(listaEncadeada.get(indice).getPosicaoInicial());            
            
            //insere novo segmento na lista encadeada
            listaEncadeada.set(indice, seg);
            
            //se o comprimento do segmento for maior, ser� necess�rio atualizar a posi��o inicial desse segmento e tamb�m o seu comprimento
        } else {
            //atualiza a posi��o inicial do novo semento
            seg.setPosicaoInicial(listaEncadeada.get(indice).getPosicaoInicial());
            
            //acrescenta o segmento novo
            listaEncadeada.add(indice, seg);
            //atualiza o segmento antigo, reduzindo o tamanho do comprimento dele e atualizando a posi��o inicial
            SegmentoMemoria segAntigo = listaEncadeada.get(indice + 1);

            segAntigo.setComprimento(segAntigo.getComprimento() - seg.getComprimento());
            segAntigo.setPosicaoInicial(seg.getPosicaoInicial() + seg.getComprimento());

            //atualiza a posi��o inicial dos demais segmentos existentes na lista. Se n�o existir mais segmentos, nada ser� feito.
            SegmentoMemoria aux;

            for (int a = indice + 2; a < listaEncadeada.size(); a++) {

                aux = listaEncadeada.get(a);

                aux.setPosicaoInicial(listaEncadeada.get(a - 1).getPosicaoInicial() + listaEncadeada.get(a - 1).getComprimento());
            }
        }
    }

    //Lembrem que � necess�rio agrupar os segmentos vazios que est�o pr�ximos
    public void agregaSegmentosVazios() {
    }

    /**
     * Este m�todo procura por um segmento de mem�ria na lista encadeada que
     * seja maior ou igual que o comprimento passado como par�metro.
     *
     * @param comprimento comprimento m�nimo do segmento desejado
     * @return retorna o �ndice do segmento para alocar o processo, ou retorna
     * -1 caso n�o tenha segmento com tamanho adequado.
     */
    public int buscaSegmento(int comprimento) {

        for (int a = 0; a < listaEncadeada.size(); a++) {
            //Se o comprimento do segmento for maior ou igual ao tamanho exigido pelo processo, esse ser� o segmento escolhido
            if (!listaEncadeada.get(a).isOcupado() && listaEncadeada.get(a).getComprimento() >= comprimento) {
                return a;
            }
        }
        return -1;
    }

    
    public void exibeSegmentosMemoria() {

        System.out.println("-------- Segmentos de Mem�ria -------- \n");

        for (int a = 0; a < listaEncadeada.size(); a++) {
            System.out.println("Segmento [" + a + "] --> " + listaEncadeada.get(a));
        }

        System.out.println("\n\n");
    }

    public void exibeListaEncadeada() {

        System.out.println("-------- Segmentos de Mem�ria -------- \n");

        for (int a = 0; a < listaEncadeada.size(); a++) {
            if (listaEncadeada.size() - 1 == a) {
                System.out.print(listaEncadeada.get(a));
            } else {
                System.out.print(listaEncadeada.get(a) + " --> ");
            }
        }

        System.out.println("\n\n");
    }

    public static void main(String[] args) {

        //Gerenciador configurado para uma mem�ria de 62KB e dividida em unidades de aloca��o de 2KB
        Gerenciador gerenciador = new Gerenciador(2, 62);

        //cria um processo que precisa de um segmento de mem�ria com um comprimento maior ou igual a 10 para ser executado        
        Processo d = new Processo(10);
        
        Processo e = new Processo(3);
        Processo f = new Processo(1);

        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcesso(d);

        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcesso(e);

        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcesso(f);

        gerenciador.exibeListaEncadeada();

        //Processo finalizado
        d.setOnExecution(false);

        gerenciador.exibeListaEncadeada();

        gerenciador.alocaProcesso(f);

        gerenciador.exibeListaEncadeada();
    }
}
