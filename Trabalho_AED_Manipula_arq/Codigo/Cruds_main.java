/*
*
* Uriel Andrade
*
*/
package Trabalho_AED_Manipula_arq;

//Bibliotecas//
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;


public class Cruds_main {
    private static void clearBuffer(Scanner scanner)        //procedimento para linpa o bufffer de memória//
    {
        if (scanner.hasNextLine()) 
        {
            scanner.nextLine();
        }
    }
    static Scanner sca = new Scanner(System.in);
    public static void main(String[] args) throws IOException{
        short menu;
        do{
            System.out.print("|1->Criar Clube|\n|2->Ver o(s) clube(s)|\n|3->Deletar||\n|4->Update||\n|5->Cadastrar Partida|\n|0->Sair|\n");     //INFO para o usuário via terminal//
            System.out.print("O que deseja fazer: ");       //INFO para o usuário via terminal//
            menu = sca.nextShort();
        
            switch(menu){           //Sistema de menu do Usuário//
                case 1:
                    System.out.println(Create_Time(menu));
                break;
                case 2:
                    short shortmenu;
                       do{
                        System.out.println("Você deseja ler todos os times ou apenas um time:");        //INFO para o usuário via terminal//
                        System.out.println("|1->Todos|\n|2->Apenas 1 time|\n|0->Sair|");        //INFO para o usuário via terminal//
                        shortmenu = sca.nextShort();
                        switch(shortmenu){          //Escolha do na quantidade de times que vai lê do arquivo//
                            case 1:
                                Read_Time(menu);
                                break;
                            case 2:
                                System.out.println("Digite o id do time que deseja lê:");       //INFO para o usuário via terminal//
                                int id = sca.nextInt();
                                Clube_futebol time = read(id);
                                System.out.println(time);
                                break;
                            default:
                            System.out.println("-----Essa opcção não existe-----");     //INFO para o usuário via terminal//
                        }
                        }while(shortmenu!=0);
                break;
                case 3:
                    boolean bol;
                    do{
                        System.out.print("Qual time você deseja deletar:");     //INFO para o usuário via terminal//
                        int escolha = sca.nextInt();
                        bol = Times_Delete(escolha);
                        System.out.println((bol == true) ? "Clube deletado":"Clube não existe");        //INFO para o usuário via terminal//
                    }while(bol == false);
                break;
                case 4:
                    System.out.print("Qual o id do time você deseja atualizar:");       //INFO para o usuário via terminal//
                    int timeupdate = sca.nextInt();
                    System.out.print("\nInforme o nome:");      //INFO para o usuário via terminal//
                    String n = sca.next();
                    System.out.print("\nInforme o cnpj:");      //INFO para o usuário via terminal//
                    String novocnpj = sca.next();
                    System.out.print("\nInforme a cidade:");        //INFO para o usuário via terminal//
                    String novacidade = sca.next();

                    Clube_futebol objeto = new Clube_futebol(timeupdate, n, novocnpj, novacidade);

                    System.out.println(objeto);

                    // ClubeDeFutebol time = new ClubeDeFutebol(objeto.id, objeto.nome, objeto.cnpj, objeto.cidade);
                    // ArrayList<ClubeDeFutebol> TimesWrite = new ArrayList<ClubeDeFutebol>(); 
                    // TimesWrite.add(time);
                    System.out.print("\nAtualizado com sucesso!!"+ "\n");       //INFO para o usuário via terminal//
                    Times_Update(objeto);
                break;
                case 5:
                System.out.print("Informe o id do primeiro time:");         //INFO para o usuário via terminal//
                int newid1 = sca.nextInt();
                
                System.out.print("Quantos gols o esse time fez:");          //INFO para o usuário via terminal//
                int gols1 = sca.nextInt();

                Clube_futebol time1 = read(newid1);
                // System.out.println(time1);

                System.out.print("Informe o id do segundo time:");              //INFO para o usuário via terminal//
                int newid2 = sca.nextInt();

                System.out.print("Quantos gols esse time fez:");            //INFO para o usuário via terminal//
                int gols2 = sca.nextInt();
                
                Clube_futebol time2 = read(newid2);
                // System.out.println(time2);

                if(gols1 > gols2){
                    time1.attpartida(3);
                    time2.attpartida(0);
                }
                else if(gols2 > gols1){
                    time1.attpartida(0);
                    time2.attpartida(3);
                }
                else{
                    time1.attpartida(1);
                    time2.attpartida(1);
                }

                Times_Update(time1);
                Times_Update(time2);

                System.out.println(time1);
                System.out.println(time2);

                break;
                default:
                System.out.println("___Escolha invalida___");               //INFO para o usuário via terminal//
            }
        }while(menu!=0);
        sca.close();
    }


