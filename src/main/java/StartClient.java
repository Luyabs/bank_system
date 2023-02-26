import ui.Login;
import ui.SetPosition;

import java.io.IOException;

public class StartClient {
    public static void main(String[] args) throws IOException {

        Login dialog = new Login();
        SetPosition.setFrameCenter(dialog);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
        System.exit(0);

    }
}
