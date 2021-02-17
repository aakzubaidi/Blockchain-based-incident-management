/**
 * @appnote Example QoS metric reporting using throughput
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
import application.java.ViolationRepoter;

public class DriverClasss {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        Datastore datastore = Datastore.getInstance();
        //choose a an identity name for your monitoring tool.
        datastore.setMonitoringIdentity("AliAlzubaidi");
        datastore.setQoSmetric("testEclipse1");
        //set a threshold to test against
        datastore.setQoSThreshold(10);
        //set delay for scheduling the incident reporter in seconds
        datastore.setDelay(10);

        // schedule monitoring agent to check for incidents
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new ReportingScheduler(), datastore.getDelay(), datastore.getDelay(), TimeUnit.SECONDS);

        MonitoringBehaviour monitoringBehaviour = new MonitoringBehaviour();

        // //important for connecting to the blockchain environment
        // // enrolls the admin and register the monitoring client
         try {
         EnrollAdmin.main(null);
         RegisterUser.main(null);
         CreateQoS.main(null);
         } catch (Exception e) {
         System.err.println(e);
         }

        // create a QoS metric
        // before running this, please define a QoS in file Datastore.
        //CreateQoS.main(null);

        // test compliant monitoring metric against a threshold
        // please define a threshold in the file Datastore.
        int throughputMetric = 11;
        for (int i = 1; i <= 10; i++) {
            MonitoringBehaviour.evaluateMonitoringMetric(throughputMetric);
            //rate of metrics reporting to the duration that takes the scheduler to report incidents
            TimeUnit.SECONDS.sleep(datastore.getDelay()/2);
        }
        
        
        // test violation monitoring metric against a threshold
        // please define a threshold in the file Datastore.
        throughputMetric = 9;
        for (int i = 1; i <= 10; i++) {
            MonitoringBehaviour.evaluateMonitoringMetric(throughputMetric);
            //rate of metrics reporting to the duration that takes the scheduler to report incidents
            TimeUnit.SECONDS.sleep(datastore.getDelay()/2);
        }


        ViolationRepoter violationRepoter = new ViolationRepoter();
        violationRepoter.transactionsStats();

	}

}
