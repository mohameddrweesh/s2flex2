package examples.flex2.camera.snapshot;

import org.seasar.flex2.rpc.amf.data.ByteArray;

public class Snapshot {
    private ByteArray source;

    public ByteArray getSource() {
        return source;
    }

    public void setSource(ByteArray source) {
        this.source = source;
    }
}
