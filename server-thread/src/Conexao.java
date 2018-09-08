import java.net.*;
import java.io.*;
import java.util.List;

public class Conexao {

    public void send(Socket socket, String txt) {
        OutputStream out;
        try {
            out = socket.getOutputStream();
            out.write(txt.getBytes());

        } catch (Exception e) {
            System.out.println("Deu pau no OutputStream: " + e.getMessage());
        }
    }

    public void sendTo(Socket socket){

    }

    public void sendToAll(List<ServerClient> clients, int senderPort, String txt){
        for(int i = 0; i < clients.size(); i++){
            Socket socket = clients.get(i).getSocket();
            if(socket.getPort() != senderPort){
                OutputStream out;
                try{
                    out = socket.getOutputStream();
                    out.write(txt.getBytes());
                    out.flush();
                }
                catch (IOException e){System.out.println(e.getMessage());}
            }
        }
    }

    public String receive(Socket socket) {
        InputStream in;
        int bt;
        byte btxt[];
        String txt = "";
        btxt = new byte[79];
        try {
            in = socket.getInputStream();
            bt = in.read(btxt);

            if (bt > 0) {
                txt = new String(btxt);
                txt = txt.substring(0, bt);
            }
        } catch (Exception e) {
            System.out.println("Excecao na leitura: " + e.getMessage());
        }
        return txt;
    }

}
