# Blockchain-based Incident Management Client Library (Approach)
 This repo provides a library that conceives our approach for handling SLA breaches based on blockchain. It enables identifying incidents and reporting them to blockchain-based incident management solutions, where these incidents to be processed and assessed by a non-repudible smart contract operating the in blockchain environment beyond the influence of service providers or third parties. For further details about our blockchain-ased incident management approach, refer to our published article [A Blockchain-based Approach for Assessing Compliance with SLA-guaranteed IoT Services](https://ieeexplore.ieee.org/document/9192398). 
 
 This library operates at the client level and acts as a client for our blockchain-based incident management solution. it provides existing monitoring tools with the capabilities of identifying incidents based on pre-defined Service Level Objective (SLO) as per defined in the SLA in place. The ultimate aim of this library is to apply best practice methods that we learnt from experience and several experiments on how monitoring tools should identify incidents and report them to blockchain in a way that is appropriate to blockchain unique behavior. For example, we know from experience that, blockchain transactions are not fire-and-forget, and there has to be a safe mechanism for reporting incidents. This is of especial important when it comes to critical scenarios. This library can be integrate with existing monitoring tools that wishes to follow our blockchain-based approach for processing and assessing SLA incidents.

## Library Example usage
You find in the repo a java class, named **DriverClasss.java**. It shows an example of how to use our library for authenticating and authorizing monitoring tools. It also shows how an authorized monitoring tool can use our library creating QoS metrics, defining their threshold and required service level. It also shows how adjust schedulers that the library use for scheduling the reporting process. The library will handle the rest tasks such as identifying incidents, managing them, and reporting them to Blockchain (Hyperledger Fabric 2).

## Design Methodology
By studying the capabilities of typical monitoring tools such as diversity of collected metrics and at which rates they are collected, and so on, we realize this incident management Blockchain client.  The ultimate aim is to enable seamless integration of this Blockchain-based incident management client with any monitoring tool.

## Library features:
1- In terms of Authentication and Authorization to the blockchain platform:
- creates a blockchain waller for the monitoring tool.
- enables communication a blockchain environment
- enrolls to the Blockchain Certificate Authority by generating the already recognized Monitoring Admin Identity and import it to the monitoring wallet.
- Registers the monitoring tool to the blockchain platform using the Admin identity. It generates relevant Identity and import it the monitoring wallet.
-facilitate communication with the blockchain platform and the deployed smart contract. 

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

2- import necessary cryptos, and connection files necessary for communication with blockchain certificate authority, authentication and authorization. They are also necessary for communicating to the blockchain platform, the smart contract, and for performing relevant incident management tasks.
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
Datastore.setQoSmetric(QoSmetric);
//important for connecting to the blockchain environment
// enrolls the admin and register the monitoring client
try {
EnrollAdmin.main(null);
RegisterUser.main(null);
CreateQoS.main(null);
} catch (Exception e) {
System.err.println(e);
}

```






 