    public static String Create_Time(int menu) {       //Cria o time no arquivo .db//

        ArrayList<Clube_futebol> TimesWrite = new ArrayList<Clube_futebol>();       //Lista para armazerar oque vai ser escrito no arquivo//
        int id = 1;         //id dos clubes//
        int op = 0;         //Variável auxiliar//

        id = Read_Time(menu);       //Resgata id para a criação do id seguinte//
        if(id==0){
            id=1;
        }
        else{
            id++;
        }        
        //Capitura informações do clube inserido pelo usuario//
        do{
            clearBuffer(sca);
            System.out.println("Digite o nome do time:");           //INFO para o usuário via terminal//
            String nome = sca.nextLine();
            System.out.println("Digite o CNPJ do time:");           //INFO para o usuário via terminal//
            String CNPJ = sca.nextLine();
            System.out.println("Digite a cidade do time:");             //INFO para o usuário via terminal//
            String cidade = sca.nextLine();
            
            Clube_futebol time = new Clube_futebol(id, nome, CNPJ, cidade);
            TimesWrite.add(time);
            id++;

            System.out.println("|1->Adicionar outro time|\n|0->Sair|");         //INFO para o usuário via terminal//
            System.out.print("Escolha: ");              //INFO para o usuário via terminal//
            op = sca.nextInt();
        }while(op==1);
        RandomAccessFile arq;
        byte[] b;       //Array do tipo byte declaração//
        id--;
        try{            //Tratamento de cod//
            arq = new RandomAccessFile("Trabalho_AED_Manipula_arq/Dados/Club_fut.db", "rw");      //Criação/Abertura do arquivo .bd, para leitura e escrita//
            arq.seek(0);
            arq.writeInt(id);
            arq.seek(arq.length());     //Move o ponteiro do arquivo para a posição indicada//
                
                //For para escrever todos os times no arquivo enqunato nomedoarraylist.size//
                for(int repet = 0; repet < TimesWrite.size(); repet++){  
                    arq.writeByte(' ');   
                    b = TimesWrite.get(repet).toByteArray();        //Transforma e escreve os atributos do time.get(posição) na array do tipo byte b//
                    arq.writeInt(b.length);
                    arq.write(b);
                }
                arq.close();
        }catch(Exception e){            //Tratamento de cod//
            System.out.println("ERRO");
        }

        return "----Criado com sucesso----";
    }
    
    
    public static int Read_Time(int menu) {     //Lê os times do arquivo .db//

        ArrayList<Clube_futebol> TimesRead = new ArrayList<Clube_futebol>();       //Lista para armazerar oque vai ser escrito no arquivo//

        RandomAccessFile arq;
        byte[] b;       //Array do tipo byte declaração//
        byte lapide;
        int tam;        //Variável auxiliar//
        try{
            arq = new RandomAccessFile("Trabalho_AED_Manipula_arq/Dados/Club_fut.db", "rw");      //Criação/Abertura do arquivo .bd, para leitura e escrita//
            arq.seek(4);        //Move o ponteiro do arquivo para a posição indicada//

                //While para Ler todos os times no arquivo até que o ponteiro seja do mesmo tamanho do arquivo//
                while(arq.getFilePointer() < arq.length()){
                    Clube_futebol timeR = new Clube_futebol();      //Instancia objetos sem valor nos atributos//
                    lapide = arq.readByte();
                    tam = arq.readInt();        //Lê do arquivo o tamanho da array de bytes do objeto a se lido//
                    // System.out.println(tam);
                    b = new byte[tam];
                    arq.read(b);            //Lê o arquivo e passa tudo para a array b// 
                    if(lapide != '*') {
                        timeR.fromByteArray(b);     //Chama a função fromByteArray para armazenar no objeto os atributos adiquiridos pela array b//
                        TimesRead.add(timeR);
                    }
                }
                arq.close();
        }catch(Exception e){
            System.out.println("ERRO");
        }


        if(menu == 2)    
        {
            //Printa na tela os times com seus atributos//
            for(int repet = 0; repet < TimesRead.size(); repet++){
                System.out.println(TimesRead.get(repet));
            }
        }
        return TimesRead.size();
    }

