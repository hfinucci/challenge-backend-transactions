# Java Backend Challenge

## Instalacion
Para correr el servicio, se puede buildear la imagen localmente o simplemente pullearla de Dockerhub. Para traerse la imagen remota basta con correr:
> docker pull hfinucci/backend-challenge

Por otro lado, si se prefiere buildear la imagen localmente:
>docker build -t backend-challenge .

Una vez que se tiene la imagen se puede levantar un contenedor con:
>docker run -it -p 8080:8080 backend-challenge

## Aclaraciones de la implementación

A la hora de implementar la API, se tomaron varias decisiones de diseño acordes al scope y requerimientos de esta.

- Dado que los valores a devolver en ambos metodos GET tienen poca complejidad, se considero innecesario crear DTOs a los que mappear las respuestas.
- La estructura del proyecto tiene la forma controller -> service -> persistence. Por mas que en este caso la capa de servicio no maneja lógica, es buena práctica aislar la capa de persistencia o repositorio de los controllers. En el caso de tener que escribir lógica de negocio, se encontraría en la capa de servicio.
- A la hora de guardar los datos de las transacciones, se utiliza un HashMap que tiene por clave al id de la transacción y como valor al objeto 'Transaction'. Si el endpoint de creación de transacciones hubiese sido un método POST (con un id serial manejado por la capa de persistencia) en vez de PUT, se hubiesen guardadas en un HashMap que tenga como clave a la propiedad 'type', con el fin de optimizar el GET /transactions/types/{type}
- Debido a la naturaleza de los métodos GET pedidos, hay un tradeoff entre la inserción y recuperación de datos. En este caso se priorizó una creación de transacciones en tiempo constante, sacrificando performance para hacer el retrieval de datos, sobretodo en el GET /transactions/sum/{transaction_id}. En un caso real, esta decisión se vería afectada por los requerimientos funcionales y no funcionales.
Se decidió utilizar la librería Lombok simplemente para facilitar el logging y la implementación del modelo 'Transaction'