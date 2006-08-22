package examples.flex2.camera.snapshot.config;


public class SnapshotServiceConfig {

    private String prefix;

    private String rootPath;

    private String rootUri;

    private String suffix;

    public SnapshotServiceConfig(){
        rootPath = "E:\\profile\\s2flex\\workspace2\\flex2_example_hotdeploy\\snapshot\\";
        rootUri = "http://localhost/flex2_example_hotdeploy/snapshot/";
        prefix = "snapshot_";
        suffix = ".png";
    }
    
    public String getPrefix() {
        return prefix;
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getRootUri() {
        return rootUri;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public void setRootUri(String rootUri) {
        this.rootUri = rootUri;
    }

    public void setSuffix(String suffixType) {
        this.suffix = suffixType;
    }
}
