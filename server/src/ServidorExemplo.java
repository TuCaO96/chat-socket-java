
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorExemplo {

    /**
     * @param args
     */
    static ServerSocket serversocket;

    static Socket client_socket;

    static Conexao c;

    static String msg;

    public ServidorExemplo() {
        try {
            serversocket = new ServerSocket(9600, 5);
            System.out.println("Criei o Server Socket");
        } catch (Exception e) {
            System.out.println("Nao criei o server socket...");
        }
    }

    public static void main(String args[]) {
        String texto, resposta;
        int operacao;

        // inicializa o banco central
        new ServidorExemplo();
        // cria um objeto para o send e o recv
        c = new Conexao();

		// inicializa a estrutura de armazenamento dos bancos
        while (true) {
            if (connect()) {

                // fica num loop de 5 mensagens
                for (int i = 1; i <= 5; i++) {
                    msg = c.receive(client_socket);

                    System.out.println("Mensagem " + i + " Recebida: " + msg);

                    c.send(client_socket, "Mensagem " + i + " Recebida com Sucesso.");
                }

                try {
                    client_socket.close();
                } catch (Exception e) {
                    System.out.println("N�o desconectei...");
                }

            } else {
                try {
                    serversocket.close();
                    break;
                } catch (Exception e) {
                    System.out.println("N�o desconectei...");
                }
            }
        }

    }

    static boolean connect() {
        boolean ret;
        try {
            client_socket = serversocket.accept();
            System.out.println("Porta: " + client_socket.getLocalPort() + " "
                    + client_socket.getPort());
            ret = true;
        } catch (Exception e) {
            System.out.println("Erro de connect..." + e.getMessage());
            ret = false;
        }
        return ret;
    }

}
