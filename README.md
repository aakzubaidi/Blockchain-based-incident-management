# Blockchain based Incident management
This project shows an in example of authenticating and authorizing monitoring tools. It also shows how an authorised monitoring tool can use our library for handling incidents and reporting them to Blockchain (Hyperledger Fabric 2). The reported incidents are processed and assessed by a non-repudible smart contract operating the in blockchain environment beyond the influence of service providers or third parties . Please refer to out published article [A Blockchain-based Approach for Assessing Compliance with SLA-guaranteed IoT Services](https://ieeexplore.ieee.org/document/9192398).
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
- Periodic assessment of the compliance of cloud providers (monitored service).Oper

3- Alerting mechanism:
- Identify breaches (non-compliant monitoring metrics) and prepares them to be reported to the blockchain.
- it provides an adjustable periodic Incident reporting, such that workers will periodically (e.g. every 1 minute) ensure there is no tangling incidents. Workers autonomously collects them and report them to the smart contract.

4- Adjustable metrics and thresholds.
- report about any QoS (i.e. Throughput, Latency, ...).
- set required level (Greater than, Less than, equals, ...).
- set threshold (e.g. 10).




 
