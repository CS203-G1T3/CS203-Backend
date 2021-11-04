# CS203

## Deployment Guide

Note! Ensure database is connected to remote database (ask jon)
and seed data is commented off

First dockerise application

```bash 
    # for mac, windows might be different
    
    # compile jar files for dockerisation
    ./mvnw clean package
    
    # build images
    docker build -t cs203-backend .
    
    # run container on local to make sure it works!
    docker run -i -p 8080:8080 cs203-backend:latest
```

If all good (make sure it runs first as expected),
tag image to AWS ECR (stores image on cloud)

check push commands on ECR

```bash
    # authenticate aws ecr cli
    aws ecr get-login-password | docker login --username ...
    
    # tag docker image
    docker tag cs203-backend:latest <repositoryURI>:latest
    
    # push image to ecr
    docker push <repositoryURI
```

Now all you need to do is update the service in ECS for changes to be made