    //Deleta o time informado pelo usuário do arquivo//
    public static boolean Times_Delete(int id) throws IOException {

        RandomAccessFile arq = new RandomAccessFile("Trabalho_AED_Manipula_arq/Dados/Club_fut.db", "rw");
        arq.seek(4);
        // pular cabecalho
        long pos;
        byte lapide;
        int tam;
        byte[] b;
        Clube_futebol objeto;
        while (arq.getFilePointer() < arq.length()) {
            pos = arq.getFilePointer();
            lapide = arq.readByte();
            tam = arq.readInt();
            b = new byte[tam];
            arq.read(b);
            if (lapide != '*') {
                objeto = new Clube_futebol();
                objeto.fromByteArray(b);
                if (objeto.getId() == id) {
                    arq.seek(pos);
                    arq.writeByte('*');
                    arq.close();
                    return true;
                }
            }
        }

        arq.close();
        return false;
    }

   //Atualiza o time que o usuario pedir do arquivo// 
    public static boolean Times_Update(Clube_futebol novoObjeto) throws IOException {
        RandomAccessFile arq = new RandomAccessFile("Trabalho_AED_Manipula_arq/Dados/Club_fut.db", "rw");
        arq.seek(4);
        long pos;
        byte lapide;
        byte[] b;
        byte[] novoB;
        int tam;
        Clube_futebol objeto;
        while (arq.getFilePointer() < arq.length()) {
            pos = arq.getFilePointer();
            lapide = arq.readByte();
            tam = arq.readInt();
            b = new byte[tam];
            arq.read(b);
            if (lapide != '*') {
                objeto = new Clube_futebol();
                objeto.fromByteArray(b);
                if (objeto.getId() == novoObjeto.getId()) {
                    novoB = novoObjeto.toByteArray();
                    if (novoB.length < tam) {
                        arq.seek(pos + 5);
                        arq.write(novoB);
                    } else {
                        arq.seek(pos);
                        arq.writeByte('*');
                        arq.seek(arq.length());
                        arq.writeByte(' ');
                        arq.writeInt(novoB.length);
                        arq.write(novoB);
                    }
                    arq.close();

                    return true;
                }
            }
        }
        arq.close();

        return false;
    }


    //Realiza a leitura de apenas um time do arquivo
    public static Clube_futebol read(int id) throws IOException {
        RandomAccessFile arq = new RandomAccessFile("Trabalho_AED_Manipula_arq/Dados/Club_fut.db", "rw");
        arq.seek(4);
        // pular cabecalho

        byte lapide;
        byte[] b;
        int tam;
        Clube_futebol objeto;
        while (arq.getFilePointer() < arq.length()) {
            lapide = arq.readByte();
            tam = arq.readInt();
            b = new byte[tam];
            arq.read(b);
            if (lapide != '*') {
                objeto = new Clube_futebol();
                objeto.fromByteArray(b);
                // System.out.println(objeto.getId());
                if (objeto.getId() == id) {
                    arq.close();
                    return objeto;
                    
                }
            }
        }
        arq.close();
        return null;
    }

}
