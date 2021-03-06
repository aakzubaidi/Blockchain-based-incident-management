/**
 * @appnote Example QoS metric reporting using throughput. 
 * Have a look on the readme file for approaprtiate usage
 * @author aliaalzubaidi
 * @since 2021
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import application.java.CreateQoS;
import application.java.Datastore;
import application.java.EnrollAdmin;
import application.java.MonitoringBehaviour;
import application.java.RegisterUser;
import application.java.ReportingScheduler;
import application.java.RequieredLevel;

public class DriverClasss {

    public static void main(String[] args) throws Exception {
        Datastore datastore = Datastore.getInstance();
        // choose a an identity name for your monitoring tool.
        Datastore.setMonitoringIdentity("aliAlzubaidi");
        // name the QoS metric (throughput, latency, etc)
        String QoSmetric = ("TransmissionTimeMELToVM");
        // Requered Service Level (GraterThan, LessThan, Equals)
        RequieredLevel requieredLevel = RequieredLevel.LessThan;
        // set a threshold to test against
        double threshold = 10;

        // //important for connecting to the blockchain environment
        // // enrolls the admin and register the monitoring client
        try {
            EnrollAdmin.main(null);
            RegisterUser.main(null);
        } catch (Exception e) {
            System.err.println(e);
        }

        // create a QoS metric
        // before running this, please define a QoS in file Datastore.
        CreateQoS.main(new String[] {QoSmetric});


        // schedule monitoring agent to check for incidents
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
        scheduler.scheduleAtFixedRate(new ReportingScheduler(QoSmetric), datastore.getDelay(), datastore.getDelay(),
                TimeUnit.SECONDS);

        MonitoringBehaviour monitoringBehaviour = new MonitoringBehaviour();
        
        //Usecase: Breaches
        // test compliant monitoring metric against a threshold
        // please define a thrshold in the file Datastore.
        double monitoringReading = 11;
        for (int i = 1; i <= 2; i++) {
            monitoringBehaviour.evaluateMonitoringMetric(QoSmetric, monitoringReading, requieredLevel, threshold);

            // rate of metrics reporting to the duration that takes the scheduler to report
            // incidents
            TimeUnit.SECONDS.sleep(datastore.getDelay() / 2);
        }

        /**
         * 
         * 
         * 
         * 
         * A set of usecases test
         * 
         * 
         * 
         * 
         */
        //Usecase: compliant
        // test violation monitoring metric against a threshold
        // please define a thrshold in the file Datastore.
        monitoringReading = 9;
        for (int i = 1; i <= 10; i++) {
            monitoringBehaviour.evaluateMonitoringMetric(QoSmetric, monitoringReading, requieredLevel, threshold);
            // rate of metrics reporting to the duration that takes the scheduler to report
            // incidents
            TimeUnit.SECONDS.sleep(datastore.getDelay() / 2);
        }
        
        
        //Usecase: Braches again to see if the incident manager update compliant records.
        // test violation monitoring metric against a threshold
        // please define a thrshold in the file Datastore.
        monitoringReading = 11;
        for (int i = 1; i <= 10; i++) {
            monitoringBehaviour.evaluateMonitoringMetric(QoSmetric, monitoringReading, requieredLevel, threshold);
            // rate of metrics reporting to the duration that takes the scheduler to report
            // incidents
            TimeUnit.SECONDS.sleep(datastore.getDelay() / 2);
        }
        
        //Usecase: Braches with no wait time (performance testing).
        // test violation monitoring metric against a threshold
        // please define a thrshold in the file Datastore.
        monitoringReading = 11;
        for (int i = 1; i <= 10; i++) {
            monitoringBehaviour.evaluateMonitoringMetric(QoSmetric, monitoringReading, requieredLevel, threshold);
            // rate of metrics reporting to the duration that takes the scheduler to report
            // incidents
        }

    }

}
