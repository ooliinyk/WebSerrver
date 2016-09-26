package comn;

/**
 * Created by user on 26.09.2016.
 */
public class Resource {

//    public static void main(String[] args) throws IOException {
//        for( int i = 0; i < 3; i++ ) {
////            WebServer webServer =new WebServer();
//
//            Socket socket = new Socket("localhost", WebServer.DEFAULT_PORT);
//            sendMessage(socket);
//            readResponse(socket);
//            socket.close();
//        }
//    }
//
//    /**
//     * httpd.conf and mime.types are the default file names.
//     * You may specify which files to use, but they must have the
//     * correct extensions.
//     * For Example:
//     * Request request = new Request("HttpConfig.conf", "MimeFile.types");
//     */
//    public static void sendMessage(Socket socket) throws IOException {
//        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//        HttpRequest request = new HttpRequest();
//        request.parseRequestLine();
//        // Random message for now.
//        String message = request.getAllHeaders() + "\njpg => " + request.mimeTypeLookUp("jpg")
//                + "\nmp4 => " + request.mimeTypeLookUp("mp4");
//        out.println(message + "END");
//        System.out.println("-------------------------");
//        System.out.println("I just sent:\n" + message);
//    }
//
//    public static void readResponse(Socket socket) throws IOException {
//        BufferedReader response = new BufferedReader(
//                new InputStreamReader(socket.getInputStream())
//        );
//        String line;
//        while((line = response.readLine()) != null) {
//            System.out.println( "+ " + line);
//        }
//        System.out.println("-------------------------");
//    }
}
