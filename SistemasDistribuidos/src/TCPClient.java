
import java.net.*;
import java.io.*;
import java.util.Scanner;
public class TCPClient {
    
        public static void telaInicial(){
            System.out.println("Escolha sua operação");
            System.out.println("Tecle D para depósito");        
            System.out.println("Tecle A para Saque");
            System.out.println("Tecle $ para Saldo");
            System.out.println("Tecle T para Tranferencia");
            System.out.println("Tecle E para Extrato");
            System.out.println("Digite sair para finalizar");
            System.out.println("=========================");    
        }
    
	public static void main (String args[]) {
                boolean j = true;
                Scanner tec = new Scanner (System.in); 
                
                while (j){
                
                telaInicial();
                String a = tec.nextLine();
                String b = null;
                String c = null;
                String d = null;
               
                if ("$".equals(a)){
                    System.out.println("=====SALDO====");
                    System.out.println("Insira o numero da Conta");
                    b = tec.nextLine();
                    System.out.println("Insira o numero da Agencia");
                    c = tec.nextLine();
                    d="0";
                }
                
                if ("A".equals(a)){
                    System.out.println("=====SAQUE====");
                    System.out.println("Insira o numero da Conta");
                    b = tec.nextLine();
                    System.out.println("Insira o numero da Agencia");
                    c = tec.nextLine();
                    System.out.println("Insira o Valor: ");
                    d = tec.nextLine();
                }
                
                if ("D".equals(a)){
                    System.out.println("=====DEPÓSITO====");
                    System.out.println("Insira o numero da Conta");
                    b = tec.nextLine();
                    System.out.println("Insira o numero da Agencia");
                    c = tec.nextLine();
                    System.out.println("Insira o Valor: ");
                    d = tec.nextLine();
                }
                
                if ("T".equals(a)){
                    System.out.println("=====TRANFERENCIA====");
                    System.out.println("Insira o numero da Conta");
                    b = tec.nextLine();
                    System.out.println("Insira o numero da Conta destino");
                    c = tec.nextLine();
                    System.out.println("Insira o Valor: ");
                    d = tec.nextLine();
                    
                }
                
                if ("E".equals(a)){
                    System.out.println("========EXTRATO=======");
                    System.out.println("Insira o numero da Conta");
                    b = tec.nextLine();
                    System.out.println("Insira o Numero da Agencia");
                    c = tec.nextLine();
                    d = "0";                    
                }
                
                if ("SAIR".equals(a)||"sair".equals(a)){
                    break;
                }
                
            
		Socket s = null;
                 
		try{
                    
			int serverPort = 7896;
			s = new Socket("127.0.0.1", serverPort);    
			DataInputStream in = new DataInputStream( s.getInputStream());
			DataOutputStream out =new DataOutputStream( s.getOutputStream());
			out.writeUTF(a+b+c+d); 	// UTF is a string encoding see Sn. 4.
			String data = in.readUTF(); // read a line of data from the stream                      
                     if (!"ERRO".equals(data) && !"NS".equals(data) ){
                        
                        if ("$".equals(a)){
                            System.out.println("-------SALDO-----------");
                            System.out.println("Conta: "+b+ " Agencia: "+c);
                            System.out.println("Seu saldo é de: "+data);
                            System.out.println("-----------------------");
                        }
                        
                        if ("T".equals(a)){
                            System.out.println("---------------TRANFERNCIA-------------");
                            System.out.println("Conta origem: "+b+ " Conta Destino: "+c);
                            System.out.println("Valor Tranferido: "+data);
                            System.out.println("---------------------------------------");
                        }
                        
                        if ("A".equals(a)){
                            System.out.println("---------SAQUE----------");
                            System.out.println("Conta: "+b+ " Agencia: "+c);
                            System.out.println("Valor sacado: "+d);
                            System.out.println("Seu saldo: "+data);
                            System.out.println("-------------------------");
                        }
                        
                        if ("D".equals(a)){
                            System.out.println("---------DEPÓSITO----------");
                            System.out.println("Conta: "+b+ " Agencia: "+c);
                            System.out.println("Valor depositado: "+d);
                            System.out.println("Seu saldo: "+data);
                            System.out.println("---------------------------");
                        }
                        
                        if ("E".equals(a)){
                            System.out.println("---------EXTRATO----------");
                            System.out.println(data);
                            System.out.println("==========================");
                        }
                        
                        
                        
                        
                        
                    }//fim do if ERRO
                     
                     else{
                         if ("NS".equals(data)){
                             System.out.println("------------------");  
                             System.out.println("Saldo Insuficiente");  
                             System.out.println("------------------");  
                         }else{
                             System.out.println("---------------------------------"); 
                             System.out.println("Erro ao digitar o numero da Conta!");
                             System.out.println("----------------------------------"); 
                         }
                     }    
                        
		}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){System.out.println("OPERAÇÃO INEXISTENTE");
		}catch (IOException e){System.out.println("readline:"+e.getMessage());
		}finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
     }}
        
}