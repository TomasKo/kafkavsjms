
<h1 align="center">How to run it</h1>

  <p align="center">
    This section is about how to run dockerized Kafka and JMS(ActiveMQ).
</p>

## Setting up your computer

Getting all the tooling setup on your computer can be a daunting task, but thankfully as Docker has become stable, getting Docker up and running on your favorite OS has become very easy.

The [Install Docker](https://docs.docker.com/desktop/) guide on Docker has detailed instructions for setting up Docker on Mac, Linux and Windows.

Once you are done installing Docker, test your Docker installation by running the following:

```dockerfile
$ docker run hello-world
```

You should get:
Hello from Docker.
This message shows that your installation appears to be working correctly.

## Run docker images

To run docker images you have to go in terminal to folder

```shell
cd seniorprogram\docker\docker-compose
```
and run the docker-compose:
```shell
docker-compose up
```

after initialize of containers you can simply run implemented java examples on each topic.

