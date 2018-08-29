import java.net.*;
import java.io.*;


public class ClienteExemplo {

  static Conexao c;
  static Socket socket;
 
  public ClienteExemplo()
  {
    try {
      socket = new Socket("10.1.1.10",9600);
    }
    catch (Exception e)
    {
      System.out.println("Nao consegui resolver o host...");
    }
  }
  
    @SuppressWarnings("static-access")
  public static void main(String args[])
  {
     String msg;
     String texto;
     new ClienteExemplo();
     
     c = new Conexao();
     
     // DataInputStream in = new DataInputStream(System.in);
     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

     // fica num loop de 5 mensagens
     for(int i=0;i<5;i++)
     {
     	try{
     		msg = in.readLine();
     		c.send(socket,msg);
     		texto = c.receive(socket);
     		System.out.println(texto);
     	}catch(Exception e)
     	{
     		System.out.println("Erro na leitura "+e.getMessage());
     	}
     	
     }
     
     
     try {
       socket.close(); }
     catch (Exception e) {
       System.out.println("Nao desconectei..."+e);
     }
     
     
  }
}
