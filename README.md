# Apache ActiveMQ
En esta rama se encuentra el experimento de baja frecuencia con Eketal.

# Instrucciones

1. Abrir 3 consolas para ejecutar un productor y varios consumidores. 

2. Ir a /amq y en una de la consolas, ejecutar el comando "mvn clean package".

3. Luego, ejecutar cada .java con el siguiente comando mvn exec:java -Dexec.mainClass="example.src.ProducerTool" y para los consumidores mvn exec:java -Dexec.mainClass="example.src.ConsumerTool". Para modificar el número de mensajes producidos o de consumidores, se debe modificar cada archivo respectivamente. Ir a la siguiente ubicación /amq/src/main/java/example/src y para el número de mensajes cambiar el valor de "messageCount" y para los consumidores el valor de "parallelThreads".
