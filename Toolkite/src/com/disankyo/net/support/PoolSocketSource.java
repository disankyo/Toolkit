package com.disankyo.net.support;

import com.disankyo.log.SyncLogUtil;
import com.disankyo.net.SocketSource;
import com.disankyo.net.SocketValidate;
import com.disankyo.properties.Properties;
import com.disankyo.properties.impl.CommonProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.ObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;

/**
 * 
 * @version 1.00 2012-4-1 11:18:12
 * @since 1.6
 * @author allean
 */
@SuppressWarnings("unchecked")
public class PoolSocketSource implements SocketSource {

    private static final SyncLogUtil LOG = new SyncLogUtil(LogFactory.getLog(PoolSocketSource.class));
    private Config localConfig;
    private boolean hasInit;
    private ObjectPool pool;
    
    /**
     * 构造一个新的Socket对象池实例
     */
    public PoolSocketSource() {
    }

    /**
     * 用URL指定文件所在路径来初始化连接池
     * @param url 连接池的属性配置信息
     * @throws java.lang.IOException
     */
    public PoolSocketSource(URL url) throws Exception {
        this(url.openStream());
    }

    /**
     * 用一个类的路径来初始化连接池
     * @param classPath 类的路径
     * @throws java.lang.IOException
     */
    public PoolSocketSource(String classPath) throws Exception {
        this(PoolSocketSource.class.getResourceAsStream(classPath));
    }

    /**
     * 用本地文件来初始化连接池
     * @param file 本地文件
     * @throws IOException
     */
    public PoolSocketSource(File file) throws Exception {
        this(new FileInputStream(file));
    }

    /**
     * 实际构造对象池的构造方法，以一个输入流构造配置文件
     * @param stream 配置文件的输入流
     * @throws java.lang.IOException
     */
    public PoolSocketSource(InputStream stream) throws Exception {
        Properties pro = new CommonProperties();
        pro.load(stream);
        stream.close();
        init(pro);
    }

    /**
     * 初始化
     * @param pro properties文件
     */
    private void init(Properties pro) throws Exception {
        if(!hasInit){
            localConfig = new Config(pro);

            GenericObjectPool.Config poolConfig = initPoolConfig(localConfig);
            PoolableSocketFactory  socketFactory = new PoolableSocketFactory();
            ObjectPoolFactory poolFactory = new GenericObjectPoolFactory(socketFactory, poolConfig);
            pool = poolFactory.createPool();

            hasInit = true;
        }
    }

    /**
     * 构造GenericObjectPool.Config实例
     * @param localConfig 
     * @return
     */
    private GenericObjectPool.Config initPoolConfig(Config localConfig){
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        poolConfig.maxActive = localConfig.getMaxActive();
        poolConfig.minIdle = localConfig.getMinIdle();
        poolConfig.maxIdle = localConfig.getMaxIdle();
        poolConfig.whenExhaustedAction = localConfig.getWhenExhaustedAction();
        poolConfig.maxWait = localConfig.getMaxWait();
        poolConfig.timeBetweenEvictionRunsMillis = localConfig.getTimeBetweenEvictionrunsMillis();
        poolConfig.minEvictableIdleTimeMillis = localConfig.getMinEvictableIdleTimeMillis();
        poolConfig.testWhileIdle = localConfig.isTestWhileIdle();
        poolConfig.testOnBorrow = localConfig.isTestOnBorrow();
        poolConfig.testOnReturn = localConfig.isTestOnReturn();

        return poolConfig;
    }

    /**
     * 从数据池中获取套接字
     * @return 返回需要的对象
     * @throws java.lang.Exception
     */
    @Override
    public Socket getSocket() throws Exception {
        if(hasInit){
            Socket socket = (Socket) pool.borrowObject();
            LOG.debugLog("Get Socket {0} from the pool.", socket);
            
            return socket;
        } else {
            throw new IllegalStateException("Not completed initialization.");
        }
    }

