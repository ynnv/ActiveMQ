# Apache ActiveMQ
En esta rama se encuentra el experimento de baja frecuencia con Eketal.

# Instrucciones

1. Luego de conectar los brokers como se menciona en las instrucciones del master, abrir 3 consolas para ejecutar un productor y varios consumidores. 

2. Ir a /amq y en una de la consolas, ejecutar el comando "mvn clean package".

3. Luego, ejecutar cada .java con el siguiente comando para conectar un consumidor al broker-2 mvn exec:java -Dexec.mainClass="example.src.ConsumerTool", para conectar un consumidor al broker-3 mvn exec:java -Dexec.mainClass="example.src.ConsumerTool2" y para el productor mvn exec:java -Dexec.mainClass="example.src.ProducerTool", en el orden respectivo puesto que los consumidores esperan que el productor envíe los mensajes. 

4. Para modificar el número de mensajes producidos o de consumidores, se debe modificar cada archivo respectivamente. Ir a la siguiente ubicación /amq/src/main/java/example/src, para el número de mensajes cambiar el valor de "messageCount" y para los consumidores el valor de "parallelThreads".
