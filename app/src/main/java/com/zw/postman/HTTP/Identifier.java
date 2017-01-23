package com.zw.postman.HTTP;

import java.net.URL;

/**
 * Created by 张巍 on 2017/1/21.
 * 检测输入信息并生成相应的对象
 */

public class Identifier {

    private int protocolNum, mode;

    public Identifier(URL url) {
        ModeDetector(protocolDetector(url));
    }

    //通过检测开头来确定请求模式（1为HTTP，2为HTTPS）

    private int protocolDetector(URL url) {
        String judgement;
        judgement = url.toString().toLowerCase();
        if (judgement.isEmpty()) {
            protocolNum = 0;
        }
        if (judgement.startsWith("https")) {
            protocolNum = 2;
        } else if (judgement.startsWith("http")) {
            protocolNum = 1;
        } else protocolNum = 0;
        return protocolNum;
    }

    private void ModeDetector(int modes) {
        switch (modes) {
            case 1:
                System.out.println("HTTP");
                mode = 1;
                break;
            case 2:
                System.out.println("HTTPS");
                mode = 2;
                break;
            default:
                System.out.println("模式错误");
                break;
        }
    }


    public int getMode() {
        return mode;
    }
}