    /**
     * Socket实现
     */
    private class SocketImpl extends Socket{

        /**
         * 该方法是将Socket归还对象池，而非真正的关闭。
         * @throws IOException
         */
        @Override
        public synchronized void close() throws IOException {
            try {
                pool.returnObject(this);
            } catch (Exception ex) {
                LOG.errorLog(ex);
            }
        }
        
        /**
         * 实际执行的close方法
         * @throws IOException
         */
        public void doClose() throws IOException{
            super.close();
        }
    }
    
    /**
     * 生成对象的对象池实现
     */
    private class PoolableSocketFactory extends BasePoolableObjectFactory{
        
        /**
         * 生成新的Socket放到线程池
         * @return
         * @throws Exception
         */
        @Override
        public Object makeObject() throws Exception {
            SocketImpl socket = new SocketImpl();
            socket.setKeepAlive(localConfig.isKeepAlive());
            socket.setReceiveBufferSize(localConfig.getReceiveBufferSize());
            socket.setReuseAddress(localConfig.isResuseAddress());
            socket.setSendBufferSize(localConfig.getSendBufferSize());
            socket.setSoTimeout(localConfig.getTimeout());
            socket.setTcpNoDelay(localConfig.isNoDelay());

            if(localConfig.getLinger() > 0){
                socket.setSoLinger(true, localConfig.getLinger());
            }

            if(localConfig.getTrafficClass() > 0){
                socket.setTrafficClass(localConfig.getTrafficClass());
            }

            socket.connect(new InetSocketAddress(
                    localConfig.getHost(),
                    localConfig.getPort()),
                    localConfig.getConnectionTimeout());
            
            return socket;
        }

        /**
         * 用于销毁被validateObject判定为失效的对象
         * @param obj
         * @throws Exception
         */
        @Override
        public void destroyObject(Object obj) throws Exception {
            if(obj != null && SocketImpl.class.isInstance(obj)){
                SocketImpl socket = (SocketImpl) obj;
                socket.doClose();
            }
        }

        /**
         * 用于校验一个对象是否仍然有效，对于失效的对象交由destoryObject销毁
         * @param obj
         * @return
         */
        @Override
        public boolean validateObject(Object obj) {
            if(obj == null){
                LOG.debugLog("The Object is null.");
                return false;
            }

            SocketImpl socket = null;
            if(!SocketImpl.class.isInstance(obj)){
                LOG.debugLog("The Object is not inherits with SocketImpl.");
                return false;
            } else {
                socket = new SocketImpl();
                boolean validate = localConfig.getValidate().validate(socket);
                if(validate){
                    LOG.debugLog("Effective socket {0}.", socket);
                } else {
                    LOG.warnLog("Invalid socket {0}.", socket);
                }

                return validate;
            }
        }

    }

    /**
     * 配置信息处理类
     */
    private class Config {

