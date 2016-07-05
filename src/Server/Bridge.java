package Server;

import Server.Connection;
import System.Control;
import System.Output;
import System.Report;
import System.Subroutine;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Bridge {
    
    Socket auxiliarySocket;
    OutputStream auxiliaryStream;
    int currentPort = Constants.AUXILIARY_PORT;
    String serverIP;
    HashMap< String , Connection > subConnections = new HashMap<>();
    

    public Bridge( String inputServerIP ) throws IOException {
        
        Output.log( new Report( "Attempting connection to " + inputServerIP ) , "connection" );
        
        this.serverIP = inputServerIP;
        
        this.auxiliarySocket = new Socket( this.serverIP, 25561 );
        this.auxiliaryStream = this.auxiliarySocket.getOutputStream();
        
        new Subroutine( new CloseManager() );
        
        this.currentPort++;
        
        Output.log( new Report( "Connection successful " + inputServerIP ) , "connection" );
        
    }

    public Connection addConnection( String name ) throws IOException {

        Connection connection = new Connection( new Socket( this.serverIP , this.currentPort ) );
        
        this.subConnections.put( name, connection );
        
        this.currentPort++;
        
        return connection;
        
    }

    public class CloseManager extends Thread {
        
        @Override
        public void run() {
            
            while( true ){
            
                try {
                    
                    Bridge.this.auxiliaryStream.write( 1 );
                    Bridge.this.auxiliaryStream.flush();
                    
                }
                catch (Exception e) {
                    
                    Output.log( new Report(" Bridge closed to IP " + Bridge.this.serverIP ) , "connection" );
                    
                    break;
                    
                }
                Control.sleep(1);
            } 
            
        }
        
    }

}

