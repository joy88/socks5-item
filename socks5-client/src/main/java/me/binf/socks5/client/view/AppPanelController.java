package me.binf.socks5.client.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import me.binf.socks5.client.service.ProxyService;
import me.binf.socks5.client.service.ProxyServiceImpl;
import me.binf.socks5.client.utils.ConfigUtil;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 */
public class AppPanelController extends BaseController{

    @FXML Label status;
    @FXML TextField remoteIp;
    @FXML TextField remotePort;
    @FXML TextField localPort;


    @FXML public void onSave() {

        String sPort = remotePort.getText();
        if(StringUtils.isBlank(sPort)){
            notification("远程端口号不能为空!");
            return;
        }

        String sIp = remoteIp.getText();
        if(StringUtils.isBlank(sIp)){
            notification("远程IP不能为空");
            remoteIp.requestFocus();
            return;
        }


        String lPort =  localPort.getText();
        if(StringUtils.isBlank(lPort)){
            notification("本地端口不能为空");
            return;
        }

        ConfigUtil.updateProp("remote.port",sPort);
        ConfigUtil.updateProp("remote.ip",sIp);
        ConfigUtil.updateProp("local.port", lPort);

        try {
            ProxyService proxyService = ProxyServiceImpl.getInstance(this);

            proxyService.start(Integer.valueOf(lPort),sIp,Integer.valueOf(sPort));


        }catch (Exception e){
           e.printStackTrace();
        }

    }


    public void initConfSystem(String remoteIp,Integer remotePort,Integer localPort){
        this.remoteIp.setText(remoteIp);
        this.remotePort.setText(remotePort.toString());
        this.localPort.setText(localPort.toString());
    }


    @Override
    public void notification(String message) {
            Platform.runLater(() -> {
                try {
                    status.setText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }


}
