# Apache ActiveMQ
En este repositorio se encontrarán los experimentos realizados con Apache ActiveMQ 5.8.0 http://activemq.apache.org/activemq-580-release.html. La configuración realizada es un cluster "network of workers" que funciona como un balanceador de cargas de mensajes, en los que los clientes son productores y consumidores. 
A continuación se darán unos pasos para ejecutar la prueba de productor/consumidor.

# Instrucciones
1. Una vez se haya clonado el repositorio, descargar Activemq 5.8.0 copiar la carpeta en cada broker. Configurar la carpeta apache-activemq-5.8.0 copiando en su interior bin, conf, cluster y example. Si el directorio ya contiene el directorio example no copiarlo. 

2. Ir al siguiente directorio /b3/apache-activemq-5.8.0/cluster/broker-3/bin y escribir el comando ./broker-3 console para iniciar la conexión de los brokers. Luego, escribir el comando en broker-2 y broker-1 en el orden respectivo seguidos de "console". El broker-1 tendrá dos conexiones broker-2 y broker-3, de los cuales el primero (broker-1) será la fuente de los mensajes y los demás los destinos.

3. Cada broker se debe iniciar en consolas diferentes. Si no se conectan, se debe ir a la configuración de cada broker, es decir, en el caso de broker-3 ir a /b3/apache-activemq-5.8.0/cluster/broker-3/bin y editar el archivo que tiene el mismo nombre y la siguiente línea "export ACTIVEMQ_HOME=/home/ubuntu/apache-activemq-5.8.0" modificarla por la carpeta donde se clonó o descargó la carpeta. 

4. En caso de querer cambiar las direcciones a las que se conectará cada broker o la topología definida de colas a tópicos, en el archivo de configuracion se podrán incluir los cambios para luego, ponerlos dentro del archivo activemq.xml del broker-1 ubicado en la carpeta conf.

5. Luego, en consolas diferentes iniciamos un productor y tantos consumidores se desee. Vamos al siguiente directorio b1\apache-activemq-5.8.0\example para ejecutar la aplicación y conectar el productor, para los consumidores vamos a  b2\apache-activemq-5.8.0\example y  b3\apache-activemq-5.8.0\example. Se conectan primero los consumidores para que esperen recibir mensajes, se escribe el siguiente comando 

        ant consumer -Durl=tcp://localhost:61626 -Dtopic=false -Dsubject=moo.bar -DclientId=C1 -Ddurable=true
    
6. Para el productor 

        ant producer -Durl=tcp://localhost:61616 -Dtopic=false -Dsubject=moo.bar -Dmax=10 -Ddurable=true

7. La dirección tcp://localhost:puerto incluye el puerto con el que se conectan los brokers y a ellos productores y consumidores.

8. Las direcciones y el tipo de conexión se pueden configurar de acuerdo a como se desee al igual que el nombre de la cola (-Dsubject), el número de mensajes (-Dmax), mantener los mensajes en la cola (-Ddurable) y el nombre del consumidor (-DclientId).


