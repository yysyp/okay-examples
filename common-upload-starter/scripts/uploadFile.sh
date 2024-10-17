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


