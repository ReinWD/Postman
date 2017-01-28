package com.zw.postman.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by 张巍 on 2017/1/21.
 * 检测输入信息并生成相应的对象
 */

public class HttpRequest {
    /**
     * declaration.
     */
    private URL mRequestUrl;
    private String mMethod;
    private String mProtocol;



    private int protocolNum;

    /**
     * A constructor
     * nothing important...
     *
     * @param url     urls input from outside(your goal).
     * @param methods HTTP request mMethod (your selection in MainActivity)
     */
    public HttpRequest(URL url, String methods, String protocol) {

            ModeDetector(url, methods, protocol);

                setRequestUrl(url);

            }


    //通过检测开头来确定请求协议（1为HTTP，2为HTTPS）

    /**
     * Select mode for your input.
     *
     * @param url     urls input from outside(your goal).
     * @param methods HTTP request mMethod (your selection in MainActivity)
     */
    private void ModeDetector(URL url, String methods, String protocol) {

        //协议检测
        String judgement;
        judgement = url.toString().toLowerCase();
        if (judgement.isEmpty()) {
        } else if (judgement.startsWith("https")) {
            System.out.println("HTTPS");
            protocolNum = 2;
        } else if (judgement.startsWith("http")) {
            System.out.println("HTTP");
            protocolNum = 1;
        } else {mProtocol = protocol.replace("://", "");
        if(mProtocol.startsWith("https"))protocolNum = 2;else protocolNum = 1;}
        //设定工作模式
        setMethod(methods);
    }

    /**
     * @return *0:unknown (throw IOException)
     * *1:HTTP
     * *2:HTTPs
     */
    public int getProtocol() {
        return protocolNum;
    }
    /**
     * setter for
     *
     * @param mRequestUrl Meow~
     */
    private void setRequestUrl(URL mRequestUrl) {
        this.mRequestUrl = mRequestUrl;
    }

    /**
     * setter for
     *
     * @param mMethod Meow~
     */
    private void setMethod(String mMethod) {
        this.mMethod = mMethod;
    }

    /**
     * Initialize URLConnections and send request.
     *
     * @return *Messages if connection is successful.
     * *Response code and error message if connection fails.
     * @throws IOException if given URL is wrong.
     */

    public String Request() throws IOException {
        int responseCode;
        String result;
        String message;
        try {
        switch (protocolNum) {

                case 1:
                    HttpURLConnection mHttpRequest = (HttpURLConnection) mRequestUrl.openConnection();
                    mHttpRequest.getPermission();
                    mHttpRequest.setRequestMethod(mMethod);
                    mHttpRequest.setConnectTimeout(5000);
                    mHttpRequest.setReadTimeout(10000);

                    responseCode = mHttpRequest.getResponseCode();
                    message = mHttpRequest.getResponseMessage();
                    System.out.println(responseCode);
                    System.out.println(message);
                    result = response(mHttpRequest, responseCode);

                    if (result.equals(" ")) {
                        result = String.valueOf(responseCode) + "\n" + message;
                    }
                    break;
                case 2:
                    HttpsURLConnection mHttpSRequest = (HttpsURLConnection) mRequestUrl.openConnection();
                    mHttpSRequest.getPermission();
                    mHttpSRequest.setRequestMethod(mMethod);
                    mHttpSRequest.setConnectTimeout(5000);
                    mHttpSRequest.setReadTimeout(10000);

                    responseCode = mHttpSRequest.getResponseCode();
                    message = mHttpSRequest.getResponseMessage();
                    System.out.println(responseCode);
                    System.out.println(message);
                    result = response(mHttpSRequest, responseCode);

                    if (result.equals(" ")) {
                        result = String.valueOf(responseCode) + "\n" + message;
                    }
                    break;
                default:
                    result = "Failed to identify http or https";
                    break;
            }
        }catch (SocketTimeoutException e){
            System.out.println("连接超时");
            result = "Connect Time Out!";
        }
        return result;
    }

    /**
     * Response according to response code
     * refer to http://http.cats (for fun)
     * or https://en.wikipedia.org/wiki/List_of_HTTP_status_codes (for more information)
     *
     * @param responseCode response code from former request.
     * @param connection   URLConnection used in Request() above.
     * @return "" when we failed to receive information we expected.
     * information we want if we got great progress.
     */

    private String response(URLConnection connection, int responseCode) {

        switch (responseCode) {
            case 200://OK
                return readData(connection);
            case 201://Created
                break;
            case 202://Accepted
                break;
            case 203://Non-Authoritative Information
                break;
            case 204://No Content
                break;
            case 205://Reset Content
                break;
            case 206://Partial Content
                break;
            case 300://Multiple Choices
                break;
            case 301://Moved Permanently
                break;
            case 302://Temporary Redirect
                break;
            case 303://See Other
                break;
            case 304://Not Modified
                break;
            case 305://Use Proxy
                break;
            case 400://Bad Request
                break;
            case 401://Unauthorized
                break;
            case 402://Payment Required
                break;
            case 403://Forbidden
                break;
            case 404://Not Found
                break;
            case 405://Not Allowed
                break;
            case 406://Not Acceptable
                break;
            case 407://Proxy Authentication Required
                break;
            case 408://Request Time Out
                break;
            case 409://Conflict
                break;
            case 410://Gone
                break;
            case 411://Length Required
                break;
            case 412://Precondition Failed
                break;
            case 413://Request Entity too Large
                break;
            case 414://Request URI too Large
                break;
            case 415://Unsupported Media Type
                break;
            case 500://Internal Server Error
                break;
            case 501://Not Implemented
                break;
            case 502://Bad Gateway
                break;
            case 503://Service Unavailable
                break;
            case 504://Gateway Timeout
                break;
            case 505://HTTP Version Not Supported
                break;
            default:
                break;
        }
        return " ";
    }

    /**
     * read data from response InputStream
     *
     * @param connection URLConnection used in response()
     * @return results from target
     */
    private String readData(URLConnection connection) {
        InputStream inputStream;
        OutputStream outputStream;
        int cache;
        try {
            inputStream = connection.getInputStream();
            outputStream = new ByteArrayOutputStream();
            while ((cache = inputStream.read()) != -1) {
                outputStream.write(cache);
            }
            String result = outputStream.toString();
            inputStream.close();
            outputStream.close();
            return result;
        } catch (IOException e
                ) {
            e.printStackTrace();
        }
        return "";
    }

}
