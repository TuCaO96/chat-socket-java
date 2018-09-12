/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
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
    private int type; //0 for send to all, 1 to send for one
    private String selectedNickname;
    private String fraseEscrita; // frase sem o comando -- o protocolo
    private int tamanho = 0;
    ServerClient privateCliente = null;
    private boolean conectado = true;

    public ThreadServer(ServerSocket server, ServerClient client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {
        conexao = new Conexao();

        while (conectado) {
            String msg = conexao.receive(client.getSocket());

            conexao.send(client.getSocket(), "Mensagem recebida com sucesso!");

            if(msg.equals("/list")){
                conexao.send(client.getSocket(), "Lista de participantes: \n");
                for(int i = 0; i < Main.getClients().size(); i++){
                    conexao.send(client.getSocket(), " - " + Main.getClients().get(i).getNickname() + "\n");
                }
            }
            else if (msg.startsWith("/all")){
                String msgFormatada = msg.substring(5);
                conexao.sendToAll(Main.getClients(), client.getSocket().getPort(),  client.getNickname() + " disse: " + msgFormatada);
            }
            else if(msg.startsWith("/p")){

                fraseEscrita = msg.substring(3);

                ServerClient privateClient = null;
                for(int i = 0; i < Main.getClients().size(); i++){
                    System.out.println(Main.getClients().get(i).getNickname());

                    String nomeCompar = Main.getClients().get(i).getNickname();
                    tamanho = nomeCompar.length();
                    selectedNickname =  fraseEscrita.substring(0, tamanho);

                    if(Main.getClients().get(i).getNickname().toLowerCase().equals(selectedNickname.toLowerCase())){
                        privateClient = Main.getClients().get(i);
                        //break;
                    }
                }
                if(privateClient == null){
                    conexao.send(client.getSocket(), "Usuário não encontrado!");
                }
                String[] arrayValores = msg.split(" ");
                String msgFormatada = " ";
                for (int i = 2; i < arrayValores.length; i++) {
                    msgFormatada+= " " + arrayValores[i];
                    System.out.println(arrayValores[i]);
                }
                conexao.send(privateClient.getSocket(), "[PRIVADO] " + client.getNickname() + " disse:" + msgFormatada);
            }else if(msg.startsWith("/q")){
                try {
                    conexao.sendToAll(Main.getClients(), client.getSocket().getPort(),  client.getNickname() + " saiu.");

                    for(int i = 0; i < Main.getClients().size(); i++){
                        if(Main.getClients().get(i).getNickname().equals(client.getNickname())){
                            Main.getClients().remove(i);
                        }
                    }
                    conectado = false;
                    client.getSocket().close();

                } catch (IOException e) {
                    System.out.println("Ops! Ocorreu alguma falha ao se desconectar com o chat.");
                }
            }else {
                conexao.send(client.getSocket(), "Comando não reconhecido!\nPor gentileza tente novamente");
                conexao.send(client.getSocket(), "Os comandos aceitos são:\n - /all sua mensagem aqui.\n - /p destinatario sua mensagem aqui.\n - /q (para sair do chat)");
            }
            System.out.println(client.getNickname() + " disse: " + msg + "\n");

        }

    }

}
