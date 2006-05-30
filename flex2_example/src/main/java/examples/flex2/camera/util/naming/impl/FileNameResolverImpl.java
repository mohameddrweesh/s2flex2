package examples.flex2.camera.util.naming.impl;

import examples.flex2.camera.util.naming.FileNameResolver;


public class FileNameResolverImpl implements FileNameResolver {

    public String getFileName(Object object) {
        return "" + System.currentTimeMillis();
    }
}
