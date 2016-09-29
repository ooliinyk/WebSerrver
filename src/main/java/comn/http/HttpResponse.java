package comn.http;

import comn.HttpDConfig;
import comn.MimeType;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * HttpResponse class defines the HTTP Response Status Line (method, URI, version)
 * and Headers http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html
 */
public class HttpResponse {

    private static Logger log = Logger.getLogger(HttpResponse.class);

    public static final String VERSION = "HTTP/1.0";

    List<String> headers = new ArrayList<String>();

    byte[] body;

    public HttpResponse(HttpRequest req) throws IOException {

        switch (req.method) {
            case HEAD:
                fillHeaders(Status._200);
                break;
            case GET:

                String documentRoot = new HttpDConfig("httpd.conf").getConfigurationMap().get("DocumentRoot").get(0);

                try {
                    fillHeaders(Status._200);
                    File file = new File(documentRoot + req.uri);
                    System.out.println(documentRoot + req.uri);
                    if (file.isDirectory()) {
                        headers.add(ContentType.HTML.toString());
                        StringBuilder result = new StringBuilder("<html><head><title>Index of ");
                        result.append(req.uri);
                        result.append("</title></head><body><h1>Index of ");
                        result.append(req.uri);
                        result.append("</h1><hr><pre>");

                        File[] files = file.listFiles();
                        for (File subfile : files) {
                            result.append(" <a href=\"" + subfile.getPath() + "\">" + subfile.getPath() + "</a>\n");
                        }
                        result.append("<hr></pre></body></html>");
                        fillResponse(result.toString());
                    } else if (file.exists()) {
                        setContentType(req.uri, headers);
                        fillResponse(getBytes(file));
                    } else {
                        log.info("File not found:1" + req.uri);
                        fillHeaders(Status._404);
                        fillResponse(Status._404.toString());
                    }
                } catch (Exception e) {
                    log.error("Response Error", e);
                    fillHeaders(Status._400);
                    fillResponse(Status._400.toString());
                }

                break;
            case POST:
                break;
            case PUT:

                break;
            case UNRECOGNIZED:
                fillHeaders(Status._400);
                fillResponse(Status._400.toString());
                break;
            default:
                fillHeaders(Status._501);
                fillResponse(Status._501.toString());
        }

    }

    private byte[] getBytes(File file) throws IOException {
        int length = (int) file.length();
        byte[] array = new byte[length];
        InputStream in = new FileInputStream(file);
        int offset = 0;
        while (offset < length) {
            int count = in.read(array, offset, (length - offset));
            offset += count;
        }
        in.close();
        return array;
    }

    private void fillHeaders(Status status) {
        headers.add(HttpResponse.VERSION + " " + status.toString());
        headers.add("Connection: close");
        headers.add("Server: My");
    }

    private void fillResponse(String response) {
        body = response.getBytes();
    }

    private void fillResponse(byte[] response) {
        body = response;
    }

    public void write(OutputStream os) throws IOException {

        DataOutputStream output = new DataOutputStream(os);
        for (String header : headers) {
            output.writeBytes(header + "\r\n");
        }
        output.writeBytes("\r\n");
        if (body != null) {
            output.write(body);
        }
        output.writeBytes("\r\n");
        output.flush();
    }

    private void setContentType(String uri, List<String> list) {
        try {
            String ext = uri.substring(uri.indexOf(".") + 1);
            MimeType mimeType = new MimeType("mime.types");
//			String str = ext.toLowerCase().toString()
            mimeType.getMimeTypesMap().get(ext.toLowerCase());
            list.add("Content-type: " + mimeType.getMimeTypesMap().get(ext.toLowerCase()));

//			list.add(ContentType.valueOf(ext.toUpperCase()).toString());
        } catch (Exception e) {
            log.error("ContentType not found: " + e, e);
        }
    }

//    private void processPUT(HttpRequest myRequest, OutputStream out) throws IOException{
//
//
//        String fileSeparator = System.getProperty("file.separator");
//        String documentRoot =  new HttpDConfig("httpd.conf").getConfigurationMap().get("DocumentRoot").get(0);
//        File PUTFile = new File(myRequest.uri);
//        if(PUTFile.exists()){
//            FileWriter fstream = new FileWriter(PUTFile);// fileWriter will write to this
//            BufferedWriter putToFile = new BufferedWriter(fstream);
//
//            if(myRequest.messageBody.isEmpty()){
//                setStatus(204);
//                writeOutput(out,myRequest,new File(documentRoot + fileSeparator + NO_CONTENT_FILE));
//            }
//            else{
//                for(int i = 0;i<=myRequest.messageBody.size();i++){
//                    String temp = myRequest.messageBody.get(i);
//                    putToFile.write(temp);
//                    setStatus(201);
//                    writeOutput(out,myRequest,PUTFile);
//                }
//            }
//
//            putToFile.close();
//        }
//        else{
//            statusCode = 404;
//            writeOutput(out, myRequest, new File(documentRoot + fileSeparator + PAGE_NOT_FOUND_FILE));
//        }
//    }
}
