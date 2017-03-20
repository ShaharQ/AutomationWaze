package com.automation.wazeServices;


import com.waze.automation.gdrive.services.GoogleDriveService;

import java.io.IOException;

/**
 * Created by QTTEST on 13/03/2017.
 */
public class GoogleDriveClient {

    private GoogleDriveService googleDriveService;

    public GoogleDriveClient() throws IOException
    {
        googleDriveService = new GoogleDriveService();
    }

    public String downloadAPKFile(String fileName, String downloadTo)
    {
        return googleDriveService.downloadFileByName(fileName,downloadTo).name();
    }




}
