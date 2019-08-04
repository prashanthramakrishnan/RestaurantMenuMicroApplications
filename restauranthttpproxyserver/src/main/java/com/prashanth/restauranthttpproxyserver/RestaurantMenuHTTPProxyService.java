package com.prashanth.restauranthttpproxyserver;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSource;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.TransportProtocol;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

public class RestaurantMenuHTTPProxyService extends Service {

    private HttpProxyServer httpProxyServer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        httpProxyServer = DefaultHttpProxyServer.bootstrap()
                .withAllowLocalOnly(false)
                .withAllowRequestToOriginServer(true)
                .withTransportProtocol(TransportProtocol.TCP)
                .withTransparent(true)
                .withFiltersSource(new HttpFiltersSource() {
                    @Override
                    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                        //Intercept request for GET, sample.json
                        if (HttpMethod.GET == originalRequest.method()) {
                            return new RestaurantMenuResponseFilter(originalRequest, ctx);
                        }
                        return null;
                    }

                    @Override
                    public int getMaximumRequestBufferSizeInBytes() {
                        return 10*1024*1024;
                    }

                    @Override
                    public int getMaximumResponseBufferSizeInBytes() {
                        return 10*1024*1024;
                    }
                })
                .start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        httpProxyServer.stop();
    }

    private class RestaurantMenuResponseFilter extends HttpFiltersAdapter {

        RestaurantMenuResponseFilter(HttpRequest originalRequest, ChannelHandlerContext ctx) {
            super(originalRequest, ctx);
        }

        @Override
        public HttpResponse clientToProxyRequest(HttpObject httpObject) {
            //check if the uri is the same as that of the request, if yes send the stored/cached data else continue
            Uri uri = new Uri.Builder().scheme("content").authority("com.prashanth.restaurantmenudata")
                    .path(originalRequest.uri())
                    .build();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                ByteBuf buffer = Unpooled.wrappedBuffer(cursor.getBlob(1));
                cursor.close();
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                        buffer);
                response.headers().add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
                response.headers().add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept");
                response.headers().add(HttpHeaderNames.CONTENT_LENGTH, buffer.readableBytes());
                response.headers().add(HttpHeaderNames.CONTENT_TYPE, "application/json");
                return response;
            }
            return super.clientToProxyRequest(httpObject);
        }
    }
}