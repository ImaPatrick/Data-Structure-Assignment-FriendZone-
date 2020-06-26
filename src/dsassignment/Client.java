package dsassignment;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends javax.swing.JFrame {

    String Clientname, address = "localhost";
    ArrayList<String> clients = new ArrayList();
    int port_number = 1111;
    Boolean isConnected = false;
    String username, receiver;

    Socket S;
    BufferedReader reader;
    PrintWriter writer;
    LinkedList<String> list_messages = new LinkedList<>();
    Encryption encrypt = new Encryption();

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    public void clientAdd(String data) {
        clients.add(data);
    }

    public void clientRemove(String data) {
        chatShow.append(data + "is not online");
    }

    public void writeclients() {
        String[] temperoryList = new String[(clients.size())];
        clients.toArray(temperoryList);
        for (String token : temperoryList) {
            //clients.append(token + "\n");   
        }
    }

    public void send_Disconnect() {
        String bye = (Clientname + ": :Disconnect");
        {
            try {
                writer.println(bye);
                writer.flush();
            } catch (Exception ex) {
                chatShow.append("Cloud has not sent disconnect message. \n");
            }
        }

    }

    public void Disconnect() {
        try {
            chatShow.append("Client is Disconnected. \n");
            S.close();
        } catch (Exception e) {
            chatShow.append("Failed to disconnect the client. \n");
        }
        isConnected = false;
        txtClientName.setEditable(true);
    }

    public Client(String username, String receiver) throws Exception {

        this.username = username;
        this.receiver = receiver;
        initComponents();
    }

    public String getListMessages() {
        String str = "";
        for (int i = 0; i < list_messages.length(); i++) {
            str += list_messages.get(i);
        }
        return str;
    }

    public class IncomingReader implements Runnable {

        String username = txtClientName.getText();
        Random r = new Random();

        @Override
        public void run() {
            String[] dataset;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try {
                while ((stream = reader.readLine()) != null) {
                    dataset = stream.split(":");
                    if (dataset[2].equals(chat)) {
                        if (!dataset[0].equals(username)) {
                            for (int n = 0; n < word1.length; n++) {
                                dataset[1] = dataset[1].replaceAll(word1[n], word2[n]);
                            }
                            for (int n = 0; n < word3.length; n++) {
                                int a = r.nextInt(word4.length);
                                dataset[1] = dataset[1].replaceAll(word3[n], word4[a]);
                            }
                            chatShow.append(dataset[0] + " : " + dataset[1] + "\n");
                            chatShow.setCaretPosition(chatShow.getDocument().getLength());
                        } else {
                            chatShow.append(dataset[0] + " : " + dataset[1] + "\n");
                            chatShow.setCaretPosition(chatShow.getDocument().getLength());
                        }
                    } else if (dataset[2].equals(connect)) {
                        chatShow.removeAll();
                        clientAdd(dataset[0]);
                    } else if (dataset[2].equals(disconnect)) {
                        clientRemove(dataset[0]);
                    }
                    //clients.setText("");
                    writeclients();
                    clients.clear();
                }

            } catch (Exception e) {
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        connect_client = new javax.swing.JButton();
        client_name = new javax.swing.JLabel();
        chatBox = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        txtClientName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatShow = new javax.swing.JTextArea();
        btnDisconnect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        connect_client.setBackground(new java.awt.Color(255, 111, 97));
        connect_client.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        connect_client.setForeground(new java.awt.Color(255, 255, 255));
        connect_client.setText("Connect");
        connect_client.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connect_clientActionPerformed(evt);
            }
        });

        client_name.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        client_name.setText("Clientname");

        chatBox.setBackground(new java.awt.Color(255, 111, 97));
        chatBox.setForeground(new java.awt.Color(255, 255, 255));

        btnSend.setBackground(new java.awt.Color(255, 111, 97));
        btnSend.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnSend.setForeground(new java.awt.Color(255, 255, 255));
        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        txtClientName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClientNameActionPerformed(evt);
            }
        });

        chatShow.setBackground(new java.awt.Color(255, 111, 97));
        chatShow.setColumns(20);
        chatShow.setForeground(new java.awt.Color(255, 255, 255));
        chatShow.setRows(5);
        jScrollPane1.setViewportView(chatShow);

        btnDisconnect.setBackground(new java.awt.Color(255, 111, 97));
        btnDisconnect.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnDisconnect.setForeground(new java.awt.Color(255, 255, 255));
        btnDisconnect.setText("Disconnect");
        btnDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisconnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chatBox, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(client_name)
                        .addGap(3, 3, 3)
                        .addComponent(txtClientName, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(connect_client, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDisconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(client_name)
                    .addComponent(txtClientName)
                    .addComponent(connect_client)
                    .addComponent(btnDisconnect))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chatBox, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed

        String nothing_Found = "";
        if ((chatBox.getText()).equals(nothing_Found)) {
            chatBox.setText("");
            chatBox.requestFocus();
        } else {
            try {

                writer.println(Clientname + ":" + chatBox.getText() + ":" + "Chat");
                list_messages.addNode(chatBox.getText() + " %% ");
                encrypt.setListMessages(chatBox.getText() + " %% ");
                writer.flush(); // this command will flush the buffer.
            } catch (Exception e) {
                chatShow.append("Failed to send the message! \n");
            }
            chatBox.setText("");
            chatBox.requestFocus();
        }
        chatBox.setText("");
        chatBox.requestFocus();

    }//GEN-LAST:event_btnSendActionPerformed

    private void txtClientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClientNameActionPerformed

    }//GEN-LAST:event_txtClientNameActionPerformed

    private void connect_clientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connect_clientActionPerformed
        if (isConnected == false) {
            txtClientName.setText(username);
            Clientname = txtClientName.getText();
            txtClientName.setEditable(false);

            try {
                S = new Socket(address, port_number);
                InputStreamReader streamreader = new InputStreamReader(S.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(S.getOutputStream());
                writer.println(Clientname + ":the client is connected: client");
                writer.flush();
                isConnected = true;
            } catch (Exception e) {
                chatShow.append("Cannot connect to the server at this moment! Please try again later!! \n");
                txtClientName.setEditable(true);
            }
            ListenThread();

        } else if (isConnected == true) {
            chatShow.append("You are already connected to the server. \n");
        }
    }//GEN-LAST:event_connect_clientActionPerformed

    private void btnDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisconnectActionPerformed
        try {
            encrypt.getListMessages();
            encrypt.getSender(username);
            encrypt.getReceiver(receiver);
            encrypt.launch();
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        send_Disconnect();
        Disconnect();

    }//GEN-LAST:event_btnDisconnectActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    public void launch() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Client(username, receiver).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void connect() {
        if (isConnected == false) {
            txtClientName.setText(username);
            Clientname = txtClientName.getText();
            txtClientName.setEditable(false);

            try {
                S = new Socket(address, port_number);
                InputStreamReader streamreader = new InputStreamReader(S.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(S.getOutputStream());
                writer.println(Clientname + ":the client is connected: client");
                writer.flush();
                isConnected = true;
            } catch (Exception e) {
                chatShow.append("Cannot connect to the server at this moment! Please try again later!! \n");
                txtClientName.setEditable(true);
            }
            ListenThread();

        } else if (isConnected == true) {
            chatShow.append("You are already connected to the server. \n");
        }
    }
    private String[] word1 = {"(?i)pretty", "(?i)girl", "(?i)love", "(?i)yes", "(?i)date", "(?i)movie", "(?i)sorry", "(?i)eyes", "(?i)great"};
    private String[] word2 = {"ugly", "monkey!", "hate", "no", "one night stand", "f**k", "sorry, it's a prank!", "ass", "awful"};
    private String[] word3 = {"(?i)smart", "(?i)pretty", "(?i)cute", "(?i)lovely", "(?i)beautiful"};
    private String[] word4 = {"ugly", "dumb", "smelly", "retarded", "bitchy", "annoying", "clingy", "thot", "disgusting", "fake", "selfish", "cold"};
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDisconnect;
    private javax.swing.JButton btnSend;
    private javax.swing.JTextField chatBox;
    private javax.swing.JTextArea chatShow;
    private javax.swing.JLabel client_name;
    private javax.swing.JButton connect_client;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtClientName;
    // End of variables declaration//GEN-END:variables
}
