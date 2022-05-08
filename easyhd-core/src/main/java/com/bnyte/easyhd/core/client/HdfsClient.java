package com.bnyte.easyhd.core.client;

import com.bnyte.easyhd.core.bind.FsPut;
import com.bnyte.easyhd.core.constant.SystemConstant;
import com.bnyte.easyhd.core.enums.EHdfsMethod;
import com.bnyte.easyhd.core.exception.ClientNotFoundException;
import com.bnyte.easyhd.core.uitl.PropertiesUtils;
import com.sun.jersey.api.client.ClientHandlerException;
import org.apache.commons.lang3.AnnotationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.PropertyPlaceholderHelper;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

/**
 * @author bnyte
 * @since 2022/5/8 14:34
 */
public class HdfsClient {

    /**
     * hdfs请求方式
     */
    private EHdfsMethod method;

    /**
     * hdfs请求地址
     */
    private String address;

    /**
     * 本地文件地址
     */
    private String local;

    /**
     * 远程地址
     */
    private String remote;

    /**
     * 是否重写
     */
    private Boolean overwrite;

    /**
     * 是否删除本地文件
     */
    private Boolean remove;

    /**
     * 操作用户
     */
    private String user;

    /**
     * 执行方法
     */
    private Method invokeMethod;

    /**
     * 执行参数
     */
    private List<Object> args;

    public HdfsClient() {
    }

    public static HdfsClient build(Object annotation, List<Object> args, Method method) {
        if (annotation instanceof FsPut) {
            return buildFsPutClient((FsPut) annotation, args, method);
        }
        throw new ClientNotFoundException();
    }

    private static HdfsClient buildFsPutClient(FsPut annotation, List<Object> args, Method method) {
        HdfsClient hdfsClient = new HdfsClient();
        hdfsClient.setInvokeMethod(method);
        hdfsClient.setArgs(args);
        hdfsClient.setAddress(annotation.address());
        hdfsClient.setLocal(annotation.local());
        hdfsClient.setOverwrite(annotation.overwrite());
        hdfsClient.setRemote(annotation.remote());
        hdfsClient.setRemove(annotation.remove());
        hdfsClient.setUser(annotation.user());
        hdfsClient.setMethod(EHdfsMethod.PUT);
        return hdfsClient;
    }


    public EHdfsMethod getMethod() {
        return method;
    }

    public void setMethod(EHdfsMethod method) {
        this.method = method;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (StringUtils.isEmpty(address)) {
            this.address = "hdfs://localhost:8020";
            return;
        }
        if (!CollectionUtils.isEmpty(this.args)) {
            Properties render = PropertiesUtils.render(this.args, this.invokeMethod);
            this.address = SystemConstant.placeholderHelper.replacePlaceholders(address, render);
            return;
        }
        this.address = address;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        if (!CollectionUtils.isEmpty(args)) {
            Properties render = PropertiesUtils.render(args, this.invokeMethod);
            this.local = SystemConstant.placeholderHelper.replacePlaceholders(local, render);
            return;
        }
        this.local = local;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        if (StringUtils.isEmpty(remote)) {
            Resource resource = new FileSystemResource(this.local);
            this.remote = "/easyhd" + "/" + resource.getFilename();
            return;
        }
        if (!CollectionUtils.isEmpty(args)) {
            Properties render = PropertiesUtils.render(args, this.invokeMethod);
            this.remote = SystemConstant.placeholderHelper.replacePlaceholders(remote, render);
            return;
        }
        this.remote = remote;
    }

    public Boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }

    public Boolean getRemove() {
        return remove;
    }

    public void setRemove(Boolean remove) {
        this.remove = remove;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        if (!CollectionUtils.isEmpty(args)) {
            Properties render = PropertiesUtils.render(args, this.invokeMethod);
            this.user = SystemConstant.placeholderHelper.replacePlaceholders(user, render);
            return;
        }
        this.user = user;
    }

    public void setInvokeMethod(Method invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "HdfsClient{" +
                "method=" + method +
                ", address='" + address + '\'' +
                ", local='" + local + '\'' +
                ", remote='" + remote + '\'' +
                ", overwrite=" + overwrite +
                ", remove=" + remove +
                ", user='" + user + '\'' +
                '}';
    }
}
