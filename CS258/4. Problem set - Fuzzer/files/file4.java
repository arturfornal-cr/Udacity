

package ignisv5;


import java.io.*;
import java.net.*;


public class Server extends Thread {

 int portNumber = 1234;
 String serverStatus = "Not yet started";

 Socket serverSocket = null;
 PrintWriter output = null;
 BufferedReader input = null;

 public void setPortNumber(int PN){
        this.portNumber = PN;
 }
 
 public int getPortNumber(){
     return portNumber;
 }

 public String getServerStatus(){
     return serverStatus;
 }

 public void setServerStatus(String status){
     serverStatus = status;
 }

 public void run(){

      

     try{
        ServerSocket serverSocket = new ServerSocket(portNumber);
        //created server socket to listen indefinitely and accept incoming requests. This is done by using the accept() method of ServerSocket class. When a request comes, accept() returns a Socket object representing the connection. In code it would be:
        setServerStatus("Waiting for client connection...");
        Socket incoming = serverSocket.accept();
        //InputStream incomingStream = new InputStream();
        //incomingStream = incoming.getInputStream();
        setServerStatus("Connection accepted...");
        BufferedReader input = new BufferedReader  (new InputStreamReader(incoming.getInputStream()));
        PrintWriter output = new PrintWriter  (incoming.getOutputStream(), true /* autoFlush */);

        boolean done = false;
        while (!done)
        {
          String messageline = input.readLine();
          if (messageline == null){
              done = true;
              setServerStatus("OFF");
          }
          else
          {
            output.println("Echo: " + messageline);
              setServerStatus("ON");
          }

          if (messageline.trim().equals("BYE")){
              done = true;
              setServerStatus("OFF ... Terminated by client.");
          }
        }

        incoming.close();

     }catch(Exception e){e.printStackTrace();}

 }





}
