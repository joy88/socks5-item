import java.io.*;
import java.net.Socket;

/**
 * Created by wangbin on 2015/10/18.
 */
public class SocketHandler implements Runnable {

    final static String CRLF ="\r\n";

    private Socket clientSocket;

    public SocketHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void handleSocket(Socket clientSocket)throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(clientSocket.getOutputStream())),true);

        String requestHeader = "";
        String s ;

        while ((s=in.readLine())!=null){
            s+=CRLF;
            requestHeader = requestHeader +s;
            if(s.equals(CRLF)){
                break;
            }
        }
        System.out.println("客户端请求头：");
        System.out.println(requestHeader);
        String responseBody = "<html><head><title>hello</title></head><body><h1>helloWorld</h1></body></html>";

        String responseHeader = "HTTP/1.0 200 OK\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: "+responseBody.getBytes().length+"\r\n" +
                "\r\n";
        System.out.println("响应头：");
        System.out.println(responseHeader);

        out.write(responseHeader);
        out.write(responseBody);
        out.flush();

        out.close();
        in.close();
        clientSocket.close();

    }


    @Override
    public void run() {
        try {
            handleSocket(clientSocket);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }


}


