# Java SDK

Conductor provides the following java clients to interact with the various APIs

| Client          | Usage                                                                     |
|-----------------|---------------------------------------------------------------------------|
| Metadata Client | Register / Update workflow and task definitions                           |
| Workflow Client | Start a new workflow / Get execution status of a workflow                 |
| Task Client     | Poll for task / Update task result after execution / Get status of a task |

## Worker
Conductor provides an automated framework to poll for tasks, manage the execution thread and update the status of the execution back to the server.

Implement the [Worker](https://github.com/conductor-oss/java-sdk/blob/main/conductor-client/src/main/java/com/netflix/conductor/client/worker/Worker.java) interface to execute the task.

## TaskRunnerConfigurer  
The TaskRunnerConfigurer can be used to register the worker(s) and initialize the polling loop.  
Manages the task workers thread pool and server communication (poll and task update).  

Use the [Builder](https://github.com/conductor-oss/java-sdk/blob/main/conductor-client/src/main/java/com/netflix/conductor/client/automator/TaskRunnerConfigurer.java#L62) to create an instance of the TaskRunnerConfigurer. The Builder constructor takes the following parameters.

| Parameter  | Description                                                   |
| ---------- | ------------------------------------------------------------- |
| TaskClient | TaskClient used to communicate to the Conductor server        |
| Workers    | Workers that will be used for polling work and task execution.|

The builder accepts the following parameters:

| Parameter                      | Description                                                                                                                                                                                                                    | Default                      |
|--------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------|
| withEurekaClient               | EurekaClient is used to identify if the server is in discovery or not.  When the server goes out of discovery, the polling is stopped unless `pollOutOfDiscovery` is set to true. If passed null, discovery check is not done. | provided by platform         |
| withThreadCount                | Number of threads assigned to the workers. Should be at-least the size of taskWorkers to avoid starvation in a busy system.                                                                                                    | Number of registered workers |
| withSleepWhenRetry             | Time in milliseconds, for which the thread should sleep when task update call fails, before retrying the operation.                                                                                                            | 500                          |
| withUpdateRetryCount           | Number of attempts to be made when updating task status when update status call fails.                                                                                                                                         | 3                            |
| withWorkerNamePrefix           | String prefix that will be used for all the workers.                                                                                                                                                                           | workflow-worker-             |
| withShutdownGracePeriodSeconds | Waiting seconds before forcing shutdown of your worker                                                                                                                                                                         | 10                           |

Once an instance is created, call `init()` method to initialize the TaskPollExecutor and begin the polling and execution of tasks.

!!! tip "Note"
    To ensure that the TaskRunnerConfigurer stops polling for tasks when the instance becomes unhealthy, call the provided `shutdown()` hook in a `PreDestroy` block.

## Properties
The worker behavior can be further controlled by using these properties:

| Property           | Type    | Description                                                                                                                                | Default |
|--------------------|---------|--------------------------------------------------------------------------------------------------------------------------------------------|---------|
| paused             | boolean | If set to true, the worker stops polling.                                                                                                  | false   |
| pollInterval       | int     | Interval in milliseconds at which the server should be polled for tasks.                                                                   | 1000    |
| pollOutOfDiscovery | boolean | If set to true, the instance will poll for tasks regardless of the discovery  <br/> status. This is useful while running on a dev machine. | false   |

Further, these properties can be set either by Worker implementation or by setting the following system properties in the JVM:

| Name                                        | Description                                                      |
|---------------------------------------------|------------------------------------------------------------------|
| `conductor.worker.<property>`               | Applies to ALL the workers in the JVM.                           |
| `conductor.worker.<taskDefName>.<property>` | Applies to the specified worker.  Overrides the global property. |

## Examples

* [Sample Worker Implementation](https://github.com/conductor-oss/java-sdk/blob/main/conductor-client/src/test/java/com/netflix/conductor/client/sample/SampleWorker.java)
* [Example](https://github.com/conductor-oss/java-sdk/blob/main/conductor-client/src/test/java/com/netflix/conductor/client/sample/Main.java)