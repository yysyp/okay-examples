# okay-examples
## Collected examples, it's not perfect but works.
## The idea is that has something useful no need to be perfect and keep learning new things.

### There is a quick copy module project tool it provide the ability of coping a existing project to generate a new project: 
    copy-tool/src/main/java/ps/demo/copy/CopyTool.java

### There is a simple CRUD example using JPA: springboot-jpa-demo
URLs:
- http://localhost:10001/api-docs
- http://localhost:10001/swagger-ui/index.html
- http://localhost:10001/api/books/

H2 DataBase:
- http://localhost:10001/h2-console
<pre>
    Driver Class: org.h2.Driver
    JDBC URL:     jdbc:h2:mem:snd;MODE=MYSQL
    User Name:    sa
</pre>

--
MinIO: https://blog.csdn.net/joshua317/article/details/128259365?ops_request_misc=%257B%2522request%255Fid%2522%253A%25228ff3c3768c0448e68f2a8c4bde560296%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=8ff3c3768c0448e68f2a8c4bde560296&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_click~default-5-128259365-null-null.142^v100^pc_search_result_base9&utm_term=minio&spm=1018.2226.3001.4187

SpringBoot 实战：文件上传之秒传、断点续传、分片上传:
https://blog.csdn.net/MarkerHub/article/details/129253226

https://www.51cto.com/article/801461.html
https://www.51cto.com/article/791860.html



断点续传（Resumable File Upload）是一种文件上传的技术，它允许在上传过程中出现中断或失败的情况下，能够从中断的位置继续上传，而不需要重新上传整个文件。这在处理大文件或不稳定的网络连接时非常有用。
断点续传的实现通常涉及以下几个关键概念和步骤：
1. 分片：将大文件分割成较小的文件块（通常是固定大小的块），每个块都有一个唯一的标识符。
2. 上传请求：客户端发起上传请求，并将文件分片按顺序上传到服务器。
3. 上传状态记录：服务器端需要记录上传的状态，包括已接收的分片、分片的顺序和完整文件的大小等信息。
4. 中断处理：如果上传过程中发生中断（例如网络中断、用户主动中止等），客户端可以记录已上传的分片信息，以便在恢复上传时使用。
5. 恢复上传：当上传中断后再次开始上传时，客户端可以发送恢复上传请求，并将已上传的分片信息发送给服务器。
6. 服务器处理：服务器接收到恢复上传请求后，根据已上传的分片信息，判断哪些分片已经上传，然后继续接收剩余的分片。
7. 合并文件：当所有分片都上传完成后，服务器将所有分片按顺序组合成完整的文件。
   https://github.com/cpc0209/v2v3-large-file-upload