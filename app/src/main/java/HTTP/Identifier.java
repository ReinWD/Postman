package HTTP;

import java.net.URL;

/**
 * Created by 张巍 on 2017/1/21.
 * 检测输入信息并生成相应的对象
 */

public class Identifier {

    private int protocolNum,mode;

    public Identifier(URL url) {
        protocolDetector(url);

    }

    //通过检测开头来确定请求模式（1为HTTP，2为HTTPS）

    private int protocolDetector(URL url) {
        String judgement;
        judgement = url.getPath().toLowerCase();
        if (judgement.isEmpty()) {
            protocolNum = 0;
        } if (judgement.startsWith("https")) {
            protocolNum = 2;
        } else if (judgement.startsWith("http")) {
            protocolNum = 1;
        } else protocolNum = 0;
        return protocolNum;
    }

    private int ModeDetector(){
        return 0;
    }


}