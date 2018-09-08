/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dirceu
 */
public class Main {

    public static ArrayList<ServerClient> clients;

    public static ArrayList<ServerClient> getClients(){
        return clients;
    }

    public static void main(String[] args) {

        try {
            
            Conexao conexao = new Conexao();
            clients = new ArrayList<>();
            
            ServerSocket server = new ServerSocket(9600, 5);

            while (true) {
                Socket socket = server.accept();
                System.out.println("Porta: " + socket.getLocalPort() + " "
                        + socket.getPort());
                conexao.send(socket, "Qual seu nickname?");
                String nickname = conexao.receive(socket);
                ServerClient client = new ServerClient(socket, nickname);
                clients.add(client);
                conexao.sendToAll(clients, client.getSocket().getPort(), nickname + " entrou");

                ThreadServer t = new ThreadServer(server, client);
                t.start();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
