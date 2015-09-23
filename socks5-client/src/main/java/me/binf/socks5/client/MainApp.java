package me.binf.socks5.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.binf.socks5.client.proxy.HexDumpProxy;
import me.binf.socks5.client.utils.ConfigUtil;
import me.binf.socks5.client.view.AppPanelController;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 *                                              g
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private VBox  rootLayout;

    @Override
    public void start(Stage primaryStage ) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SimpleShadowsocks");

        initRootLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/PaneLayout.fxml"));
            rootLayout =  loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            AppPanelController appPanelController = loader.getController();
            initConf(appPanelController);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void initConf(AppPanelController appPanelController){

        if(StringUtils.isNotBlank(ConfigUtil.getString("remote.ip"))
            &&StringUtils.isNotBlank(ConfigUtil.getString("remote.port"))
                &&StringUtils.isNotBlank(ConfigUtil.getString("local.port"))){
            String remoteIp = ConfigUtil.getString("remote.ip") ;
            Integer remotePort = Integer.valueOf(ConfigUtil.getString("remote.port"));
            Integer localPort = Integer.valueOf(ConfigUtil.getString("local.port"));
            appPanelController.initConfSystem(remoteIp, remotePort, localPort);
        }
    }



    public static void main( String[] args ) {
        launch(args);
    }

}
