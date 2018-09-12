import java.net.*;
import java.io.*;


public class ClienteExemplo extends Thread{

  static Conexao c;
  static Socket socket;
  private static boolean conectado = true;

  public ClienteExemplo()
  {
    try {
      socket = new Socket("localhost",9600);
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
          System.out.println("Precione a tecla enter para se conectar com o chat.");
          msg = in.readLine();
          c.send(socket, msg);
          Thread thread = new ClienteExemplo();
          thread.start();

          while(conectado){
              msg = in.readLine();
              c.send(socket, msg);
              if(msg.equals("/q")){
                  socket.close();
                  conectado = false;
              }
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

            while (conectado)
            {
                try{
                    texto = c.receive(socket);
                    System.out.println(texto);
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