        //配置套接字的属性
        //主机的地址
        private static final String SOCKET_HOST = "socket.host";
        //主机的端口
        private static final String SOCKET_PORT = "socket.port";
        //是否关闭Socket的缓冲
        private static final String SOCKET_NODELAY = "socket.nodelay";
        //一个进程关闭Scoket后，另一个进程是否能重用相同的接口
        private static final String SOCKET_RESUSE_ADDRESS = "socket.resuseAddress";
        //用来设置如果输入流如果没有数据时的最大等待时间 单位:毫秒数
        private static final String SOCKET_TIMEOUT = "socket.timeout";
        //建立新连接时，超时等待的时间  单位:毫秒数
        private static final String SOCKET_CONNECTION_TIMEOUT = "socket.connection.timeout";
        //用来设置Socket发送数据后，当调用close方法时，底层Socket的等待时间
        private static final String SOCKET_LINGER = "socket.liner";
        //用来设置ocket输入数据缓冲区的大小
        private static final String SOCKET_RECEIVE_BUFFER_SIZE = "socket.receiveBuffer";
        //用来设置Socket输出数据缓冲区的大小
        private static final String SOCKET_SEND_BUFFER_SIZE = "socket.sendBuffer";
        //用来设置底层的TCP实现是否监视该连接是否有效
        private static final String SOCKET_KEEP_ALIVE = "socket.keepAlive";
        //设置的连接服务类型，可用的值为{"inexpensive","reliable","throughput","delay"},
        //依次代表成本低，高可靠性，吞吐量和延迟 
        private static final String SOCKET_TRAFFIC_CLASS = "socket.trafficClass";
        //检查连接是否有效时调用的接口实现
        private static final String SOCKET_TEST_CLASS = "socket.testClass";
        //配置连接池的属性
        //用来设置从Socket池中借出对象的最大数目
        private static final String POOL_MAX_ACTIVE = "pool.maxActive";
        //用来设置Socket池中最小空闲数
        private static final String POOL_MIN_IDLE = "pool.minIdle";
        //用来设置Stock池中最大空闲数
        private static final String POOL_MAX_IDLE = "pool.maxIdle";
        //用来设置线程池已达目标极限的情况下，调用borrowObject方法的行为。
        //0表示抛出一个java.util.NoSuchElementException异常，1表示等待，2表示创建一个新实例（不过就使maxActive参数失去意义）
        private static final String POOL_EXHAUSTED_ACTION = "pool.exhaustedAction";
        //用来设置若对象池为空时调用borrowObject方法的行为被设定为等待，最大等待的毫秒数，
        //若超过这个数值，则抛出一个java.util.NoSuchElementException，若为整数，则表示无限期等待
        private static final String POOL_MAX_WAIT = "pool.maxWait";
        //设定时间间隔多长时间对后台对象进行一次清理行为，
        private static final String POOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS = "pool.timeBetweenEvictionRunsMillis";
        //延迟驱逐线程判断对象池中的对象是否过期的最小空闲时间的毫秒值
        private static final String POOL_MIN_EVICTABLE_IDLE_TIME_MILLIS = "pool.minEvictableIdleTimeMillis";
        //设定在后台进行对象清理时，是否对对象池中未过期的对象进行有效性检查，若检查通不过的对象也将被回收。
        private static final String POOL_TEST_WHILE_IDLE = "pool.testWhileIdle";
        //设定在借出对象时是否进行有效性检查
        private static final String POOL_TEST_ON_BORROW = "pool.testOnBorrow";
        //设定在归返对象时是否进行有效性检查
        private static final String POOL_TEST_ON_RETURN = "pool.testOnReturn";

        private Properties pro;
        //================socket属性配置
        private String host;
        private int port;
        private boolean noDelay;
        private boolean resuseAddress;
        private int timeout;
        private int connectionTimeout;
        private int linger;
        private int receiveBufferSize;
        private int sendBufferSize;
        private boolean keepAlive;
        private int trafficClass;
        private SocketValidate validate;
        //===============pool属性配置
        private int maxActive;
        private int minIdle;
        private int maxIdle;
        private byte whenExhaustedAction;
        private int maxWait;
        private long timeBetweenEvictionrunsMillis;
        private long minEvictableIdleTimeMillis;
        private boolean testWhileIdle;
        private boolean testOnBorrow;
        private boolean testOnReturn;

