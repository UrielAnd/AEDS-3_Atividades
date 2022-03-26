package Trabalho_AED_Manipula_arq;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Clube_futebol {
    protected int id;       //Atributos//
    protected String nome;      //Atributos//
    protected String cnpj;      //Atributos//
    protected String cidade;        //Atributos//
    protected int partidasJogadas;      //Atributos//
    protected int pontos;       //Atributos//

    //Contrutora vazia
    public Clube_futebol(){

    }

    //Construtora passando parametros//
    public Clube_futebol (int i, String n, String cn, String c){

        id = i;
        nome = n;
        cnpj = cn;
        cidade = c;
        partidasJogadas = 0;
        pontos = 0;
    }
    //Retorna os dados do object Clube_futebol//
    public String toString(){
        return "ID: " + id + 
        "\nNOME: " + nome +
        "\nCNPJ: " + cnpj +
        "\nCIDADE: " + cidade +
        "\nPartidas Jogadas: " + partidasJogadas +
        "\nPontos: " + pontos;
    }
    //metodo para passar os atributos do objeto para o array de bytes do main//
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();       //array de bytes//
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.cnpj);
        dos.writeUTF(this.cidade);
        dos.writeInt(this.partidasJogadas);
        dos.writeInt(this.pontos);

        return baos.toByteArray();
}   
    //metodo para passar o array de bytes para as variáveis de atributos do objeto//
    public void fromByteArray(byte[] b) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(b);        //array de bytes//
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.cnpj= dis.readUTF();
        this.cidade= dis.readUTF();
        this.partidasJogadas = dis.readInt();
        this.pontos = dis.readInt();
    }
    //Captura o id e retorna pra quem chama//
    public int getId() {
        return this.id;
    }
    //Captura o seta o id informmado e retorna pra quem chama//
    public void setId(int i) {
    }
    
    public String getNome() {
        return null;
    }

    public char[] imprimeClube() {
        return null;
    }
    public void attpartida(int pontos){
        partidasJogadas++;
        this.pontos = pontos;
    }
}
