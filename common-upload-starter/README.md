### There is a simple CRUD example using JPA: common-upload-starter
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

---
Shell script to call upload file API (from Insomnia):
On Windows open with: Git Bash:

#! /bin/bash
set -eu

curl --request POST \
--url 'http://localhost:10001/api/books/file?fileType=string&checksum=string' \
--header 'Content-Type: multipart/form-data' \
--header 'User-Agent: insomnia/10.0.0' \
--form 'file=@D:\workspace\okay-examples\common-upload-starter\src\main\resources\excel\inputData2headerRows.xlsx' \
--form 'uploadMetaDto={
"fileType": "xlsx",
"checksum": "blabla",
"extraParams": {
"additionalProp1": {},
"additionalProp2": {},
"additionalProp3": {}
}
}'

echo ' '
read -p "Press any key..."


