package org.seasar.flex2.util.data.transfer;

import org.seasar.flex2.util.data.transfer.storage.Storage;

public interface Transfer {

    public abstract void importTo(Storage storage, Object target);

    public abstract void exportTo(Storage storage, Object target);

}