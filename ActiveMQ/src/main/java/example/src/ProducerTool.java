package example.src;

import java.io.IOException;
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.util.IndentPrinter;

/**
 * A simple tool for publishing messages
 *
 *
 */
public class ProducerTool extends Thread {

    private Destination destination;
    private int messageCount = 10;
    private long sleepTime;
    private boolean verbose = true;
    private int messageSize = 255;
    private static int parallelThreads = 1;
    private long timeToLive;
    private String user = ActiveMQConnection.DEFAULT_USER;
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private String subject = "TOOL.DEFAULT";
    private boolean topic;
    private boolean transacted;
    private boolean persistent;
    private static Object lockResults = new Object();

   private Session session;
    
    public static void main(String[] args) throws Exception {
        ArrayList<ProducerTool> threads = new ArrayList();
        ProducerTool producerTool = new ProducerTool();
        String[] unknown = CommandLineSupport.setOptions(producerTool, args);
        if (unknown.length > 0) {
            System.out.println("Unknown options: " + Arrays.toString(unknown));
            System.exit(-1);
        }
        producerTool.showParameters();
        for (int threadCount = 1; threadCount <= parallelThreads; threadCount++) {
            producerTool = new ProducerTool();
            CommandLineSupport.setOptions(producerTool, args);
            producerTool.start();
            threads.add(producerTool);
        }
        while (true) {
            Iterator<ProducerTool> itr = threads.iterator();
            int running = 0;
            while (itr.hasNext()) {
                ProducerTool thread = itr.next();
                if (thread.isAlive()) {
                    running++;
                }
            }
            if (running <= 0) {
                System.out.println("All threads completed their work");
                break;
            }
            try {
                Thread.sleep(0);
            } catch (Exception e) {
            }
        }      
        

        producerTool.queueDeleter();
        producerTool.purgeMessages("TOOL.DEFAULT");
        
        //producerTool.run();
        System.exit(0);
    }

    public void showParameters() {
        System.out.println("Connecting to URL: " + url + " (" + user + ":" + password + ")");
        System.out.println("Publishing a Message with size " + messageSize + " to " + (topic ? "topic" : "queue") + ": " + subject);
        System.out.println("Using " + (persistent ? "persistent" : "non-persistent") + " messages");
        System.out.println("Sleeping between publish " + sleepTime + " ms");
        System.out.println("Running " + parallelThreads + " parallel threads");

        if (timeToLive != 0) {
            System.out.println("Messages time to live " + timeToLive + " ms");
        }
    }
   

    public void run() {
    	System.out.println("Producer");
        Connection connection = null;
        try {
            // Create the connection.
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
            connection = connectionFactory.createConnection();
            connection.start();

            // Create the session
            Session session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
            if (topic) {
                destination = session.createTopic(subject);
            } else {
                destination = session.createQueue(subject);
            }

            // Create the producer.
            MessageProducer producer = session.createProducer(destination);
            if (persistent) {
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            } else {
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            }
            if (timeToLive != 0) {
                producer.setTimeToLive(timeToLive);
            }

            // Start sending messages
            sendLoop(session, producer);

            System.out.println("[" + this.getName() + "] Done.");

            synchronized (lockResults) {
                ActiveMQConnection c = (ActiveMQConnection) connection;
                System.out.println("[" + this.getName() + "] Results:\n");
                c.getConnectionStats().dump(new IndentPrinter());
            }

        } catch (Exception e) {
            System.out.println("[" + this.getName() + "] Caught: " + e);
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Throwable ignore) {
            }
        }
    }
    
    protected void onMessage(MessageProducer producer, TextMessage message)throws Exception {
    	producer.send(message);
    }
    
    protected void sendLoop(Session session, MessageProducer producer) throws Exception {

        for (int i = 0; i < messageCount || messageCount == 0; i++) {

            TextMessage message = session.createTextMessage(createMessageText(i));

            if (verbose) {
                String msg = message.getText();
                if (msg.length() > 50) {
                    msg = msg.substring(0, 50) + "...";
                }
                System.out.println("[" + this.getName() + "] Sending message: '" + msg + "'");
            }

            //producer.send(message);
            onMessage(producer,message);
           

            if (transacted) {
                System.out.println("[" + this.getName() + "] Committing " + messageCount + " messages");
                session.commit();
            }
            Thread.sleep(sleepTime);
        }
    }

    private String createMessageText(int index) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        String date = dateFormat.format(cal.getTime());
        
        StringBuffer buffer = new StringBuffer(messageSize);
        buffer.append("Message: " + index + " time: " + date);
        if (buffer.length() > messageSize) {
            return buffer.substring(0, messageSize);
        }
        for (int i = buffer.length(); i < messageSize; i++) {
            buffer.append(' ');
        }
        return buffer.toString();
    }
   
    public void queueDeleter()throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url);
        MBeanServerConnection conn = jmxc.getMBeanServerConnection();

        String operationName="removeQueue"; //operation like addQueue or removeQueue
        String parameter="TEST.FOO";   // Queue name
        ObjectName activeMQ = new ObjectName("org.apache.activemq:brokerName=broker-1,type=Broker");
        if(parameter != null) {
            Object[] params = {parameter};
            String[] sig = {"java.lang.String"};
            conn.invoke(activeMQ, operationName, params, sig);
        }
    }
    
    public void purgeMessages(String queueName) {
        try {
             JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
             JMXConnector jmxc = JMXConnectorFactory.connect(url);
             MBeanServerConnection conn = jmxc.getMBeanServerConnection();
             ObjectName name = new ObjectName("org.apache.activemq:type=Broker,brokerName=broker-1");
             BrokerViewMBean proxy = (BrokerViewMBean)MBeanServerInvocationHandler.newProxyInstance(conn, name, BrokerViewMBean.class, true);
             for (ObjectName queue : proxy.getQueues()) {  
                QueueViewMBean queueBean = (QueueViewMBean) MBeanServerInvocationHandler.newProxyInstance(conn, queue, QueueViewMBean.class, true);
                if(queueBean.getName().equals(queueName)) {
                    System.out.println("Purge messages : " + queueName);
                    queueBean.purge();
                    return;
                }
             }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setPersistent(boolean durable) {
        this.persistent = durable;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setParallelThreads(int parallelThreads) {
        if (parallelThreads < 1) {
            parallelThreads = 1;
        }
        this.parallelThreads = parallelThreads;
    }

    public void setTopic(boolean topic) {
        this.topic = topic;
    }

    public void setQueue(boolean queue) {
        this.topic = !queue;
    }

    public void setTransacted(boolean transacted) {
        this.transacted = transacted;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
}
