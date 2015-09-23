package me.binf.socks5.client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import me.binf.socks5.client.proxy.HexDumpProxy;
import me.binf.socks5.client.proxy.ProxyService;
import me.binf.socks5.client.proxy.ProxyServiceImpl;
import me.binf.socks5.client.utils.ConfigUtil;
import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class AppPanelController  {

    @FXML Label status;
    @FXML TextField remoteIp;
    @FXML TextField remotePort;
    @FXML TextField localPort;


    @FXML public void onSave() {

        String sPort = remotePort.getText();
        if(StringUtils.isBlank(sPort)){
            print("remote port will cannot null");
            return;
        }

        String sIp = remoteIp.getText();
        if(StringUtils.isBlank(sIp)){
            print("remote ip will cannot null");
            remoteIp.requestFocus();
            return;
        }


        String lPort =  localPort.getText();
        if(StringUtils.isBlank(lPort)){
            print("local port will cannot null");
            return;
        }

        ConfigUtil.updateProp("remote.port",sPort);
        ConfigUtil.updateProp("remote.ip",sIp);
        ConfigUtil.updateProp("local.port", lPort);

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ProxyService proxyService = new ProxyServiceImpl();
                        proxyService.start(Integer.valueOf(lPort),sIp,Integer.valueOf(sPort));
                    }catch (Exception e){
                       e.printStackTrace();
                    }
                }
            }).start();
        }catch (Exception e){
           e.printStackTrace();
        }

    }

    @FXML public void onStop() {
        ProxyService proxyService = new ProxyServiceImpl();
        proxyService.stop();
    }


    public void initConfSystem(String remoteIp,Integer remotePort,Integer localPort){
        this.remoteIp.setText(remoteIp);
        this.remotePort.setText(remotePort.toString());
        this.localPort.setText(localPort.toString());
    }



    private void print(String text){

        status.setText(text);
    }
}
