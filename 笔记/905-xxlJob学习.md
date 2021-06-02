### xxlJob分片任务

分片任务就是一个任务，开始时由机器进行判断是否要执行相应业务，并在业务

分片任务执行参考官方包中的实验

 `com.xxl.job.executor.service.jobhandler.SampleXxlJob#shardingJobHandler2` 

- 代码

  <img src="905-xxlJob学习.assets/image-20210428151321037.png" alt="image-20210428151321037" style="zoom:50%;" />

- 任务策略必须为 `分片广播` 

  <img src="905-xxlJob学习.assets/image-20210428151147452.png" alt="image-20210428151147452" style="zoom: 50%;" />

- 执行结果

  <img src="905-xxlJob学习.assets/image-20210428150924958.png" alt="image-20210428150924958" style="zoom:50%;" />

  <img src="905-xxlJob学习.assets/image-20210428151029981.png" alt="image-20210428151029981" style="zoom:50%;" />





