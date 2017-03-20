import com.automation.wazeServices.GoogleDriveClient;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by QTTEST on 13/03/2017.
 */
public class CopyAPKStep {

    @Test
    public void copyAPK() throws IOException
    {
        GoogleDriveClient googleDriveClient = new GoogleDriveClient();
        String result = googleDriveClient.downloadAPKFile("waze_qt_il.apk", "c:\\waze_qt_il.apk");
        System.out.println(result);
    }
}
