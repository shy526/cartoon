package com.github.shy526.cartoon.pool;

import com.github.shy526.poll.MyGenericObjectPool;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * javascript 脚本执行器创建工厂
 *
 * @author Administrator
 */
public class ScriptEngineFactory extends BasePooledObjectFactory<ScriptEngine> {

    @Override
    public ScriptEngine create() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        return manager.getEngineByName("javascript");
    }

    @Override
    public PooledObject<ScriptEngine> wrap(ScriptEngine scriptEngine) {
        return new DefaultPooledObject<>(scriptEngine);
    }

    public static MyGenericObjectPool<ScriptEngine> newObjectPool() {
        ScriptEngineFactory personPoolFactory = new ScriptEngineFactory();
        GenericObjectPoolConfig<ScriptEngine> objectPoolConfig = new GenericObjectPoolConfig<>();
        objectPoolConfig.setMaxTotal(5);
        return new MyGenericObjectPool<>(personPoolFactory, objectPoolConfig);
    }

    public static void main(String[] args) {
        MyGenericObjectPool<ScriptEngine> poll = newObjectPool();
        poll.leaseObject(item -> System.out.println("item = " + item));
    }

}
