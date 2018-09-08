import java.net.Socket;

public class ServerClient{
    Socket socket;
    String nickname;

    public ServerClient(Socket socket1, String nickname1){
        socket = socket1;
        nickname = nickname1;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getNickname() {
        return nickname;
    }
}