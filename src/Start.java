
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui.Login;
import util.Util;

import java.awt.*;

public class Start {
    private static Logger logger = LogManager.getLogger(Start.class);

    public static void main(String[] args){
        Util.getLog4j();

        //dispatch thread가 곧바로 처리
        EventQueue.invokeLater(() -> {
            logger.info("==========Start App===========");
            new Login().setVisible(true);
        });
    }
}
