import java.net.*;
import java.io.*;


public class ClienteExemplo extends Thread{

  static Conexao c;
  static Socket socket;
 
  public ClienteExemplo()
  {
    try {
      socket = new Socket("127.0.0.1",9600);
    }
    catch (Exception e)
    {
      System.out.println("Nao consegui resolver o host...");
    }
  }
  
    @SuppressWarnings("static-access")
  public static void main(String args[])
  {
      try{
          BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
          String msg;
          c = new Conexao();
          msg = in.readLine();
          c.send(socket, msg);
          Thread thread = new ClienteExemplo();
          thread.start();

          while(true){
              msg = in.readLine();
              c.send(socket, msg);
          }

      }catch (IOException e){
          System.out.println(e.getMessage());
      }
  }

    @Override
    public void run() {
        try {
            // DataInputStream in = new DataInputStream(System.in);
            String texto;

            while (true)
            {
                try{
                    texto = c.receive(socket);
                    System.out.println(texto);

                    if(texto.equals("/q")){
                        socket.close();
                    }
                }catch(Exception e)
                {
                    System.out.println("Erro na leitura "+e.getMessage());
                }

            }
        }
        catch (Exception e) {
            System.out.println("Olha o que houve: "+ e.getMessage());
        }
    }
}
