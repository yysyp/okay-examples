Service DNS
Kubernetes provides a built-in DNS service that allows services to be discovered by their
DNS name. Each service is assigned a DNS name that is based on its name and
namespace. The DNS name for the service is in the following format:
..svc.cluster.local
For example, if you have a service named “my-service” in the “my-namespace” namespace,
its DNS name would be “my-service.my-namespace.svc.cluster.local”.
3/6
By using the DNS name of a service, other services can communicate with it without having
to know its IP address. This makes it easier to scale and manage services in a microservices
architecture.
To test the book-service, run the following command:
curl http://book-service.default.svc.cluster.local:8080/books
This command will make a GET request to the book-service to retrieve a list of books.

---

kubectl -n nsapp -it exec $(kubectl -n nsapp get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep springcloud-gateway | head -1) -- bash
http://springcloud-gateway-service.nsapp.svc.cluster.local:10000/api/books/


kubectl -n nsapp logs -f $(kubectl -n nsapp get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep springcloud-gateway | head -1)