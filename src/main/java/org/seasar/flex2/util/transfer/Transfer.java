package org.seasar.flex2.util.transfer;

import org.seasar.flex2.util.transfer.storage.Storage;

public interface Transfer {

    void importToData(Storage storage, Object target);

    void exportToStorage(Object target, Storage storage);
}