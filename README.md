# Apache ActiveMQ
En esta rama se encuentra el issue de ActiveMQ con Eketal.

# Instrucciones

1. Abrir 3 consolas para ejecutar un productor y varios consumidores. 

2. Ir a /Active y en una de la consolas, ejecutar el comando "mvn clean package".

3. Luego, ejecutar cada .java con el siguiente comando para conectar un consumidor al broker-2 mvn exec:java -Dexec.mainClass="example.src.ConsumerTool", para conectar un consumidor al broker-3 mvn exec:java -Dexec.mainClass="example.src.ConsumerTool2" y para el productor y que se conecte a broker-1 mvn exec:java -Dexec.mainClass="example.src.ProducerTool", en el orden respectivo puesto que los consumidores esperan que el productor envíe los mensajes.

4. Para modificar el número de mensajes producidos o de consumidores, se debe modificar cada archivo respectivamente. Ir a la siguiente ubicación /Active/src/main/java/example/src y para el número de mensajes cambiar el valor de "messageCount" y para los consumidores el valor de "parallelThreads".
