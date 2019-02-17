import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TCPServer {
     
    
    public static void main (String args[]) {
        ArrayList<Conta> dadosConta =  new ArrayList<>();

           Conta eduardo = new Conta();

            eduardo.setNomePessoa("Eduardo Costa");
            eduardo.setNumberConta("1234");
            eduardo.setNumberAgencia("4321");
            eduardo.setSaldo(1500);
            eduardo.setExtra("");
            dadosConta.add(eduardo);

            //pessoa numero 2
            Conta emilly = new Conta();

            emilly.setNomePessoa("Emilly Kelly ");
            emilly.setNumberConta("5678");
            emilly.setNumberAgencia("8765");
            emilly.setSaldo(2000);
            emilly.setExtra("");
            dadosConta.add(emilly);


            try{
                    int serverPort = 7896; // the server port
                    ServerSocket listenSocket = new ServerSocket(serverPort);
                    while(true) {
                            Socket clientSocket = listenSocket.accept();
                            Connection c = new Connection(clientSocket, dadosConta);
                    }
            } catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
    }
  
}
class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
        ArrayList<Conta> dadosConta =  new ArrayList<>();
	public Connection (Socket aClientSocket, ArrayList<Conta>dadosContas) {
		try {
                        this.dadosConta = dadosContas;
			clientSocket = aClientSocket;
			in = new DataInputStream( clientSocket.getInputStream());
			out =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
                    } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
	}
        
        
public void run(){
                
             
    
                String op = null;
                String cont = null;
                String agen = null;
                String val = null;
               
            try {
               
                String data = in.readUTF();
                
                //TANSFORMANDO val em decimal//
                
        
                //separando a string data em partes;
                op = data.substring(0, 1);    //operação//////////
                cont = data.substring(1, 5); //numero da conta///
                agen = data.substring(5,9); //numero da agencia/
                val = data.substring(9);   //valor inserido////
                
                float val1 = Float.parseFloat(val);
               
                switch (op){
                    case "D":
                       dep (val1,cont);
                        break;

                    case "A":
                        //fazer saque
                        saq(val1, cont);
                        
                        break;
                    case "$":
                        //mostrar saldo
                        Saldo(cont);
                        break;

                    case "T":
                        //fazer transferencia
                       tranf (cont, agen, val);
                        break;  

                    case "E":
                        //fazer extrato
                        getExtr(cont);
                        break;

                    default:
                        out.writeUTF("Operacao invalida.");
                        break;
                }

        }catch (EOFException e){System.out.println("1EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("readline:"+e.getMessage());
        } finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
}
      


        
        //metodo para sabel o saldo
        public void Saldo(String cont){
             String a = null;
             int b=0;
            for (int i = 0; i < dadosConta.size(); i++){
                
                if (cont.equals(dadosConta.get(i).numberConta))
                {
                    try{ 
                        a = String.valueOf(dadosConta.get(i).getSaldo());  
                        out.writeUTF(a);
                    } catch (IOException io) {           
                    }                                                       
                }
                else{
                 b++;} 
                }
            if (b>=2){
                Merror();
            } 
        }// fim do metodo
        
        //metodo de depositar
        public void dep (float val, String cont ){
            int b = 0;
            for (int i = 0; i < dadosConta.size(); i++){              
                if (cont.equals(dadosConta.get(i).numberConta))
                {
                    try{  
                      dadosConta.get(i).setSaldo(val+dadosConta.get(i).saldo);    
                      out.writeUTF(Float.toString(dadosConta.get(i).saldo));
                      
                      //passando dados para o extrato
                      dadosConta.get(i).setExtra("Deposito: "+Float.toString(val)+
                                                "\n"+dadosConta.get(i).getExtra());  
                       } 
                    catch (IOException io) {                       
                    }     
                }
                else{
                    b++;
                }  
            }
            if (b>=2){
                Merror();
            }
            
        }//fim do metodo     
        
        
        //metodo de realização de saque        
        public void saq (float val, String cont ){
            int b=0;
            int s = 0;
            for (int i = 0; i < dadosConta.size(); i++){            
                if (cont.equals(dadosConta.get(i).numberConta)) { 
                    
                        if (val<=dadosConta.get(i).getSaldo()){
                            dadosConta.get(i).setSaldo(dadosConta.get(i).getSaldo()-val); 
                            
                            //passando o extrado
                            dadosConta.get(i).setExtra("Saque: "+Float.toString(val)+"\n"+dadosConta.get(i).getExtra());
                            
                            try {
                             out.writeUTF(Float.toString(dadosConta.get(i).saldo)); 
                            } catch (IOException e) {
                            }
                        }else {s++;}       
                }
                b++;
                if (s>0){noSald();}
            }
            if (b>=2){
                Merror();
            }
        }//fim do metodo    
        
        // tranferencia entre contas
        public void tranf (String cont, String cont2, String val){
            int a=4; int b=4; 
            float c = 0;
         
            c = Float.parseFloat(val);
            //getting account position
            for (int i = 0; i < dadosConta.size(); i++){
                if (cont.equals(dadosConta.get(i).numberConta)) {
                a = i;                  
                }  
                if (cont2.equals(dadosConta.get(i).numberConta)) {
                b = i;                  
                }                          
            }
            //verifying Availability of accounts
            if (a == 4 || b == 4){
                try {
                    out.writeUTF("ERRO");
                } catch (IOException e) {
                }
            }
            else{
            
                if (c<=dadosConta.get(a).saldo){
                    dadosConta.get(a).setSaldo(dadosConta.get(a).getSaldo()-c);
                    dadosConta.get(b).setSaldo(dadosConta.get(b).getSaldo()+c);
                
                    //passing operation to Extrato
                    dadosConta.get(a).setExtra("Transferido: "+val+" R$ para:"+
                             " "+dadosConta.get(b).getNumberConta()+ "\n"+dadosConta.get(a).getExtra());
                    
                    dadosConta.get(b).setExtra("Creditado: "+val+" R$ de:"+
                             " "+dadosConta.get(a).getNumberConta()+"\n"+dadosConta.get(b).getExtra());
                    
                    try {
                        out.writeUTF(val);
                    } 
                    catch (IOException e) {
                    }
                }// End of if (c<=dadosConta)
                else{
                      noSald();
                }    
            }//fim do else            
        }//fim do metodo tranferencia    
        
        //Extrato method
        private void getExtr(String cont){
           int a = 0;
            for (int i = 0; i<dadosConta.size(); i++){
                if (dadosConta.get(i).getNumberConta().equals(cont)){
                    try {
                        out.writeUTF(dadosConta.get(i).getExtra()+"\n"+"Seu Saldo Atual:"+
                                                   " "+dadosConta.get(i).getSaldo());
                    } catch (IOException e) {
                    }
                }else{a++;}
                if (a>=2){
                    Merror();
                }
            }
        }//End of method
        
    // Unexisting account method
        public void Merror(){
            try {
                out.writeUTF("ERRO");
            } catch (IOException e) {
            }
        
        }// end of method
        
        // No ballance method
        public void noSald(){
            try {
                out.writeUTF("NS");
            } catch (IOException e) {
            }     
        }// end of method
}