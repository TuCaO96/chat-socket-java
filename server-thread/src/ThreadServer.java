/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author dirceu
 */
public class ThreadServer extends Thread {

    private final ServerSocket server;
    private final ServerClient client;
    private Conexao conexao;
    private int type = 0; //0 for send to all, 1 to send for one
    private String selectedNickname;

    public ThreadServer(ServerSocket server, ServerClient client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {
        conexao = new Conexao();

        while (true) {
            String msg = conexao.receive(client.getSocket());

            conexao.send(client.getSocket(), "Mensagem recebida com sucesso!");

            if(msg.equals("/list")){
                conexao.send(client.getSocket(), "Lista de participantes: \n");
                for(int i = 0; i < Main.getClients().size(); i++){
                    conexao.send(client.getSocket(), " - " + Main.getClients().get(i).getNickname() + "\n");
                }
            }
            else if (msg.startsWith("/all")){
                type = 0;
            }
            else if(msg.startsWith("/p")){
                type = 1;
                selectedNickname = msg.substring(3);
            }
            else{
                if(type == 0){
                    conexao.sendToAll(Main.getClients(), client.getSocket().getPort(),  client.getNickname() + " disse: " + msg);
                }
                else{
                    ServerClient privateClient = null;
                    for(int i = 0; i < Main.getClients().size(); i++){
                        if(Main.getClients().get(i).getNickname().toLowerCase().equals(selectedNickname.toLowerCase())){
                            privateClient = Main.getClients().get(i);
                            break;
                        }
                    }
                    if(privateClient == null){
                        conexao.send(client.getSocket(), "Usuário não encontrado!");
                        continue;
                    }
                    conexao.send(privateClient.getSocket(), "[PRIVADO] " + client.getNickname() + " disse: " + msg);
                }
            }
            System.out.println(client.getNickname() + " disse: " + msg + "\n");

        }

    }

}
