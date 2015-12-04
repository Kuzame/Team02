/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager;

/**
 *
 * @author Reign
 */
import FanManager.model.Fan;
import FanManager.model.FanGroup;
import FanManager.model.FanPane;
import FanManager.view.FanManagerLayoutController;
import java.io.*;  
import java.net.*; 
import java.util.Date;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;


public class TCPClient implements Runnable {  

    private ObjectOutputStream toClient;
    private DataInputStream fromClient;
    private ServerSocket server;
    private Socket client;

    private FanManagerLayoutController mainApp;
    private FanGroup fanGroup;
    private ObservableList<Fan> fanList;
    private FanPane [] fanPanes;

    
    public TCPClient(FanManagerLayoutController mainApp) {
        this.mainApp = mainApp;
        fanList = this.mainApp.getFanList();
        fanGroup = this.mainApp.getFanGroup();
        fanPanes = this.mainApp.getFanPanes();
    }

    @Override
    public void run() {
        try {
            // Create a socket to connect to the server
            server = new ServerSocket(8001);
            System.out.println("Wifi started at " + new Date() + '\n');
            client = server.accept();
            System.out.println("client = Server accepted");

            // Create an output stream to send data to the server
            toClient = new ObjectOutputStream(client.getOutputStream());
            System.out.println("toClient: " + toClient + '\n');
            toClient.flush();
            System.out.println("toClient flush: " + toClient + '\n');
 
            // Create an input stream to receive data from the server
            fromClient = new DataInputStream(client.getInputStream());
            System.out.println("fromClient: " + fromClient + '\n');
//                System.out.println(fromClient.readObject());

            fanList.addListener(new ListChangeListener() {
                @Override
                public void onChanged(ListChangeListener.Change change) {
                    System.out.println("Data changed");
                    sendData();
                }
            });
            
            // Receive server data
            Thread recieveData = new Thread(() -> recieveData());
            recieveData.setDaemon(true);
            recieveData.start();
            
//            String sentence;   String modifiedSentence;   
//            System.out.println("Inside aThis TCPClient RUN!");
//
//            BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));   
//            Socket clientSocket = new Socket("10.0.1.32", 8000);
//
//            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());   
//            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());   
//
//            outToServer.writeBytes("hi");   
//
//            sentence = (String) inFromServer.readObject(); 
//            System.out.println("FROM SERVER: " + sentence); 
//
//            clientSocket.close();  

           } catch (ConnectException ce) {
               System.err.println(ce);
           } catch (Exception ex) {
               System.out.println("Bad TCPClient Connection");
                              System.err.println(ex);

           }
        }
    
        private void recieveData() {
        try {
            while (true) {
                System.out.println("Waiting for object");
                // Read from network
                int theObject = fromClient.read();
//                FanGroup theObject = (FanGroup) fromClient.readObject();
//    System.setErr(new PrintStream(Console.getInstance(theObject)));
                
                
                System.out.println("Recieved char");
                System.out.println(theObject);

                // If new fanGroup sent over, then update each fan
                if (!fanGroup.equals(theObject)) {
//                    fanGroup.update(theObject);
                    
                    // Update fan animations
                    for (int i = 0; i < fanGroup.getFans().size(); i++) {
                        if (!fanList.get(i).equals(fanGroup.getFans().get(i))) {
                            fanPanes[i].setFan(fanGroup.getFans().get(i));
                            fanPanes[i].updateGauge();
                        }
                    }
                }

                Thread.sleep(10);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                client.close();
                fromClient.close();
                toClient.close();
            } catch (SocketException ex) {
                System.err.println(ex);
                System.out.println("Fan Server Error: Finally Block");
            } catch (IOException ex) {
                System.err.println(ex);
            }

        }
    }

    private void sendData() {
        try {
                toClient.reset();
                System.out.println("sending data");
                fanGroup = mainApp.getFanGroup();
                //System.out.println(fanGroup.getFans().get(0).getSpeed());

                // Write to network
                toClient.writeObject(fanGroup);
                toClient.flush();
                
                System.out.println("data sent");

                
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
    }
    } 