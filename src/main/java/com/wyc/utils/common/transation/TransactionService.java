package com.wyc.utils.common.transation;

import java.util.concurrent.Callable;

public interface TransactionService {

    void begin();

    void commit();

    void rollback();

    <V> V execute(Callable<V> callable);

}
