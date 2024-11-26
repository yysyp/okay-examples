package com.minio.config;

import com.google.common.collect.Multimap;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.Signer;
import io.minio.credentials.Credentials;
import io.minio.http.Method;
import okhttp3.HttpUrl;
import okhttp3.Request;

import static io.minio.http.HttpUtils.EMPTY_BODY;

/**
 * 接入minio，如果url是内网，上传没问题，但是需要公网去访问就会出问题
 * 所以，重写获取url的接口
 */
public class IMinioClient extends MinioClient {

    private String publicBaseUrl;

    public void setPublicBaseUrl(String publicBaseUrl) {
        this.publicBaseUrl = publicBaseUrl;
    }

    protected IMinioClient(MinioClient client) {
        super(client);
    }

    /**
     * 获取公网临时访问地址
     *
     * @param args
     */
    public String getPresignedObjectPublicUrl(GetPresignedObjectUrlArgs args) throws Exception {
        return getPresignedObjectUrl(args, publicBaseUrl);
    }

    /**
     * 获取公网临时访问地址，可以指定baseurl
     *
     * @param args
     * @param endpoint
     */
    public String getPresignedObjectUrl(GetPresignedObjectUrlArgs args, String endpoint) throws Exception {
        checkArgs(args);

        byte[] body = (args.method() == Method.PUT || args.method() == Method.POST) ? EMPTY_BODY : null;

        Multimap<String, String> queryParams = newMultimap(args.extraQueryParams());
        if (args.versionId() != null) {
            queryParams.put("versionId", args.versionId());
        }

        String region = getRegion(args.bucket(), args.region());
        if (provider == null) {
            HttpUrl url = buildUrl(args.method(), args.bucket(), args.object(), region, queryParams);
            return url.toString();
        }

        Credentials creds = provider.fetch();
        if (creds.sessionToken() != null) {
            queryParams.put("X-Amz-Security-Token", creds.sessionToken());
        }
        HttpUrl url = buildUrl(args.method(), args.bucket(), args.object(), region, queryParams);
        // 这部分修改访问地址，修改方式就是直接将baseurl替换成自定义的地址
        if (endpoint != null) {
            url = url.newBuilder(url.toString().replace(baseUrl.toString(), endpoint)).build();
        }
        Request request =
                createRequest(
                        url,
                        args.method(),
                        args.extraHeaders() == null ? null : httpHeaders(args.extraHeaders()),
                        body,
                        0,
                        creds);
        url = Signer.presignV4(request, region, creds.accessKey(), creds.secretKey(), args.expiry());
        return url.toString();
    }

}
