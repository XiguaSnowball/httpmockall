//package com.mryx.data.olapquery.service.impl;
//
//import com.google.common.collect.Lists;
//import com.mryx.data.olapquery.config.DataSourceConstant;
//import com.mryx.data.olapquery.dao.model.OlapTaskQueue;
//import com.mryx.data.olapquery.enums.OlapTaskStatusEnum;
//import com.mryx.data.olapquery.service.CacheService;
//import com.mryx.data.olapquery.service.OlapTaskExecuteService;
//import com.mryx.data.olapquery.service.OlapTaskQueueService;
//import com.mryx.data.olapquery.service.TairService;
//import com.mryx.monitor.api.BusinessMonitor;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.ResultSetExtractor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.UnknownHostException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.concurrent.*;
//import java.util.stream.Collectors;
//
//@Service
//public class OlapTaskExecuteServiceImpl implements OlapTaskExecuteService {
//
//    private static Logger logger = LoggerFactory.getLogger(OlapTaskExecuteServiceImpl.class);
//
//    private static final String OLAP_TASK_QUEUE = "OLAP_TASK_QUEUE";
//    private static final String OLAP_TASK_QUEUE_LOCK = "OLAP_TASK_QUEUE_LOCK";
//    private static ExecutorService threadPool = null;
//    private final String tair_pre = "tair_";
//
//    @Value("${olap.max.task.num:3}")
//    private int maxTaskNum;
//    private volatile boolean needRun;
//    private String IP = null;
//    @Resource
//    private CacheService cacheService;
//
//    @Resource
//    private OlapTaskQueueService olapTaskQueueService;
//    @Autowired
//    @Qualifier("sparkJdbcTemplate2")
//    private JdbcTemplate jdbcTemplate;
//    @Resource
//    private TairService tairService;
//
//    @PostConstruct
//    public void init() {
//        try {
//            threadPool = new ThreadPoolExecutor(maxTaskNum, maxTaskNum, 0, TimeUnit.MILLISECONDS,
//                    new SynchronousQueue<>(), new CustomizableThreadFactory("olap-task-thread-pool-"));
//            updateTaskByServicStartup();
//        } catch (Exception e) {
//            logger.error("init olap task queue error", e);
//        }
//    }
//
//    /**
//     * 执行任务
//     */
//    @SuppressWarnings("unchecked")
//    private void executeTask() {
//        try {
//            int activeCount = ((ThreadPoolExecutor)threadPool).getActiveCount();
//            for (int i = 1; needRun && i <= maxTaskNum - activeCount; i++) {
//                threadPool.execute(() -> {
//                    try {
//                        while (needRun) {
//                            Object taskId = cacheService.forList().rightPop(OLAP_TASK_QUEUE);
//                            if (taskId != null) {
//                                process(Long.parseLong(taskId.toString()));
//                            }
//                            loadTask();
//                            needRun = cacheService.hasPureKey(OLAP_TASK_QUEUE) || cacheService.forList().size(OLAP_TASK_QUEUE) != 0;
//                        }
//                    } catch (Exception e) {
//                        logger.error("任务执行过程中发生异常", e);
//                    } finally {
//                        needRun = cacheService.hasPureKey(OLAP_TASK_QUEUE) || cacheService.forList().size(OLAP_TASK_QUEUE) != 0;
//                    }
//                });
//            }
//        } catch (RejectedExecutionException re) {
//            logger.error("任务提交被拒绝", re);
//        } catch (Exception e) {
//            logger.error("任务提交发生异常", e);
//        }
//    }
//
//    /**
//     * 任务处理
//     *
//     * @param taskId
//     */
//    @SuppressWarnings({ "unchecked", "rawtypes" })
//    private void process(Long taskId) {
//        OlapTaskQueue task = null;
//        try {
//            logger.info("**************** 开始执行，任务ID： {} *********************", taskId);
//            task = olapTaskQueueService.selectOlapTaskQueueById(taskId);
//            if (task == null) {
//                logger.info("任务已不存在，任务ID：{}", taskId);
//                return;
//            }
//            if (task.getTaskStatus().intValue() != OlapTaskStatusEnum.WAIT.getValue().intValue()) {
//                logger.info("任务非排队中状态，任务ID：{}，status：{}", task.getId(), task.getTaskStatus());
//                return;
//            }
//
//            task.setExeMachine(getIP());
//            task.setBeginExeTime(new Date());
//            task.setTaskStatus(OlapTaskStatusEnum.RUNNING.getValue());
//            int updateRows = updateTaskStatus(task, Lists.newArrayList(OlapTaskStatusEnum.WAIT.getValue()));
//            if (updateRows <= 0) {
//                logger.info("无开始运行中状态修改，任务ID：{}", task.getId());
//                return;
//            }
//
//
////			Random rand = new Random();
////			Thread.sleep(rand.nextInt(100000 - 50000 + 1) + 50000);
////			Thread.sleep(5000l);
//
//
//            final String orderParamMd5 = task.getOrderParamMd5();
//            final String unorderParamMd5 = task.getUnorderParamMd5();
//            int rowNum = (int)jdbcTemplate.query(task.getOrderSql(), new ResultSetExtractor() {
//                @Override
//                public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
//                    try {
//                        return tairService.addTairData(orderParamMd5, unorderParamMd5, rs);
//                    } catch (Exception e) {
//                        logger.error("********** 数据添加Tair发生错误，任务ID：{} ***********", taskId, e);
//                    }
//                    return -1;
//                }
//            });
////			int rowNum = 100;
//            String tairDataKey = orderParamMd5;
//            if(rowNum <= DataSourceConstant.LIMIT_SIZE){
//                tairDataKey = task.getUnorderParamMd5();
//            }
//            task.setRowNum(new Long(rowNum));
//            task.setEndExeTime(new Date());
//            task.setTaskStatus(rowNum >=0 ? OlapTaskStatusEnum.SUCCESS.getValue() : OlapTaskStatusEnum.FAIL.getValue());
//            task.setExeResult(rowNum >=0 ? OlapTaskStatusEnum.SUCCESS.getNote() : "数据添加Tair发生错误");
//            updateTaskStatus(task, Lists.newArrayList(OlapTaskStatusEnum.RUNNING.getValue()));
//            if (rowNum >= 0) {
//                tairService.setTairData(tair_pre + tairDataKey, task.getId().toString(), TairService.expireTime - 60 * 60);
//            }
//            logger.info("******************* 执行完成，任务ID: {} ******************", taskId);
//        } catch (Exception e) {
//            logger.error("*************** 执行异常，任务ID：{} *************", taskId, e);
//            task.setEndExeTime(new Date());
//            task.setTaskStatus(OlapTaskStatusEnum.FAIL.getValue());
//            task.setExeResult(e.getMessage());
//            updateTaskStatus(task, Lists.newArrayList(OlapTaskStatusEnum.RUNNING.getValue()));
//            BusinessMonitor.recordError("olap_task_process_error");
//        }
//    }
//
//    /**
//     * 更新任务状态
//     *
//     * @param olapTaskQueue
//     * @param statusList
//     *
//     * @return
//     */
//    private int updateTaskStatus(OlapTaskQueue olapTaskQueue, List<Integer> statusList) {
//        try {
//            return olapTaskQueueService.updateOlapTaskQueueInfoByStatus(olapTaskQueue, statusList);
//        } catch (Exception e) {
//            logger.error("method updateTaskStatus error", e);
//            return 0;
//        }
//    }
//
//    /**
//     * 服务启动运行中任务置为失败
//     */
//    private void updateTaskByServicStartup() {
//        try {
//            OlapTaskQueue olapTaskQueue = new OlapTaskQueue();
//            olapTaskQueue.setEndExeTime(new Date());
//            olapTaskQueue.setTaskStatus(OlapTaskStatusEnum.FAIL.getValue());
//            olapTaskQueue.setExeResult("服务重启");
//            olapTaskQueueService.restartFailTask(olapTaskQueue, Lists.newArrayList(OlapTaskStatusEnum.RUNNING.getValue()), getIP());
//        } catch (Exception e) {
//            logger.error("method updateTaskByServicStartup error", e);
//        }
//    }
//
//    /**
//     * 执行任务调用
//     */
//    @Scheduled(cron = "*/10 * * * * ?")
//    private void callExecuteTask() {
//        logger.info("周期执行开始");
//        try {
//            loadTask();
//            needRun = true;
//            executeTask();
//        } catch (Exception e) {
//            logger.error("周期执行发生异常", e);
//        }
//        logger.info("周期执行结束");
//    }
//
//    @SuppressWarnings("unchecked")
//    private void loadTask() {
//        try {
//            if(cacheService.hasPureKey(OLAP_TASK_QUEUE_LOCK) && cacheService.getPureExpire(OLAP_TASK_QUEUE_LOCK, TimeUnit.SECONDS).longValue() == -1) {
//                cacheService.expirePure(OLAP_TASK_QUEUE_LOCK, 10, TimeUnit.SECONDS);
//            }
//            if (!cacheService.savePureDataIfAbsent(OLAP_TASK_QUEUE_LOCK, 1)) {
//                logger.info("加载任务，未获取到锁");
//            } else {
//                cacheService.savePureDataWithExpired(OLAP_TASK_QUEUE_LOCK, 1, 3, TimeUnit.MINUTES);
//
//                Long size = cacheService.forList().size(OLAP_TASK_QUEUE);
//                if (size != null && size.longValue() >= maxTaskNum) {
//                    logger.info("当前任务数{}，超过最大任务上限{}", size, maxTaskNum);
//                } else {
//                    Object obj = cacheService.forList().index(OLAP_TASK_QUEUE, 0);
//                    List<OlapTaskQueue> list = olapTaskQueueService.queryWaitTaskBatch(obj != null ? Long.parseLong(obj.toString()) : 0L,
//                            OlapTaskStatusEnum.WAIT.getValue(), maxTaskNum - (size != null ? size.intValue() : 0));
//                    if (CollectionUtils.isNotEmpty(list)) {
//                        cacheService.forList().leftPushAll(OLAP_TASK_QUEUE, list.stream().map(OlapTaskQueue :: getId).collect(Collectors.toList()));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error("加载执行发生异常", e);
//        } finally {
//            cacheService.pureDelete(OLAP_TASK_QUEUE_LOCK);
//        }
//    }
//
//    /**
//     * 获取IP
//     *
//     * @return
//     */
//    private String getIP() {
//        try {
//            if (StringUtils.isBlank(IP)) {
//                String os = System.getProperty("os.name").toLowerCase();
//                if (os.contains("windows")) {
//                    IP = InetAddress.getLocalHost().getHostAddress();
//                } else {
//                    Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
//                    while (netInterfaces.hasMoreElements()) {
//                        NetworkInterface ni = netInterfaces.nextElement();
//                        if ("eth0".equals(ni.getName())) {
//                            Enumeration<InetAddress> ips = ni.getInetAddresses();
//                            while (ips.hasMoreElements()) {
//                                IP = ips.nextElement().getHostAddress();
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (UnknownHostException uhe) {
//            IP = "unknown";
//            logger.error("get ip address error", uhe);
//        } catch (Exception e) {
//            IP = "unknown";
//            logger.error("get ip address error", e);
//        }
//        return IP;
//    }
//}