        public Config(Properties pro) throws Exception {
            this.pro = pro;

            //==========socket属性
            host = this.pro.getValue(SOCKET_HOST);
            port = pro.getIntValue(SOCKET_PORT);
            noDelay = pro.getBooleanValue(SOCKET_NODELAY);
            resuseAddress = pro.getBooleanValue(SOCKET_RESUSE_ADDRESS);
            timeout = pro.getIntValue(SOCKET_TIMEOUT);
            connectionTimeout = pro.getIntValue(SOCKET_CONNECTION_TIMEOUT);
            linger = pro.getByteValue(SOCKET_LINGER);
            receiveBufferSize = pro.getIntValue(SOCKET_RECEIVE_BUFFER_SIZE);
            sendBufferSize = pro.getIntValue(SOCKET_SEND_BUFFER_SIZE);
            keepAlive = pro.getBooleanValue(SOCKET_KEEP_ALIVE);
            trafficClass = pro.getIntValue(SOCKET_TRAFFIC_CLASS);
            String className = pro.getValue(SOCKET_TEST_CLASS);
            validate = (SocketValidate) Class.forName(className).newInstance();
            
            //=========pool属性
            maxActive = pro.getIntValue(POOL_MAX_ACTIVE);
            minIdle = pro.getIntValue(POOL_MIN_IDLE);
            maxIdle = pro.getIntValue(POOL_MAX_IDLE);
            whenExhaustedAction = pro.getByteValue(POOL_EXHAUSTED_ACTION);
            maxWait = pro.getIntValue(POOL_MAX_WAIT);
            timeBetweenEvictionrunsMillis = pro.getLongValue(POOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
            minEvictableIdleTimeMillis = pro.getLongValue(POOL_MIN_EVICTABLE_IDLE_TIME_MILLIS);
            testWhileIdle = pro.getBooleanValue(POOL_TEST_WHILE_IDLE);
            testOnBorrow = pro.getBooleanValue(POOL_TEST_ON_BORROW);
            testOnReturn = pro.getBooleanValue(POOL_TEST_ON_RETURN);
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public byte getWhenExhaustedAction() {
            return whenExhaustedAction;
        }

        public void setWhenExhaustedAction(byte whenExhaustedAction) {
            this.whenExhaustedAction = whenExhaustedAction;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public boolean isKeepAlive() {
            return keepAlive;
        }

        public void setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
        }

        public int getLinger() {
            return linger;
        }

        public void setLinger(int linger) {
            this.linger = linger;
        }

        public int getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(int maxWait) {
            this.maxWait = maxWait;
        }

        public long getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        public void setMinEvictableIdleTimeMillis(
                long minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public boolean isNoDelay() {
            return noDelay;
        }

        public void setNoDelay(boolean noDelay) {
            this.noDelay = noDelay;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getReceiveBufferSize() {
            return receiveBufferSize;
        }

        public void setReceiveBufferSize(int receiveBufferSize) {
            this.receiveBufferSize = receiveBufferSize;
        }

        public boolean isResuseAddress() {
            return resuseAddress;
        }

        public void setResuseAddress(boolean resuseAddress) {
            this.resuseAddress = resuseAddress;
        }

        public int getSendBufferSize() {
            return sendBufferSize;
        }

        public void setSendBufferSize(int sendBufferSize) {
            this.sendBufferSize = sendBufferSize;
        }

        public boolean isTestOnBorrow() {
            return testOnBorrow;
        }

        public void setTestOnBorrow(boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
        }

        public boolean isTestOnReturn() {
            return testOnReturn;
        }

        public void setTestOnReturn(boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
        }

        public boolean isTestWhileIdle() {
            return testWhileIdle;
        }

        public void setTestWhileIdle(boolean testWhileIdle) {
            this.testWhileIdle = testWhileIdle;
        }

        public long getTimeBetweenEvictionrunsMillis() {
            return timeBetweenEvictionrunsMillis;
        }

        public void setTimeBetweenEvictionrunsMillis(
                long timeBetweenEvictionrunsMillis) {
            this.timeBetweenEvictionrunsMillis = timeBetweenEvictionrunsMillis;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getTrafficClass() {
            return trafficClass;
        }

        public void setTrafficClass(int trafficClass) {
            this.trafficClass = trafficClass;
        }

        public SocketValidate getValidate() {
            return validate;
        }

        public void setValidate(SocketValidate validate) {
            this.validate = validate;
        }
    }
}
