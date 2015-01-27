package cscie55.hw6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by Sohail on 4/24/14.
 */
public class Server {

    private ATMThread[] threads;
    public static LinkedList<ATMRunnable> taskQueue;
    private ServerSocket serverSocket;
    private BufferedReader bufferedReader;
    private ATMImplementation atmImplementation;

    public Server(int port) throws java.io.IOException {
        serverSocket = new ServerSocket(port);

        taskQueue = new LinkedList<ATMRunnable>();
        threads = new ATMThread[5];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ATMThread();
            threads[i].start();
        }

        atmImplementation = new ATMImplementation();

    }

    //ExecutorService pool = Executors.newFixedThreadPool(5);

    /** serviceClient accepts a client connection and reads lines from the socket.
     *  Each line is handed to executeCommand for parsing and execution.
     */
    public void serviceClient() throws java.io.IOException {
        System.out.println("Accepting clients now");
        Socket clientConnection = serverSocket.accept();
        // Arrange to read input from the Socket
        InputStream inputStream = clientConnection.getInputStream();
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        // Arrange to write result across Socket back to client
        OutputStream outputStream = clientConnection.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        System.out.println("Client acquired on port #" + serverSocket.getLocalPort() + ", reading from socket");
        String commandLine;
        while((commandLine = bufferedReader.readLine()) != null) {
            ATMRunnable task = new ATMRunnable(commandLine, printStream, atmImplementation);
            synchronized (taskQueue) {
                taskQueue.addLast(task);
                taskQueue.notifyAll();
            }
        }
    }

    public static void main(String argv[]) {
        int port = 1099;
        if(argv.length > 0) {
            try {
                port = Integer.parseInt(argv[0]);
            } catch (Exception e) { }
        }
        try {
            Server server = new Server(port);
            server.serviceClient();
            System.out.println("Client serviced");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
