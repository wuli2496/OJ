# Odyssey Online and Mobile Banking Infrastructure

# Prerequisites

- Minikube or Docker for desktop
- Java 11 or above
- Maven 3.6 or above

# Install Kubernetess and Istio

Please make sure that you have a local Kubernetess installation up and running via Minikube or Docker for Desktop

https://kubernetes.io/docs/setup/learning-environment/minikube/ on Mac

or

https://docs.docker.com/docker-for-windows/kubernetes/ on Windows

If you install Kubernetess via Docker Desktop, Kubernetess will fetch local images directly but you need to apply below configuration for minikube so that we don't have to upload images to dockerhub for local setup.

```bash
eval $(minikube docker-env) # Please do this for each new terminal
```

Install istioctl https://istio.io/latest/docs/setup/getting-started/#download and makesure istioctl is available in your PATH

Install istio on kubernetess

```bash
 istioctl install --set profile=demo
```

Enable automatic injection of sidecar

```bash
kubectl label namespace default istio-injection=enabled
```

Please make sure that, there is no other application listening port 80 on your local because Istio Ingress Gateway will listen this port by default. Ingress gateway will be enabled in api-gateway-service

Please install and deploy following microservices in below order by using their Readme files

1- cyn-commons (Common shared code for all services. This will be installed in your local maven repository)

2- authorization-service (Please note that, authorization service demonstrates usage of configmap and secrets for injecting properties to applications)

3- authentication-service

4- account-service

5- api-gateway-service

# Setup the Ingress Host and Port for Local

We need to check the port and hostname of istio ingress to access services locally. This changes per environment.

Firstly, Check if istio ingress gateway is activated.

```bash
kubectl get svc istio-ingressgateway -n istio-system

NAME                   TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)                                                                      AGE
istio-ingressgateway   LoadBalancer   10.105.131.102   localhost     15021:30758/TCP,80:31067/TCP,443:31029/TCP,31400:30071/TCP,15443:31489/TCP   5h
```

Setting the ingress IP depends on the cluster provider:

Minikube: (via NodePort)

```bash
export INGRESS_HOST=$(minikube ip)
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
```

### Minikube tunnel for local access

You can only access LoadBalancer of istio on mac via `minikube tunnel` command. It must be run in a separate terminal window to keep the LoadBalancer running. Ctrl-C in the terminal can be used to terminate the process at which time the network routes will be cleaned up

Docker For Desktop:

```bash
export INGRESS_HOST=localhost
export INGRESS_PORT=80
```

Use above host and port in the postman to access ingress locally while testing the collection. Please refer to https://istio.io/latest/docs/tasks/traffic-management/ingress/ingress-control/ for more information.

# Validation

Please use the postman collections under api-gateway-service/doc to validate endpoints. You can follow the order of api definitions to 
execute and end-to-end flow.
