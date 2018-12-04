package project.components.googleAPI;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import org.springframework.stereotype.Component;
import project.tables.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class GoogleFiles {

    private static File _createGoogleFile(String googleFolderIdParent, String customFileName,
                                          AbstractInputStreamContent uploadStreamContent)
            throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(customFileName);
        List<String> parents = Arrays.asList(googleFolderIdParent);
        fileMetadata.setParents(parents);
        Drive driveService = GoogleDriveUtils.getDriveService();
        File file = driveService.files().create(fileMetadata, uploadStreamContent)
                .setFields("id, webContentLink, webViewLink, parents").execute();
        return file;
    }

    private static File createGoogleFile(String googleFolderIdParent, String contentType,
                                        String customFileName, java.io.File uploadFile) throws IOException {
        AbstractInputStreamContent uploadStreamContent = new FileContent(contentType, uploadFile);
        return _createGoogleFile(googleFolderIdParent, customFileName, uploadStreamContent);
    }

    private static Permission createPermissionForEmail(String googleFileId, String googleEmail) throws IOException {
        String permissionType = "user";
        String permissionRole = "reader";
        Permission newPermission = new Permission();
        newPermission.setType(permissionType);
        newPermission.setRole(permissionRole);
        newPermission.setEmailAddress(googleEmail);
        Drive driveService = GoogleDriveUtils.getDriveService();
        return driveService.permissions().create(googleFileId, newPermission).execute();
    }

    public static Permission createPublicPermission(String googleFileId) throws IOException {
        String permissionType = "anyone";
        String permissionRole = "reader";
        Permission newPermission = new Permission();
        newPermission.setType(permissionType);
        newPermission.setRole(permissionRole);
        Drive driveService = GoogleDriveUtils.getDriveService();
        return driveService.permissions().create(googleFileId, newPermission).execute();
    }

    public void setFile(java.io.File uploadFile, project.tables.File fileInDb, User user) throws IOException {
        File googleFile = createGoogleFile(null, "",
                uploadFile.getName(), uploadFile);
        //System.out.println("WebViewLink: " + googleFile.getWebViewLink());
        fileInDb.setGoogleLink(googleFile.getWebViewLink());
        //createPermissionForEmail(googleFile.getId(), user.getEmail());
        createPublicPermission(googleFile.getId());
    }

}
