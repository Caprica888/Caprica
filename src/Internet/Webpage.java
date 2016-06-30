package Internet;

import Datatypes.InputDataStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Webpage {
    
    public static InputDataStream download( String urlAddress ) throws Exception {
        
        URL page = new URL( urlAddress );
        
        return new InputDataStream( page.openStream() );  
        
    }
    
}
