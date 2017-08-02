# Apache ActiveMQ
En esta rama se encuentra el issue de ActiveMQ con Eketal. Esta propuesta se hace en un solo broker, puesto que al cambiar el valor en el campo del archivo .xml en todos los brokers de la topología definida fallará la conexión. Si se quiere eliminar colas en todos los brokers se debe hacer uno a uno.

Información: https://issues.apache.org/jira/browse/AMQ-5901?jql=project%20%3D%20AMQ%20AND%20resolution%20%3D%20Unresolved%20AND%20text%20~%20%22deadlock%22%20ORDER%20BY%20priority%20DESC%2C%20updated%20DESC

# Instrucciones

1. Si se quiere eliminar colas y "purgar" mensajes, configurar el archivo “activemq.xml” que se encuentra en /ActiveMQ/b1/apache-activemq-5.8.0/cluster/broker-1/conf para el caso del Broker-1.

2. Modificar el valor de (<managementContext createConnector="false"/>) a “true” para que no falle la conexión del broker.

3. Ir a /ActiveMQ y ejecutar el comando "mvn clean package".

3. Luego, ejecutar cada .java (productor y consumidores) con los comandos mencionados en las demás ramas.
