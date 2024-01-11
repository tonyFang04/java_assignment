import DatabaseException.DBException;
import Parsing.Command;
import Parsing.Directory;
import Tokenizing.Tokenizer;

import java.io.*;
import java.net.*;

class DBServer
{
    public DBServer(int portNumber)
    {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while(true) processNextConnection(serverSocket);
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextConnection(ServerSocket serverSocket)
    {
        try {
            Directory databaseDirectory = new Directory();
            Socket socket = serverSocket.accept();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Connection Established");
            while(true) processNextCommand(socketReader, socketWriter, databaseDirectory);
        } catch(IOException ioe) {
            System.err.println(ioe);
        } catch(NullPointerException npe) {
            System.out.println("Connection Lost");
        }
    }

    private void processNextCommand(BufferedReader socketReader, BufferedWriter socketWriter, Directory databaseDirectory) throws IOException, NullPointerException
    {
        String incomingCommand = socketReader.readLine();
        try {
            Tokenizer tokenizer = new Tokenizer();
            tokenizer.tokenizeIncomingCommands(incomingCommand);
            Command parseCommand = new Command(tokenizer,databaseDirectory);
            parseCommand.parse();
            socketWriter.write(parseCommand.getOutput());
        } catch (DBException e) {
            socketWriter.write(e.toString());
        }
        socketWriter.write("\n" + ((char)4) + "\n");
        socketWriter.flush();
    }

    public static void main(String args[])
    {
        DBServer server = new DBServer(8888);
    }

}
