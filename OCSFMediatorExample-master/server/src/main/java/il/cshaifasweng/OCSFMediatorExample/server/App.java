package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App
{
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException
    {
        server = new SimpleServer(3000);
        System.out.println("server is listening");
        server.listen();

	}

}

