# Blockchain-based Incident Management Client Library (Approach)
 This repo provides a library that realizes our approach for handling SLA breaches based on blockchain. It enables identifying incidents and reporting them to blockchain-based incident management solutions, where these incidents to be processed and assessed by a non-repudible smart contract operating the in blockchain environment beyond the influence of service providers or third parties. For further details about our blockchain-ased incident management approach, refer to our published article [A Blockchain-based Approach for Assessing Compliance with SLA-guaranteed IoT Services](https://ieeexplore.ieee.org/document/9192398). 
 
 This library operates at the client level and acts as a client for our blockchain-based incident management solution. it provides existing monitoring tools with the capabilities of identifying incidents based on pre-defined Service Level Objective (SLO) as per defined in the SLA in place. The ultimate aim of this library is to apply best practice methods that we learnt from experience and several experiments on how monitoring tools should identify incidents and report them to blockchain in a way that is appropriate to blockchain unique behavior. For example, we know from experience that, blockchain transactions are not fire-and-forget, and there has to be a safe mechanism for reporting incidents. This is of especial important when it comes to critical scenarios. This library can be integrate with existing monitoring tools that wishes to follow our blockchain-based approach for processing and assessing SLA incidents.

## Library Example usage
You find in the repo a java class, named **DriverClasss.java**. It shows an example of how to use our library for authenticating and authorizing monitoring tools. It also shows how an authorized monitoring tool can use our library creating QoS metrics, defining their threshold and required service level. It also shows how adjust schedulers that the library use for scheduling the reporting process. The library will handle the rest tasks such as identifying incidents, managing them, and reporting them to Blockchain (Hyperledger Fabric 2).

## Design Methodology
By studying the capabilities of typical monitoring tools such as diversity of collected metrics and at which rates they are collected, and so on, we realize this incident management Blockchain client. The ultimate aim is to enable seamless integration of this Blockchain-based incident management client with any monitoring tool.

## Library features:
1- In terms of Authentication and Authorization to the blockchain platform:
- creates a blockchain wallet for the monitoring tool.
- establishes and facilitate communication with a blockchain environment and the deployed smart contract.
- enrolls the monitoring tool admin to the Blockchain Certificate Authority by generating a registered Identity and importing it to the monitoring wallet.
- Registers the monitoring tool to the blockchain Certificate authority using the Admin identity. It generates the monitoring tool corresponding Identity and import it to the monitoring wallet.

2- It provides a set of APIs to facilitate interfacing with the smart contract functionalities such as:
- creation a QoS metric of interest (i.e. Throughput, Latency, ...).
- Incident reporting and processing.
- Periodic assessment of the compliance of cloud providers (monitored service).

3- Alerting mechanism:
- Identify breaches (non-compliant monitoring metrics) and prepares them to be reported to the blockchain.
- it provides an adjustable periodic Incident reporting, such that workers will periodically (e.g. every 1 minute) ensure there is no tangling incidents. Workers autonomously collects them and report them to the smart contract.

4- Adjustable metrics and thresholds.
- report about any quantifiably measurable QoS (i.e. Throughput, Latency, ...).
- set required level (Greater than, Less than, equals, ...).
- set threshold (e.g. 10).
-----
## Prerequisites
Following are important requirements for using the library in your monitoring tool: 
 
1- Build Path: 
- include **application-java.jar** to your class path.
- the library was tested using JRE AdoptOpenJDK 11 [11.0.8]. Highly recommended to include this particular JRE to your build path as well.

2- import cryptos, and connection files necessary for establishing communication with blockchain certificate authority, authentication and authorization. They are also important for transacting with the blockchain platform, the smart contract, and for performing relevant incident management tasks.
- in the high level of your monitoring tool directory, import both of these files:
    * connection.json
    * certificate.pem

----
## User manual
Using the library is straightforward process. Refer to Class **DriverClass.java** for an example of how to use the library within your monitoring tool. Following are some detail. The instructions in Class **DriverClass.java** are only for example and testing purposes. It is not meant to be conclusive or be run within the same class. Different instructions perform different functionalities. Thus, every part of the class should be placed wherever suitable in your monitoring tool. 

For each of the following you may use in separate classes in altogether in one class. depends on your needs.

------
### Authenticate and Authorize your monitoring:
1- import relevant APIs
```java
import application.java.Datastore;
import application.java.EnrollAdmin;
import application.java.RegisterUser;
```
2- Enroll Admin, create monitoring wallet, and import Admin identity to the monitoring wallet. Then, use the Admin identity to register an identity for your monitoring tool in the blockchain platform. It will import the monitoring identity in the wallet as well

```java
Datastore datastore = Datastore.getInstance();
//choose a an identity name for your monitoring tool.
datastore.setMonitoringIdentity("AliAlzubaidi");
//important for connecting to the blockchain environment
//enrolls the admin and register the monitoring client
    try {
    //enroll Admin
    EnrollAdmin.main(null);
    // register an identity for the monitoring tool
    RegisterUser.main(null);
    } catch (Exception e) {
    System.err.println(e);
    }
```

Eventually, you will see a directory in your main project folder; named **wallet** containing both identities of:
- admin.id
- your monitoring identity

---
## Creating a QoS metric in the blockchain records
1- import relevant APIs
```java
import application.java.Datastore;
import application.java.MonitoringBehaviour;
import application.java.ReportingScheduler;
```
2- define the expected behavior; A.K.A Service Level Objective (SLO)
```java
Datastore datastore = Datastore.getInstance();
//name of the QoS metric (e.g. Throughput, Latency, etc.)
String QoSmetric = ("APPLatency");
 // create a QoS metric
CreateQoS.main(new String[] {QoSmetric});
```
### Warning:
if the QoS already exists in the blockchain records, the library will notify you about that by showing an error (the QoS metric already exists). This is only a warning and does not mean defective operation. You can use the already existing QoS metric as normal.

-----
## Adjusting the scheduler.
For every QoS metric you create, You need to bring up a QoS worker that seeks to identify any relevant incident to this particular QoS metric. You can also adjust this worker to be scheduled at fixed rate as or different rate as per you requirement. For example, you can consider setting the delay for scheduling worker according to how frequent you expect to receive monitoring logs about this particular QoS metric.

1- You may need to import the following:
```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import application.java.ReportingScheduler;
```

2- you can define the QoS worker and schedule it as in the following:
```java
//set delay in second
datastore.setDelay(10);
//schedule monitoring agent to check for incidents
final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
scheduler.scheduleAtFixedRate(new ReportingScheduler(QoSmetric), datastore.getDelay(), datastore.getDelay(),TimeUnit.SECONDS);
```

----

## Evaluating received monitoring metrics:
For every metric the monitoring tool receives about a QoS, you can pass it to library for compliance evaluation. You can set the following:
- What QoS metric (Throughput, Latency, CPU, etc.)
- Required Service Level (GraterThan, LessThan, Equals).
- Threshold.

The Library will evaluate every received metric according to this required service level against the defined threshold.

1- Import relevant APIs.
```java
import application.java.MonitoringBehaviour;
import application.java.RequieredLevel;
```
2- evaluate the received monitoring metric
```java
// Requered Service Level (GraterThan, LessThan, Equals)
RequieredLevel requieredLevel = RequieredLevel.LessThan;
// set a threshold to test against
double threshold = 10;
MonitoringBehaviour monitoringBehaviour = new MonitoringBehaviour();
// test compliant monitoring metric against a threshold
// please define a thrshold in the file Datastore.
double monitoringReading = 11;

monitoringBehaviour.evaluateMonitoringMetric(QoSmetric, monitoringReading requieredLevel, threshold);
```

Anything else will be taken care of by our library (Blockchain-based Incident Management Client).





 
