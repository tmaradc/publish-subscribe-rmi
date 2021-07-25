# publish-subscribe-rmi

## Trabalho 1 da disciplina de Sistemas Distribuídos

 Execução no Windows, cada tópico em um terminal diferente.

### Compilar os arquivos

    cd pubsub

    javac -d classes Cell.java Client.java ClientImplementation.java Server.java ServerImplementation.java

### Iniciar o Java RMI Registry
    
    cd pubsub/classes

    PATH=%PATH%;C:\Program Files\Java\jdk-16.0.2\bin

    start rmiregistry

### Iniciar o Servidor

    cd pubsub

    java -classpath classes -Djava.rmi.server.codebase=file:classes/ pubsub.ServerImplementation

### Iniciar um Cliente (cada um em um terminal)

    cd pubsub

    java -classpath classes -Djava.rmi.server.codebase=file:classes/ pubsub.ClientImplementation [user] [sub_key] [pub_key] [article]

