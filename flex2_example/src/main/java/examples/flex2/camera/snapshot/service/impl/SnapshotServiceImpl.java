package examples.flex2.camera.snapshot.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.data.ByteArray;
import org.seasar.framework.util.FileOutputStreamUtil;

import examples.flex2.camera.snapshot.Snapshot;
import examples.flex2.camera.snapshot.service.SnapshotService;
import examples.flex2.camera.snapshot.service.SnapshotServiceConfig;
import examples.flex2.camera.util.naming.FileNameResolver;

public class SnapshotServiceImpl implements SnapshotService {

    private FileNameResolver fileNameResolver;
    
    private SnapshotServiceConfig serviceConfig;

    public FileNameResolver getFileNameResolver() {
        return fileNameResolver;
    }

    public SnapshotServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public String save(Snapshot snapshot) {
        ByteArray bytearray = snapshot.getSource();
        byte[] buffer = bytearray.getBufferBytes();
        File file = createSnapshotFile();
        FileOutputStream fileOutputSteam = FileOutputStreamUtil.create(file);
        try {
            fileOutputSteam.write(buffer);
            fileOutputSteam.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputSteam != null) {
                try {
                    fileOutputSteam.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return serviceConfig.getRootUri() + file.getName();
    }

    public void setFileNameResolver(FileNameResolver fileNameResolver) {
        this.fileNameResolver = fileNameResolver;
    }


    public void setServiceConfig(SnapshotServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }


    private final String createFileName() {
        return serviceConfig.getPrefix() + fileNameResolver.getFileName(null) + serviceConfig.getSuffix();
    }


    private final File createSnapshotFile() {
        File saveDir = new File(serviceConfig.getRootPath());
        if( !saveDir.exists()){
            saveDir.mkdir();
        }
        return new File(serviceConfig.getRootPath() + createFileName());
    }
}
